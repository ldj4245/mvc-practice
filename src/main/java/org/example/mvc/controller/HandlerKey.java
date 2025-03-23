package org.example.mvc.controller;

import java.util.Objects;

public class HandlerKey {


    private RequestMethod method;
    private String urlPath;


    public HandlerKey(RequestMethod method, String urlPath) {
        this.method = method;
        this.urlPath = urlPath;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HandlerKey)) return false;
        HandlerKey that = (HandlerKey) o;
        return method == that.method && Objects.equals(urlPath, that.urlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath);
    }
}
