package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.enums.EventType;


@AllArgsConstructor
public class Event implements CustomEvent {

    private EventType eventType;
    @Getter
    private String fileName;

@Override
    public EventType getEventType() {
    return eventType;
    }
}
