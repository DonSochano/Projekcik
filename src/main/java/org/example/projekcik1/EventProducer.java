package org.example.projekcik1;

import lombok.RequiredArgsConstructor;
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
