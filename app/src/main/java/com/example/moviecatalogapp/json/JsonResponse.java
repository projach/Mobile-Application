package com.example.moviecatalogapp.json;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;

import java.util.Objects;

public class JsonResponse {
    private JsonArray results;

    public JsonArray getMessage() {
        return results;
    }

    public void setMessage(JsonArray message) {
        this.results = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonResponse that = (JsonResponse) o;
        return Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results);
    }

    @NonNull
    @Override
    public String toString() {
        return "JsonResponse{" +
                "message=" + results.get(0).getClass() +
                '}';
    }
}
