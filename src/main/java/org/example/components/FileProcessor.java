package org.example.components;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.FileProcessorConfig;
import org.example.enums.FileStatus;
import org.example.event.EventProducer;
import org.example.entity.FileEntity;
import org.example.kafka.LineDTO;
import org.example.kafka.LineTopicProducer;
import org.example.enums.ApplicationPath;
import org.example.event.Event;
import org.example.enums.EventType;
import org.example.repository.FileProcessingConfigRepository;
import org.example.repository.FileRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class FileProcessor implements Runnable {

    private final FileProcessorConfig config;
    private final FileRepository fileRepository;
    private final EventProducer eventProducer;
    private final LineTopicProducer lineTopicProducer;
    private final FileProcessingConfigRepository configRepository;



    @Override
    public void run() {
        changeStatus(FileStatus.PROCESSING);
        log.info("Processing file: " + config.getName());

        try (CSVReader reader = readerConfig()) {
            String[] fields;
            int lineNumber = 0;

            reader.readNext();
            while ((fields = reader.readNext()) != null) {
                if (fields.length != 5) {
                    eventProducer.produceEvent(new Event(EventType.MOVE_FROM_WORK_TO_ERROR, config.getName()));
                    changeStatus(FileStatus.ERROR);
                    throw new IllegalArgumentException("Invalid CSV format");
                }
                lineNumber++;
                sendLine(lineNumber, fields);
            }

            int linesAmount = lineNumber;
            FileEntity fileWithAmountOfLines = fileRepository.findById(nameToID())
                    .orElseThrow(() -> new IllegalStateException("Can not find file!"));
            fileWithAmountOfLines.setLinesAmount(linesAmount);
            fileRepository.save(fileWithAmountOfLines);
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Finished processing file: " + config.getName());
        changeStatus(FileStatus.FINISHED);
        eventProducer.produceEvent(new Event(EventType.MOVE_FROM_WORK_TO_PROCESSED, config.getName()));
    }



    public void changeStatus(FileStatus newStatus) {
        config.setStatus(newStatus);
        configRepository.save(config);
    }

    public void sendLine(Integer lineNumber,String[] fields){
        lineTopicProducer.send(new LineDTO(nameToID(), lineNumber, fields[0], fields[1], Integer.parseInt(fields[2]), Double.parseDouble(fields[3]), fields[4]));
    }

     public Long nameToID(){
         String id = config.getName();
         String numberString = id.replace(".csv", "");

         return Long.parseLong(numberString);
     }

    public CSVReader readerConfig() throws FileNotFoundException {
      return new CSVReaderBuilder(new FileReader(ApplicationPath.PathWork.getPath()+config.getName())).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();
    }

}

