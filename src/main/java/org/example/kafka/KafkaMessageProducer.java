package org.example.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Topic;
import org.example.models.TopicMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

@Slf4j
@AllArgsConstructor
public abstract class KafkaMessageProducer<M extends TopicMessage,T  extends Topic<M>>{


    private T topic;
    private Boolean isLoggingEnabled;
    private KafkaTemplate<String, M> kafkaTemplate;

    public final CompletableFuture<SendResult<String, M>> send(M message){
        var result = kafkaTemplate.send(topic.getName(), message);
        log("["+ topic.getName()+"]" + ": Produced message: " + message.toString());
        
        return result;
    }

    private final void log(String message){
        if (isLoggingEnabled) log.info(message);
    }

}
