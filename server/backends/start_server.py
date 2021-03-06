from concurrent import futures

import grpc

from backends.db_provider import DbProvider
from backends.semantic_search import SemanticSearch
import backends.semantic_search_server_pb2 as semantic_search_server_pb2
import backends.semantic_search_server_pb2_grpc as semantic_search_server_pb2_grpc


AVAILABLE_LANGS = ['en', 'ru']
ss_models = dict()


class DataHashServicer(semantic_search_server_pb2_grpc.SemanticSearchServicer):
    def get_semantic_search_result(self, request, context):
        response = semantic_search_server_pb2.Phrase()
        if request.lang == 'apply_skills':
            update_models()
            response.text = 'model update completed successfully'
            response.lang = 'apply_skiils'
        else:
            ss_model = ss_models[request.lang]
            response.text = ss_model.predict(request.text)
            response.lang = request.lang
        return response


def update_models():
    provider = DbProvider(AVAILABLE_LANGS)
    for lang in AVAILABLE_LANGS:
        training_data = provider.get_phrases_by_lang(lang)
        if len(training_data) == 0:
            raise ValueError("Database does not contain phrases for {0} lang".format(lang))
        ss_model = SemanticSearch(lang)
        ss_model.training(training_data)
        ss_models[lang] = ss_model

def server():
    provider = DbProvider(AVAILABLE_LANGS)
    for lang in AVAILABLE_LANGS:
        training_data = provider.get_phrases_by_lang(lang)
        if len(training_data) == 0:
            raise ValueError("Database does not contain phrases for {0} lang".format(lang))
        ss_model = SemanticSearch(lang)
        ss_model.training(training_data)
        ss_models[lang] = ss_model
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4))
    semantic_search_server_pb2_grpc.add_SemanticSearchServicer_to_server(DataHashServicer(), server)
    print('Starting semantic search server on port 6066 ...')
    print('AVAILABLE_LANGS:', AVAILABLE_LANGS)
    server.add_insecure_port('[::]:6066')
    server.start()

    try:
        while True:
            pass
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == '__main__':
    server()


