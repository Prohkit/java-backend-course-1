package edu.hw2.task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PopularCommandExecutor {

    private final static Logger LOGGER = LogManager.getLogger();
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() throws Exception {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) throws Exception {
        boolean isCommandExecuted = false;
        ConnectionException connectionException = null;
        for (int i = 1; i <= maxAttempts; i++) {
            try (Connection connection = manager.getConnection()) {
                LOGGER.info("Try to connect...");
                connection.execute(command);
            } catch (ConnectionException e) {
                connectionException = e;
                LOGGER.info(e.getMessage());
                continue;
            }
            isCommandExecuted = true;
            break;
        }
        if (!isCommandExecuted) {
            throw new ConnectionException("The connection was not established.", connectionException);
        }
    }
}
