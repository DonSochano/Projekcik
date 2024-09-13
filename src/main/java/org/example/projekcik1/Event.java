package org.example.projekcik1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
 class EventProducer {
    @Autowired // private final ApplicationEventPublisher, wyjebac Autowired, i zastosowac adnotacje nad klasą RequiredArgsConstructor
    ApplicationEventPublisher eventPublisher;
    public void produceEvent(CustomEvent event){
    eventPublisher.publishEvent(event);
}
}

interface CustomEvent{ // do osobnego pliku !

public String eventCommand(); // w interfejsie public jest zbędne
public String fileName(); // zmienić nazwe metod na getFileName i getEventCommand

    }


public class Event implements CustomEvent{
    String eventCommand; // private, adnotacje getterów i setterów, przypisywać wartośc przez setter !
    String fileName;
    Event(String eventBody, String fileName) { // adnotacja allArgsConstructor
        this.eventCommand = eventBody;
        this.fileName = fileName;
    }
    @Override
    public String fileName(){
        return fileName;
    } // jak zmienisz nazwy metod w interfejsie i dasz nad klasą adnotacje @Getter to wygenerowany getter bedzie miał nazwe getFileName i z automatu nadpisze metode z interfejsu, to samo w event command

    @Override
   public String eventCommand(){
        return eventCommand;
    }
}
