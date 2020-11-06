import os


class SemanticSearch:
    def __init__(self, lang):
        self.lang = lang
        self.embedding_model_path = os.path.join('/', 'ss_model')
        self.index = None

    def training(self, training_phrases):
        # тренировка self.index
        pass

    def predict(self, phrase):
        if self.index is None:
            raise ValueError('First need to train the semantic search model!')
        # encode
        # min dist (angular? cos?)
        pass
