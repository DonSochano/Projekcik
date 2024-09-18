package org.example.projekcik1;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FileProcessor {

    private final KafkaProducer kafkaProducerService;
    private final FileRepository fileRepository;
    private final EventProducer eventProducer;


    public String newFile(MultipartFile file) throws IOException { // bez catcha, oraz trzeba zrobić to tak aby ten Bean nie przechowywał usingFile, FileRepository bo ten serwis jest singletonowy i moze byc uzywany w róznych miejscach, wiec jeden uzytkownik tego seriwsu przypisze jedne plik, a inny uzytkownik zaraz go nadpisze, on nie moze przechowywac pliku !

        File newFile = new File("src/main/resources/new/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            fos.write(file.getBytes());
        }
        return "";
    }

    public void processing(File file) {
        Long longID = null;
        Integer lineNumber = 0;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file.getPath())).withCSVParser(new CSVParserBuilder().withSeparator(',').build()).build()) {
            String[] fields;
            System.out.println("Ilosc linii przed while: " + lineNumber);
            reader.readNext();
            while ((fields = reader.readNext()) != null) {
                //  if (fields.length != 5) {
                //      throw new IllegalArgumentException("Invalid CSV format");
                //  }
                System.out.println("Ilosc linii przed if: " + lineNumber);
                lineNumber++;
                System.out.println("Ilosc linii pod dodaniu linii: " + lineNumber);
                String id = file.getName();
                String numberString = id.replace(".csv", "");
                System.out.println("Nazwa: " + file.getName());
                longID = Long.parseLong(numberString);
                System.out.println("Pole 1: " + fields[0]);
                kafkaProducerService.sendLineInfoMessage(new KafkaMessege(longID, lineNumber, fields[0], fields[1], Integer.parseInt(fields[2]), Double.parseDouble(fields[3]), fields[4]));
            }
            Integer linesAmount = lineNumber;
            lineNumber = 0;
            FileClass fileWithAmountOfLines = fileRepository.findById(longID).orElseThrow(() -> new IllegalStateException("Nie znalazło pliku!"));
            fileWithAmountOfLines.setLinesAmount(linesAmount);
            fileRepository.save(fileWithAmountOfLines);
            eventProducer.produceEvent(new Event("MoveFromWorkToProcessed", file.getName()));
            System.out.println("Wysłąny event moveToWork dla pliku: " + file.getName());
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void processing1(File usingFile) throws IOException {
        File newFileDirectory = new File("src/main/resources/work/" + usingFile.getName());

        processing(newFileDirectory);
    }


    public String Processed(MultipartFile file) { //  nazwy metod z małej !!!

        File processedFile = new File("src/main/resources/processed/" + file.getOriginalFilename());
        return "";

    }
}


//catch(Exception e){
//        e.printStackTrace();
//
//File errorFile = new File("src/main/resources/error/" + file.getOriginalFilename());
////  file.transferTo(errorFile);
//            return "Błąd podczas przetwarzania pliku: " + e.getMessage();
//        }
//                return "Plik przetworzony pomyślnie!";
//      }
