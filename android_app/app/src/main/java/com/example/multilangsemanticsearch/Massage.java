package com.example.multilangsemanticsearch;

public class Massage {
    int id;
    String message, lang, message_res, lang_res;
    Massage(String message, String lang, String message_res, String lang_res, int id) {
        this.id = id;
        this.message = message;
        this.lang = lang;
        this.message_res = message_res;
        this.lang_res = lang_res;
    }
}
