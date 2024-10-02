package org.example.components;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.FileEntity;
import org.example.kafka.KafkaMessage;
import org.example.kafka.LineTopicProducer;
import org.example.models.Event;
import org.example.repository.FileRepository;
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

    private final LineTopicProducer lineTopicProducerService;
    private final FileRepository fileRepository;
    private final EventProducer eventProducer;

    public String saveFile(MultipartFile file) throws IOException { // tą metode wyjebać do  FileService

        File newFile = new File("src/main/resources/new/" + file.getOriginalFilename()); // korzystac z ApplicationPath jak napiszesz ten enum
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            fos.write(file.getBytes());
        }

        // tutaj raczej własnie po zapisaniu powinno być wygenerowanie eventu który powie że w new pojawił się nowy plik
        // event consumer powiadomi file movera który przeniesie plik do work

        return "";
    }


    // tu sie zaczynają schody
    // to bedzie w klasie FileManager - on sobie schedulerowo będzie nasłuchiwał na folder work
    // do dodania nowa klasa właśnie FileProcessor oraz Klasa FIleProcessorConfig która będzie nowa encją na bazie danych, (id,, fileName, status - status moze byc enumem ) dodac podstawowe repo do tej klasy
    // Klasa FileProcessor Będzie przyjmowała w konstruktorze Config, EventProducer, i to co wysyła linijki na topic - może cos jeszcze okaże sie później
    //klasa File Manager będzie wstrzykiwała coś takiego jak GenericApplicationContext - to cały kontekst springa, tam są wszystkie beany w springu mozliwe do wyciągniecia i nowe do zarejestrowania

    // metoda schedulerowa w FileManager będize listować sobie wszystkie pliki z folderu work co pare sekund
    // korzstając z FileProcessingConfigRepository sprawdzac będzie po nazwach wylistowanych plików czy coś czy przypadkiem  nie pracuje nad tym plikiem - jak pracuje to nic nie robi
    // korzystajac z application context sprawdzi sobie też jakie ma procesory zarejestrowane w conteźcie i jak wygląda ich status na bazie danych - jezeli którys ma finish na bazie a istnieje w contexcie to usunac go z contextu
    // jezeli porówna te wyniki i znajdzie że jest plik nad którym nic nie pracuje to stworzy Config(randomoweID, nazwaPlikuNowego, Enum.New), zapisze go na bazie danych
    // stowrzy instanje klasy FileProcessor - FILE PROCESSOR NIE MOŻE BYC COMPONENTEM, SERVICEM ANI NICZYM TAKIM, ZWYKLA JAVOWA KLASA
    // za pomoca generic application context zarejestruje beana w contexcie springa właśnie FileProcessor - tam trzeba chyba podać nazwe beana to moze byc to randomowe id
    // nastepnie wywyoła metode run z file processor któa zacznie czytac plik i wysyłać na topic. w metodzie run ma na bazie zmienic sie status w configu z NEW na PROCESSING
    // jak file processor skończy czytac plik, ma zmienic status na FINISH na bazie oraz powiadomuje za pomocą eventu żeby przenieść plik do finish, albo error w zalezności od tego co sie stąło to później

// dodac kluczowe metody logowania, FileProcessor removed on finish, new File find, creating file processor, run file processor
    // podzielić na metody jakieś: register bean, createConfig, schedulerowa metoda moze nazywac sie list files

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
                lineTopicProducerService.send(new KafkaMessage(longID, lineNumber, fields[0], fields[1], Integer.parseInt(fields[2]), Double.parseDouble(fields[3]), fields[4]));
            }
            Integer linesAmount = lineNumber;
            lineNumber = 0;
            assert longID != null;
            FileEntity fileWithAmountOfLines = fileRepository.findById(longID).orElseThrow(() -> new IllegalStateException("Nie znalazło pliku!"));
            fileWithAmountOfLines.setLinesAmount(linesAmount);
            fileRepository.save(fileWithAmountOfLines);
            eventProducer.produceEvent(new Event("MoveFromWorkToProcessed", file.getName()));
            log.info("Wysłąny event moveToWork dla pliku: " + file.getName());
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}


