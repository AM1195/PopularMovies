package com.devnilos.aft.proj1.popularmovies.network;

/**
 * Created by Nilos on 23-Jan-17.
 */

public class NetworkResponseWrapper<T> {
    private T response;
    private Exception exception;

    public NetworkResponseWrapper(T response) {
        this.response = response;
    }

    public NetworkResponseWrapper(Exception exception) {
        this.exception = exception;
    }

    public boolean hasException() {
        return this.exception != null;
    }

    public T getResponse() {
        return response;
    }

    public Exception getException() {
        return exception;
    }
}
