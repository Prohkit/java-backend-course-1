package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static java.net.http.HttpClient.newHttpClient;

public class HackerNews {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int TIME_BEFORE_REQUEST_INTERRUPTION = 10;

    public long[] hackerNewsTopStories() {
        try {
            HttpResponse<String> response =
                doRequest(new URI("https://hacker-news.firebaseio.com/v0/topstories.json"));

            return Arrays.stream(response.body().replaceAll("[\\[\\]]", "").split(","))
                .mapToLong(Long::parseLong)
                .toArray();
        } catch (URISyntaxException e) {
            LOGGER.info("Неверный адрес topstories");
        } catch (IOException e) {
            return new long[] {};
        }
        return null;
    }

    public String news(long id) {
        try {
            HttpResponse<String> response =
                doRequest(new URI("https://hacker-news.firebaseio.com/v0/item/" + id + ".json"));
            Pattern pattern = Pattern.compile("\"title\":\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(response.body());

            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (IOException e) {
            LOGGER.info("Ошибка ввода-вывода");
        } catch (URISyntaxException e) {
            LOGGER.info("Неверный адрес сообщения");
        }
        return null;
    }

    private HttpResponse<String> doRequest(URI uri) throws IOException {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .timeout(Duration.ofSeconds(TIME_BEFORE_REQUEST_INTERRUPTION))
                .build();

            return newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            LOGGER.info("Соединение прервано");
        }
        return null;
    }
}
