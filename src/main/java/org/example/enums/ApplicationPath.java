package org.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationPath implements ApplicationEnum{


    PathNew("src/main/resources/new/"),
    PathWork("src/main/resources/work/"),
    PathError("src/main/resources/error/"),
    PathProcessed("src/main/resources/processed/"),
    PathDuplicated("src/main/resources/duplicated/");

    private final String path;

}
