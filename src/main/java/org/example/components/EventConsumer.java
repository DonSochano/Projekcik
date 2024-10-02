package org.example.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.models.CustomEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventConsumer {

    private final FileMover fileMover;
    public static final String EVENT_MESSAGE = "MoveFromNew"; // Stworzyć enum, w Custom Event dodać go jako pole, enum serializuje sie w jsonie jako String jak coś, na bazie trzeba jakas adnotacje dodawac jak masz enuma w kolumnie
    public static final String EVENT_MESSAGE1 = "MoveFromWorkToProcessed"; // te 2 nazwy to maja byc cześci enuma

    @EventListener
    public void consumeEvent(CustomEvent event) throws IOException {

        log.info("Odebrany event: event: {}, fileName: name: {}", // ang
                event.getEventCommand(), event.getFileName());
        if (event.getEventCommand().equals(EVENT_MESSAGE)) { // tutaj dodać pattern matching albo switcha na ten enum a nie ify, docelowo tutaj będzie wstrzykiwany tylko FileMover jak przerobisz i wołana jedna metoda, tam masz dalaszy opis
            fileMover.moveToWork(event.getFileName());
        } else if (event.getEventCommand().equals(EVENT_MESSAGE1))
            log.info("Odebrany event: event: {}, fileName: name: {}", // ang
                    event.getEventCommand(), event.getFileName());
        {
            fileMover.moveToProcessed(event.getFileName());
        }
    }
}
