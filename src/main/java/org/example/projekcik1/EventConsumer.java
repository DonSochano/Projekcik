package org.example.projekcik1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {
    @Autowired // private final, Required Args Constructor
    FileProcesser fileProcesser;

    public static final String eventMessage = "MoveFromNew";
    // entery pomiedzy metodami i polami klasy żeby to było czytelne - WSZĘDZIE
    @EventListener
    public void consumeEvent(CustomEvent event) {
        System.out.println("Odebrany event: " + event.eventCommand() + event.fileName()); // zastosować adnotacje nad klasą SLF4J i skorzystać (pytaj czatu)
        // przed returnem ENTER !
        fileProcesser.moveToWork(event.fileName());

    }
}
