package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();

    private Resp post(Req req) {
        map.putIfAbsent(req.sourceName(), new ConcurrentLinkedQueue<>());
        map.get(req.sourceName()).add(req.param());
        return new Resp("", Response.SUCCESS.name());
    }

    private Resp get(Req req) {
        String res = map.getOrDefault(req.sourceName(), new ConcurrentLinkedQueue<>()).poll();
        return new Resp(
                res == null ? "" : res,
                res == null ? Response.NOT_SUCCESS.name() : Response.SUCCESS.name()
        );
    }

    @Override
    public Resp process(Req req) {
        return "POST".equals(req.httpRequestType()) ? post(req) : get(req);
    }
}
