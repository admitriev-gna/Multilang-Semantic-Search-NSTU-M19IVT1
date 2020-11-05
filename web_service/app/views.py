from django.http import HttpResponse
from django.shortcuts import render
from django.views.generic import ListView, DetailView
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from django.urls import reverse_lazy

from .models import Phrases

# Create your views here.

class PhrasesList(ListView):
    model = Phrases

class PhrasesDetail(DetailView):
    model = Phrases

class PhrasesCreate(CreateView):
    model = Phrases
    fields = ['name', 'identityNumber']
    success_url = reverse_lazy('phrases_list')

class PhrasesUpdate(UpdateView):
    model = Phrases
    fields = ['name', 'identityNumber']
    success_url = reverse_lazy('phrases_list')

class PhrasesDelete(DeleteView):
    model = Phrases
    success_url = reverse_lazy('phrases_list')