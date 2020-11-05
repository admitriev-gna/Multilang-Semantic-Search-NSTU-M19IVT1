from django.urls import path
from . import views

urlpatterns = [
    path('', views.PhrasesList.as_view(), name='phrases_list'),
    path('view/<int:pk>', views.PhrasesDetail.as_view(), name='phrases_detail'),
    path('new', views.PhrasesCreate.as_view(), name='phrases_new'),
    path('edit/<int:pk>', views.PhrasesUpdate.as_view(), name='phrases_edit'),
    path('delete/<int:pk>', views.PhrasesDelete.as_view(), name='phrases__delete'),
]