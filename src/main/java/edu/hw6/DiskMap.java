package edu.hw6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiskMap extends HashMap<String, String> implements Map<String, String> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String filePath;

    public DiskMap(String filePath) {
        this.filePath = filePath;
        createFile(filePath);
    }

    public void save() {
        try (PrintWriter printWriter = new PrintWriter(filePath)) {
            for (Entry<String, String> mapEntry : this.entrySet()) {
                printWriter.println(mapEntry.getKey() + ":" + mapEntry.getValue());
            }
        } catch (FileNotFoundException e) {
            LOGGER.info("Файл " + filePath + ", не найден");
        }
    }

    public void load() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.lines()
                .map(line -> line.split(":"))
                .forEach(line -> this.put(line[0], line[1]));
        } catch (IOException e) {
            LOGGER.info("Не удалось получить доступ к файлу: " + filePath);
        }
    }

    private void createFile(String filePath) {
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            LOGGER.info("Ошибка при создании файла: " + filePath);
        }
    }
}
