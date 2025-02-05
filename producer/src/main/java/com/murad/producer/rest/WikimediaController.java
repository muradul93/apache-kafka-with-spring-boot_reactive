package com.murad.producer.rest;


import com.murad.producer.stream.WikiMediaStreamConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wikimedia")
@RequiredArgsConstructor
public class WikimediaController {

    private final WikiMediaStreamConsumer streamConsumer;

    @GetMapping
    public void startPublishing() {
        streamConsumer.consumeAndPublish();
    }
}
