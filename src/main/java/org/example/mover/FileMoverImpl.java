package org.example.mover;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.ApplicationEnum;
import org.example.enums.ApplicationPath;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.*;

@Slf4j
@Service
public class FileMoverImpl implements FileMover<ApplicationPath> {

        @Override
        public void moveFile(ApplicationPath basePath, ApplicationPath destinationPath, String fileName) {

            Path basePathConverted = Paths.get(basePath.getPath() + fileName);
            Path destinationPathConverted = Paths.get(destinationPath.getPath() + fileName);

            // Otwarcie kanału na pliku źródłowym
            try (FileChannel sourceChannel = FileChannel.open(basePathConverted, StandardOpenOption.READ, StandardOpenOption.WRITE);
                 // Próba uzyskania blokady na plik
                 FileLock lock = sourceChannel.lock()) {

                if (destinationPathConverted.toFile().exists()) {
                    destinationPathConverted = Paths.get(ApplicationPath.PathDuplicated.getPath() + fileName);
                }

                Files.move(basePathConverted, destinationPathConverted);
                log.info("File moved successfully to: " + destinationPath.getPath());

            } catch (IOException e) {
                log.error("Error while moving file: " + e.getMessage());
            }
        }
}



