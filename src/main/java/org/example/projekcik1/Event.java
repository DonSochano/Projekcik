package org.example.projekcik1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
 class EventProducer {
    @Autowired
    ApplicationEventPublisher eventPublisher;
    public void produceEvent(CustomEvent event){
    eventPublisher.publishEvent(event);
}
}

interface CustomEvent{

public String eventCommand();
public String fileName();

    }


public class Event implements CustomEvent{
    String eventCommand;
    String fileName;
    Event(String eventBody, String fileName) {
        this.eventCommand = eventBody;
        this.fileName = fileName;
    }
    @Override
    public String fileName(){
        return fileName;
    }

    @Override
   public String eventCommand(){
        return eventCommand;
    }
}