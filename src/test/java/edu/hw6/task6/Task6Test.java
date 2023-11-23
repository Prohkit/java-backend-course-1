package edu.hw6.task6;

import java.util.List;
import org.junit.jupiter.api.Test;
import static edu.hw6.task6.Protocol.TCP;
import static edu.hw6.task6.Protocol.UDP;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @Test
    void task6() {
        Task6 task6 = new Task6();
        List<Port> knownPorts = task6.getKnownPortsWithDescription();

        List<Port> expected = List.of(
            new Port(135, TCP, "EPMAP"),
            new Port(137, UDP, "Служба имен NetBIOS"),
            new Port(138, UDP, "Служба датаграмм NetBIOS"),
            new Port(139, TCP, "Служба сеансов NetBIOS"),
            new Port(445, TCP, "Microsoft-DS Active Directory"),
            new Port(843, TCP, "Adobe Flash"),
            new Port(1900, UDP, "Simple Service Discovery Protocol (SSDP"),
            new Port(3702, UDP, "Динамическое обнаружение веб-служб"),
            new Port(5353, UDP, "Многоадресный DNS"),
            new Port(5355, UDP, "Link-Local Multicast Name Resolution (LLMNR)"),
            new Port(17500, TCP, "Dropbox"),
            new Port(17500, UDP, "Dropbox"),
            new Port(27017, TCP, "MongoDB"),
            new Port(5432, TCP, "PostgreSQL"),
            new Port(5432, UDP, "PostgreSQL"),
            new Port(3389, TCP, "RDP"),
            new Port(22, TCP, "SSH"),
            new Port(22, UDP, "SSH"),
            new Port(443, TCP, "HTTPS"),
            new Port(443, UDP, "HTTPS")
        );

        assertThat(knownPorts)
            .isEqualTo(expected);
    }
}
