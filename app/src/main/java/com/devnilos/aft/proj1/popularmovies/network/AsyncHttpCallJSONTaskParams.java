package com.devnilos.aft.proj1.popularmovies.network;

/**
 * Created by Nilos on 23-Jan-17.
 */

public class AsyncHttpCallJSONTaskParams {
    private String url;
    private Class clazz;

    public AsyncHttpCallJSONTaskParams(String url, Class clazz) {
        this.url = url;
        this.clazz = clazz;
    }

    public String getUrl() {
        return url;
    }

    public Class getClazz() {
        return clazz;
    }
}
