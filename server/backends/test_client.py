import grpc

import backends.semantic_search_server_pb2 as semantic_search_server_pb2
import backends.semantic_search_server_pb2_grpc as semantic_search_server_pb2_grpc

channel = grpc.insecure_channel('localhost:6066')
stub = semantic_search_server_pb2_grpc.SemanticSearchStub(channel)

text = 'Hello Semantic Search on Server'

to_md5 = semantic_search_server_pb2.Phrase(lang='en', text=text)
response = stub.get_semantic_search_result(to_md5)
print(response)