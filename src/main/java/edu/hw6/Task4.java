package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task4 {
    private static final Logger LOGGER = LogManager.getLogger();

    public void writeFile(Path path) {
        try (OutputStream outputStream = Files.newOutputStream(path)) {
            Checksum checksum = new CRC32();
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, checksum);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
            OutputStreamWriter outputStreamWriter =
                new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);
            PrintWriter printWriter = new PrintWriter(outputStreamWriter);
            printWriter.write("Programming is learned by writing programs. ― Brian Kernighan");
            printWriter.flush();
        } catch (IOException e) {
            LOGGER.info("Не найден файл");
        }
    }
}
