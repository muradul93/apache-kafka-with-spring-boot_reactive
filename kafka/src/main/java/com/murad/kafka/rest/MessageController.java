package com.murad.kafka.rest;


import com.murad.kafka.payload.Student;
import com.murad.kafka.porducer.KafkaJsonProducer;
import com.murad.kafka.porducer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final KafkaProducer kafkaProducer;
    private final KafkaJsonProducer kafkaJsonProducer;
    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody String message){
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka Topic");
    }


    @PostMapping("/json")
    public ResponseEntity<String> sendJsonMessage(@RequestBody Student message){
        kafkaJsonProducer.sendMessage(message);
        return ResponseEntity.ok(" Student Message sent to Kafka Topic : " + message.toString());
    }
}
