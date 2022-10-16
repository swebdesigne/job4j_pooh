package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> map
            = new ConcurrentHashMap<>();

    private Resp post(Req req) {
        map.getOrDefault(req.sourceName(), new ConcurrentHashMap<>())
                .values()
                .forEach(container -> container.add(req.param()));
        return new Resp("", Response.SUCCESS.name());
    }

    private Resp get(Req req) {
        map.putIfAbsent(req.sourceName(), new ConcurrentHashMap<>());
        map.get(req.sourceName()).putIfAbsent(req.param(), new ConcurrentLinkedQueue<>());
        String res = map.get(req.sourceName()).get(req.param()).poll();
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
