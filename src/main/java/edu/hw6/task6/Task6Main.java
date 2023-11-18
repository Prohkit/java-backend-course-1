package edu.hw6.task6;

import java.io.IOException;
import java.util.List;

public class Task6Main {
    private Task6Main() {
    }

    public static void main(String[] args) throws IOException {
        Task6 task6 = new Task6();
        List<Port> busyPorts = task6.scanBusyPorts();
        List<Port> knownPorts = task6.getKnownPortsWithDescription();
        List<Port> portsWithDescription = task6.getPortDescriptionIfKnownPort(busyPorts, knownPorts);
        task6.printPorts(portsWithDescription);
    }
}
