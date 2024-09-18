package org.example.projekcik1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventConsumer {

    private final FileMover fileMover;
    public static final String EVENT_MESSAGE = "MoveFromNew";
    public static final String EVENT_MESSAGE1 = "MoveFromWorkToProcessed";

    @EventListener
    public void consumeEvent(CustomEvent event) throws IOException {

        log.info("Odebrany event: event: {}, fileName: name: {}",
                event.getEventCommand(), event.getFileName());
        if (event.getEventCommand().equals(EVENT_MESSAGE)) {
            fileMover.moveToWork(event.getFileName());
        } else if (event.getEventCommand().equals(EVENT_MESSAGE1))
            log.info("Odebrany event: event: {}, fileName: name: {}",
                    event.getEventCommand(), event.getFileName());
        {
            fileMover.moveToProcessed(event.getFileName());
        }
    }
}
