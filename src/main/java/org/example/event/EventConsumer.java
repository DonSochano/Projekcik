package org.example.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mover.FileMoverImpl;
import org.example.enums.ApplicationPath;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class  EventConsumer<E extends CustomEvent> {

    private final FileMoverImpl fileMover;


    @EventListener
    public void consumeEvent(E event) {

        log.info("Odebrany event: "+ event.getEventType() + "File: " + event.getFileName());
        switch (event.getEventType()){
            case MOVE_FROM_NEW_TO_WORK: fileMover.moveFile(ApplicationPath.PathNew, ApplicationPath.PathWork, event.getFileName());
                break;
            case MOVE_FROM_WORK_TO_ERROR: fileMover.moveFile(ApplicationPath.PathNew, ApplicationPath.PathError, event.getFileName());
                break;
            case MOVE_FROM_WORK_TO_PROCESSED: fileMover.moveFile(ApplicationPath.PathWork, ApplicationPath.PathProcessed, event.getFileName());
            break;
        }
    }
}
