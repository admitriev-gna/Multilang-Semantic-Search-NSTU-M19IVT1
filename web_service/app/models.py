from django.db import models
from django.urls import reverse

# Create your models here.
class Language(models.Model):
    name = models.CharField(max_length=200, null=False)

    def __str__(self):
        return self.name

class Phrases(models.Model):
    value = models.CharField(max_length=200, null=False)
    language = models.ForeignKey(Language, on_delete=models.CASCADE, unique=False)

    def __str__(self):
        return self.value

    def get_absolute_url(self):
        return reverse('phrases_edit', kwargs={'pk': self.pk})