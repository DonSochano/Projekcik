package org.example.projekcik1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {
    @Autowired
    FileProcesser fileProcesser;
    public static final String eventMessage = "MoveFromNew";
    @EventListener
    public void consumeEvent(CustomEvent event) {
        System.out.println("Odebrany event: " + event.eventCommand() + event.fileName());
        fileProcesser.moveToWork(event.fileName());

    }
}
