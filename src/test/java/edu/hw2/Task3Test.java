package edu.hw2;

import edu.hw2.task3.Connection;
import edu.hw2.task3.ConnectionManager;
import edu.hw2.task3.DefaultConnectionManager;
import edu.hw2.task3.FaultyConnection;
import edu.hw2.task3.FaultyConnectionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("DefaultConnectionManager возвращает объект класса, реализующего Connection")
    void getConnectionFromDefaultConnectionManager() {
        // given
        ConnectionManager connectionManager = new DefaultConnectionManager();

        // when
        Connection connection = connectionManager.getConnection();

        // then
        assertThat(connection)
            .isInstanceOf(Connection.class);
    }

    @Test
    @DisplayName("FaultyConnectionManager возвращает объект FaultyConnection")
    void getFaultyConnectionFromFaultyConnectionManager() {
        // given
        ConnectionManager connectionManager = new FaultyConnectionManager();

        // when
        Connection connection = connectionManager.getConnection();

        // then
        assertThat(connection)
            .isInstanceOf(FaultyConnection.class);
    }
}
