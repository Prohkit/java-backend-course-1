package edu.hw6.task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task6 {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MAX_PORT_NUMBER = 49151;

    @SuppressWarnings("EmptyBlock")
    public List<Port> scanBusyPorts() {
        List<Port> busyPorts = new ArrayList<>();
        for (int i = 0; i <= MAX_PORT_NUMBER; i++) {
            try (ServerSocket serverSocket = new ServerSocket(i)) {
            } catch (Exception e) {
                busyPorts.add(new Port(i, Protocol.TCP));
            }

            try (DatagramSocket datagramSocket = new DatagramSocket(i)) {
            } catch (Exception e) {
                busyPorts.add(new Port(i, Protocol.UDP));
            }
        }
        return busyPorts;
    }

    @SuppressWarnings("MagicNumber")
    public List<Port> getKnownPortsWithDescription() {
        try (Stream<String> knownPorts = Files.lines(Path.of("src/main/java/edu/hw6/knownPorts.txt"))) {
            int partsCount = 3;
            return knownPorts
                .map(string -> string.split(" ", partsCount))
                .map(strings -> new Port(Integer.parseInt(strings[1]), Protocol.valueOf(strings[0]), strings[2]))
                .toList();
        } catch (IOException e) {
            LOGGER.info("Не найден файл knownPorts.txt");
        }
        return null;
    }

    public List<Port> getPortDescriptionIfKnownPort(List<Port> busyPorts, List<Port> knownPorts) {
        List<Port> result = new ArrayList<>();
        for (Port busyPort : busyPorts) {
            if (knownPorts.contains(busyPort)) {
                result.add(knownPorts.get(knownPorts.indexOf(busyPort)));
            } else {
                result.add(busyPort);
            }
        }
        return result;
    }

    @SuppressWarnings("RegexpSinglelineJava")
    public void printPorts(List<Port> ports) {
        System.out.printf("%-10s%-10s%-1s%n", "Протокол", "Порт", "Сервис");
        for (Port port : ports) {
            if (port.getDescription() == null) {
                System.out.printf("%-10s%-10d%n", port.getProtocol(), port.getPortNumber());
            } else {
                System.out.printf("%-10s%-10d%-1s%n", port.getProtocol(), port.getPortNumber(), port.getDescription());
            }
        }
    }
}
