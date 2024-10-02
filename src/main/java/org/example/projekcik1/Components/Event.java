package org.example.projekcik1.Components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class Event implements CustomEvent {
    private String eventCommand;
    private String fileName;
}
