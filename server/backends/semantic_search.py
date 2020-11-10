import os
from sentence_transformers import SentenceTransformer, util
import torch


class SemanticSearch:
    def __init__(self, lang):
        self.lang = lang
        self.embedding_model_path = os.path.join('/', 'ss_model')
        self.embedder = SentenceTransformer('distiluse-base-multilingual-cased-v2')
        self.index = None
        self.training_data = None

    def training(self, training_phrases):
        self.index = self.embedder.encode(training_phrases, convert_to_tensor=True)
        self.training_data = training_phrases
        print('[SEMANTIC SEARCH] Training is completed')

    def predict(self, phrase):
        if self.index is None or self.training_data is None:
            raise ValueError('First need to train the semantic search model!')
        query_embedding = self.embedder.encode(phrase, convert_to_tensor=True)
        cos_scores = util.pytorch_cos_sim(query_embedding, self.index)[0]
        cos_scores = cos_scores.cpu()
        top_results = torch.topk(cos_scores, k=1)
        return self.training_data[int(top_results[1][0])]
