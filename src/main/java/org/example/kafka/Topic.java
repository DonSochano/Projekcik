package org.example.kafka;

public interface Topic<M extends TopicMessage> {
    String getName();
}
