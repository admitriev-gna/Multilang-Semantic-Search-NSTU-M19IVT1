from django.db import models
from django.urls import reverse

# Create your models here.
class Phrases(models.Model):
    name = models.CharField(max_length=200, null=False)
    identityNumber = models.CharField(max_length=200, null=False)

    def __str__(self):
        return self.name

    def get_absolute_url(self):
        return reverse('phrases_edit', kwargs={'pk': self.pk})