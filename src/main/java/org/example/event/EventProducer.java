package org.example.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventProducer{

   final private  ApplicationEventPublisher eventPublisher;

    public void produceEvent(Event event){
        eventPublisher.publishEvent(event);
        }
    }


