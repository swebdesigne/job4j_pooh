package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final static Map<String, String> RESPONSE = Map.of(
            "success", "200",
            "not_success", "204"
    );
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();

    private Resp post(Req req) {
        ConcurrentLinkedQueue<String> param = new ConcurrentLinkedQueue<>();
        param.add(req.param());
        map.putIfAbsent(req.sourceName(), param);
        return new Resp("", RESPONSE.get("success"));
    }

    private Resp get(Req req) {
        String res = map.getOrDefault(req.sourceName(), new ConcurrentLinkedQueue<>()).poll();
        return new Resp(
                res == null ? "" : res,
                res == null ? RESPONSE.get("not_success") : RESPONSE.get("success")
        );
    }

    @Override
    public Resp process(Req req) {
       return "POST".equals(req.httpRequestType()) ? post(req) : get(req);
    }
}
