package org.example.projekcik1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class EventProducer {

    private final ApplicationEventPublisher eventPublisher;

    public void produceEvent(CustomEvent event) {
        eventPublisher.publishEvent(event);
    }
}

@Setter
@Getter
@AllArgsConstructor
public class Event implements CustomEvent {
    private String eventCommand;
    private String fileName;

}
