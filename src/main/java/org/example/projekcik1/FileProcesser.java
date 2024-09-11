package org.example.projekcik1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;

@Service
public class FileProcesser {
    @Autowired
    KafkaProducer kafkaProducerService;


    private MultipartFile usingFile;
    private FileRepository fileRepository;

    public String newFile(MultipartFile file) {
        this.usingFile = file;
        File newFile = new File("src/main/resources/new/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            fos.write(file.getBytes());
        }
        catch (IOException e) {}
        return "";
    }
    public void moveToWork(String fileName) {
        File oldFileDirectory = new File("src/main/resources/new/" + fileName);
        File newFileDirectory = new File("src/main/resources/work/" + fileName);
         oldFileDirectory.renameTo(newFileDirectory);
        System.out.println("Plik: " + fileName + " został przeniesiony do: " + newFileDirectory.getPath());
        Processing1(newFileDirectory);
    }
    public String Processing(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            Integer lineNumber =0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] fields = line.split(";");
                System.out.println("Index 0: " +fields[0]);
              //  if (fields.length != 5) {
              //      throw new IllegalArgumentException("Invalid CSV format");
              //  }
                String id = file.getName();
                String numberString = id.replace(".csv", "");
                System.out.println("Nazwa: " + file.getName());
                Long longID = Long.parseLong(numberString);

                kafkaProducerService.sendMessage(new KafkaMessege(longID,lineNumber,fields[0],fields[1],Integer.parseInt(fields[2]),Double.parseDouble(fields[3]),fields[4]));
            }
            Integer linesAmount = lineNumber;

            lineNumber =0;
        }
        catch (IOException e) {
            System.out.println("Nie znalazło pliku kurwa");
        }
        return "";
    }

    public void Processing1(File usingFile){
        File newFileDirectory = new File("src/main/resources/work/" + usingFile.getName());
         Processing(newFileDirectory);

        }



public String Processed(MultipartFile file){

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