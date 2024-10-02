package org.example.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApplicationPath {

    // elementy enuma deklarujemy po przecinku, za ostatnim '
    Path1("tu jakiś path do tych twoich work, error itd jako osobne elementy enuma");

    @Getter
    private final String path; // to zwrócie : tu jakiś path do tych twoich work, error itd jako osobne elementy enuma

}
