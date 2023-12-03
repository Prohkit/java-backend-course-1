package edu.hw8.task1.server;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuotesService {
    private final String filePath = "src/main/java/edu/hw8/task1/server/quotes";

    public String getQuote(String word) {
        Map<String, String> quotes = getQuotes();
        String quote = quotes.get(word);
        if (quote == null) {
            return "Не знаю цитаты с этим словом";
        }
        return quote;
    }

    public void addQuote(String word, String quote) {
        try {
            boolean needToAppend = true;
            var map = getQuotes();
            StringBuilder sb = new StringBuilder();
            if (map.containsKey(word)) {
                needToAppend = false;
                map.put(word, quote);
                map.forEach((mapWord, mapQuote) -> sb.append(mapWord.trim())
                    .append(':')
                    .append(mapQuote.trim())
                    .append(System.lineSeparator()));
            } else {
                sb.append(word.trim())
                    .append(':')
                    .append(quote.trim())
                    .append(System.lineSeparator());
            }
            FileWriter writer = new FileWriter(filePath, needToAppend);
            writer.write(new String(sb));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getQuotes() {
        try (Stream<String> stream = Files.lines(Path.of(filePath))) {
            return stream
                .map(string -> string.split(":"))
                .collect(Collectors.toMap(
                    strings -> strings[0],
                    strings -> strings[1]
                ));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
