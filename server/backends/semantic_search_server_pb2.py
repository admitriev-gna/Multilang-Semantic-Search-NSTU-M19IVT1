# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: semantic_search_server.proto
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='semantic_search_server.proto',
  package='',
  syntax='proto3',
  serialized_options=None,
  create_key=_descriptor._internal_create_key,
  serialized_pb=b'\n\x1csemantic_search_server.proto\"$\n\x06Phrase\x12\x0c\n\x04lang\x18\x01 \x01(\t\x12\x0c\n\x04text\x18\x02 \x01(\t2B\n\x0eSemanticSearch\x12\x30\n\x1aget_semantic_search_result\x12\x07.Phrase\x1a\x07.Phrase\"\x00\x62\x06proto3'
)




_PHRASE = _descriptor.Descriptor(
  name='Phrase',
  full_name='Phrase',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='lang', full_name='Phrase.lang', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='text', full_name='Phrase.text', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=32,
  serialized_end=68,
)

DESCRIPTOR.message_types_by_name['Phrase'] = _PHRASE
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

Phrase = _reflection.GeneratedProtocolMessageType('Phrase', (_message.Message,), {
  'DESCRIPTOR' : _PHRASE,
  '__module__' : 'semantic_search_server_pb2'
  # @@protoc_insertion_point(class_scope:Phrase)
  })
_sym_db.RegisterMessage(Phrase)



_SEMANTICSEARCH = _descriptor.ServiceDescriptor(
  name='SemanticSearch',
  full_name='SemanticSearch',
  file=DESCRIPTOR,
  index=0,
  serialized_options=None,
  create_key=_descriptor._internal_create_key,
  serialized_start=70,
  serialized_end=136,
  methods=[
  _descriptor.MethodDescriptor(
    name='get_semantic_search_result',
    full_name='SemanticSearch.get_semantic_search_result',
    index=0,
    containing_service=None,
    input_type=_PHRASE,
    output_type=_PHRASE,
    serialized_options=None,
    create_key=_descriptor._internal_create_key,
  ),
])
_sym_db.RegisterServiceDescriptor(_SEMANTICSEARCH)

DESCRIPTOR.services_by_name['SemanticSearch'] = _SEMANTICSEARCH

# @@protoc_insertion_point(module_scope)
