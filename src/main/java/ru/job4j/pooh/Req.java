package ru.job4j.pooh;

import java.util.HashMap;
import java.util.Map;

public record Req(String httpRequestType, String poohMode, String sourceName, String param) {

    private static Map<String, String> params(String request) {
        String param = "";
        Map<String, String> res = new HashMap<>();
        String[] params = request.split("\n");
        String[] requestData = params[0].split(" ");
        String[] uri = requestData[1].substring(1).split("/");
        res.put("httpRequestType", requestData[0]);
        res.put("poohMode", uri[0]);
        res.put("sourceName", uri[1]);
        if (uri.length > 2) {
            param = uri[uri.length - 1];
        } else {
            if (params[params.length - 1].contains("=")) {
                param = params[params.length - 1];
            }
        }
        res.put("param", param);
        return res;
    }

    public static Req of(String connect) {
        Map<String, String> params = params(connect);
        return new Req(
                params.get("httpRequestType"),
                params.get("poohMode"),
                params.get("sourceName"),
                params.get("param")
        );
    }

    @Override
    public String toString() {
        return "Req{"
                + "httpRequestType='" + httpRequestType + '\''
                + ", poohMode='" + poohMode + '\''
                + ", sourceName='" + sourceName + '\''
                + ", param='" + param + '\''
                + '}';
    }
}
