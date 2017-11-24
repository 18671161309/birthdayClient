package com.trip.core.okHttp.callback;


public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
