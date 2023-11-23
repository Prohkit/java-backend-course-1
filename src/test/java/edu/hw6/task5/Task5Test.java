package edu.hw6.task5;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    @Test
    void task5() {
        HackerNews hackerNews = new HackerNews();
        long[] ids = hackerNews.hackerNewsTopStories();
        assertThat(ids)
            .isNotEmpty();
    }

    @Test void getNews() {
        HackerNews hackerNews = new HackerNews();
        long id = 37570037;
        String expected = "JDK 21 Release Notes";

        String news = hackerNews.news(id);

        assertThat(news)
            .isEqualTo(expected);
    }
}
