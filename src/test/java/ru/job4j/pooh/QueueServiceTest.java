package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
    }

    @Test
    public void whenQueueServiceTesting() {
        QueueService queueService = new QueueService();
        queueService.process(new Req("POST", "queue", "weather", "temperature=18"));
        queueService.process(new Req("POST", "queue", "weather", "cloudiness=rainy"));
        Resp result1 = queueService.process(new Req("GET", "queue", "weather", null));
        Resp result2 = queueService.process(new Req("GET", "queue", "weather", null));
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("cloudiness=rainy");
    }
}