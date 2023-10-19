package edu.hw2.task3;

import java.util.Random;

public class DefaultConnectionManager implements ConnectionManager {
    private static final int BAD_CONNECTION_PERCENT = 30;
    private static final int ONE_HUNDRED = 100;

    @Override
    public Connection getConnection() {
        Random random = new Random();
        if (random.nextInt(ONE_HUNDRED) < BAD_CONNECTION_PERCENT) {
            return new FaultyConnection();
        }
        return new StableConnection();
    }
}
