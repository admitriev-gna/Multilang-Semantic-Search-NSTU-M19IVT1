from django.http import HttpResponse
from django.shortcuts import render
from django.views.generic import ListView, DetailView
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from django.urls import reverse_lazy

from .models import Phrases

import grpc

from .backends import semantic_search_server_pb2, semantic_search_server_pb2_grpc

# Create your views here.
def send_data_to_server():
	print('start sending to server')
	channel = grpc.insecure_channel('localhost:6066')
	stub = semantic_search_server_pb2_grpc.SemanticSearchStub(channel)

	text = 'apply_skill'

	to_md5 = semantic_search_server_pb2.Phrase(lang='apply_skills', text=text)
	response = stub.get_semantic_search_result(to_md5)

class PhrasesList(ListView):
    model = Phrases

class PhrasesDetail(DetailView):
    model = Phrases

class PhrasesCreate(CreateView):
    model = Phrases
    fields = ['value','language']
    send_data_to_server()
    success_url = reverse_lazy('phrases_list')

class PhrasesUpdate(UpdateView):
    model = Phrases
    fields = ['value','language']
    send_data_to_server()
    success_url = reverse_lazy('phrases_list')

class PhrasesDelete(DeleteView):
    model = Phrases
    send_data_to_server()
    success_url = reverse_lazy('phrases_list')