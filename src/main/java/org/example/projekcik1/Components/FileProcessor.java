package org.example.projekcik1.Components;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projekcik1.Entity.FileClass;
import org.example.projekcik1.Kafka.KafkaMessage;
import org.example.projekcik1.Kafka.KafkaProducer;
import org.example.projekcik1.Repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileProcessor {

    private final KafkaProducer kafkaProducerService;
    private final FileRepository fileRepository;
    private final EventProducer eventProducer;

    public String saveFile(MultipartFile file) throws IOException {

        File newFile = new File("src/main/resources/new/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            fos.write(file.getBytes());
        }
        return "";
    }

    public void processing(File file) {
        Long longID = null;
        Integer lineNumber = 0;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file.getPath())).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build()) {
            String[] fields;
            log.info("Ilosc linii przed while: " + lineNumber);
            reader.readNext();
            while ((fields = reader.readNext()) != null) {
                //  if (fields.length != 5) {
                //      throw new IllegalArgumentException("Invalid CSV format");
                //  }
                log.info("Ilosc linii przed if: " + lineNumber);
                lineNumber++;
                log.info("Ilosc linii pod dodaniu linii: " + lineNumber);
                String id = file.getName();
                String numberString = id.replace(".csv", "");
                log.info("Nazwa: " + file.getName());
                longID = Long.parseLong(numberString);
                log.info("Pole 1: " + fields[0]);
                kafkaProducerService.sendLineInfoMessage(new KafkaMessage(longID, lineNumber, fields[0], fields[1], Integer.parseInt(fields[2]), Double.parseDouble(fields[3]), fields[4]));
            }
            Integer linesAmount = lineNumber;
            lineNumber = 0;
            assert longID != null;
            FileClass fileWithAmountOfLines = fileRepository.findById(longID).orElseThrow(() -> new IllegalStateException("Nie znalazło pliku!"));
            fileWithAmountOfLines.setLinesAmount(linesAmount);
            fileRepository.save(fileWithAmountOfLines);
            eventProducer.produceEvent(new Event("MoveFromWorkToProcessed", file.getName()));
            log.info("Wysłąny event moveToWork dla pliku: " + file.getName());
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}


