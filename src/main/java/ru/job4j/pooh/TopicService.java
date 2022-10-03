package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final static Map<String, String> RESPONSE = Map.of(
            "success", "200",
            "not_success", "204"
    );
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> map
            = new ConcurrentHashMap<>();

    private Resp post(Req req) {
        map.get(req.sourceName()).values()
                .forEach(container -> container.add(req.param()));
        return new Resp("", RESPONSE.get("success"));
    }

    private Resp get(Req req) {
        map.putIfAbsent(req.sourceName(), new ConcurrentHashMap<>());
        map.get(req.sourceName()).putIfAbsent(req.param(), new ConcurrentLinkedQueue<>());
        String res = map.get(req.sourceName()).get(req.param()).poll();
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
