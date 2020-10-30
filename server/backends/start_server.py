import time
from concurrent import futures

import grpc

import backends.semantic_search_server_pb2 as semantic_search_server_pb2
import backends.semantic_search_server_pb2_grpc as semantic_search_server_pb2_grpc


class DataHashServicer(semantic_search_server_pb2_grpc.SemanticSearchServicer):
    def get_semantic_search_result(self, request, context):
        response = semantic_search_server_pb2.Phrase()
        # Add semantic search here
        response.lang = 'en'
        response.text = 'Hello from server'
        return response


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4))
    semantic_search_server_pb2_grpc.add_SemanticSearchServicer_to_server(DataHashServicer(), server)

    print('Starting server on port 6066.')
    server.add_insecure_port('[::]:6066')
    server.start()

    try:
        while True:
            pass
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == '__main__':
    serve()


