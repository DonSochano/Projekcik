package org.example.mover;

import org.example.enums.ApplicationEnum;


public interface FileMover<E extends ApplicationEnum> {
     void moveFile(E basePath, E destinationPath, String fileName);
}
