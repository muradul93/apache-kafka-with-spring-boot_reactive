package com.murad.producer.stream;

import com.murad.producer.config.WebClientConfig;
import com.murad.producer.producer.WikimediaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class WikiMediaStreamConsumer {

    private final WebClient webClient;
    private final String url = "https://stream.wikimedia.org/v2";
    private final WikimediaProducer wikimediaProducer;

    public WikiMediaStreamConsumer(WebClient.Builder webClientBuilder, WikimediaProducer wikimediaProducer) {
        this.webClient = webClientBuilder
                .baseUrl(url)
                .build();
        this.wikimediaProducer = wikimediaProducer;
    }

    public void consumeAndPublish() {
        webClient.get()
                .uri("/stream/recentchange")
                .retrieve()
                .bodyToFlux(String.class)
//                .subscribe(log::info);
                .subscribe(wikimediaProducer::send);
    }
}
