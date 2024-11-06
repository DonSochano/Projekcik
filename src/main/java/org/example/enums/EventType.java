package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType implements ApplicationEnum {

    MOVE_FROM_NEW_TO_WORK,
    MOVE_FROM_WORK_TO_ERROR,
    MOVE_FROM_WORK_TO_PROCESSED,
    MOVE_FROM_NEW_TO_DUPLICATED;

}
