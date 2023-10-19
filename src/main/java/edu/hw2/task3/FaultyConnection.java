package edu.hw2.task3;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {

    private final static Logger LOGGER = LogManager.getLogger();

    private String closingMessage = "The connection was closed.";

    @Override
    public void execute(String command) {
        Random random = new Random();
        // проблемное соединение иногда бросает ConnectionException
        if (random.nextBoolean()) {
            closingMessage = "Closing the faulty connection.";
            throw new ConnectionException("Error, connection was lost.");
        } else {
            LOGGER.info("Connection is active, " + command + " done.");
        }
    }

    @Override
    public void close() throws Exception {
        LOGGER.info(closingMessage);
    }
}
