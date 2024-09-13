package org.example.projekcik1;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "files")
public class FileClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // jak getter i setter to to powinno byc raczej prywatne, tak jak i wszystkie pola tej klasy
    String fileName;
    // entery pomiedzy polami !
    LocalDate detectionTime;
    Long fileSize;

public FileClass(String fileName, LocalDate detectionTime, Long fileSize) {
    this.fileName = fileName;
    this.detectionTime = detectionTime;
    this.fileSize = fileSize;
}
    public FileClass(LocalDate detectionTime, Long fileSize) { // nie uzywany konstruktor ? po co
        this.fileName = fileName;
        this.detectionTime = detectionTime;
    }

    public FileClass() {} // adnotacja NoArgsConstruktor i to do smieci

}
