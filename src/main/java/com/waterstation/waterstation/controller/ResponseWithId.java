package com.waterstation.waterstation.controller;

class ResponseWithId {
    Object response;
    String salerOrderId;

    public ResponseWithId(Object response, String salerOrderId) {
        this.response = response;
        this.salerOrderId = salerOrderId;
    }

    public Object getResponse() {
        return response;
    }

    public String getSalerOrderId() {
        return salerOrderId;
    }
}
