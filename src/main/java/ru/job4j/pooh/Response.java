package ru.job4j.pooh;

public enum Response {
    SUCCESS("200"),
    NOT_SUCCESS("204");

    private final String response;

    Response(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
