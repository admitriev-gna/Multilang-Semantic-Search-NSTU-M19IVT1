import sys

sys.path.append("D:/mms/Multilang-Semantic-Search-NSTU-M19IVT1/server/")

from django.http import HttpResponse, HttpRequest
from django.shortcuts import render
from django.views.generic import ListView, DetailView
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from django.urls import reverse_lazy

from .models import Phrases

import grpc

from backends import semantic_search_server_pb2, semantic_search_server_pb2_grpc


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
    fields = ['value', 'language']
    success_url = reverse_lazy('phrases_list')

    def post(self, request, *args, **kwargs):
        send_data_to_server()
        return super().post(request, *args, **kwargs)


class PhrasesUpdate(UpdateView):
    model = Phrases
    fields = ['value', 'language']
    success_url = reverse_lazy('phrases_list')

    def post(self, request, *args, **kwargs):
        send_data_to_server()
        return super().post(request, *args, **kwargs)


class PhrasesDelete(DeleteView):
    model = Phrases
    success_url = reverse_lazy('phrases_list')

    def post(self, request, *args, **kwargs):
        send_data_to_server()
        return super().post(request, *args, **kwargs)
