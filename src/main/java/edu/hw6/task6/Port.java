package edu.hw6.task6;

import java.util.Objects;

public class Port {
    private final Integer portNumber;
    private final Protocol protocol;

    private String description;

    public Port(Integer portNumber, Protocol protocol) {
        this.portNumber = portNumber;
        this.protocol = protocol;
    }

    public Port(Integer portNumber, Protocol protocol, String description) {
        this.portNumber = portNumber;
        this.protocol = protocol;
        this.description = description;
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getDescription() {
        return description;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Port port = (Port) o;
        return Objects.equals(portNumber, port.portNumber) && protocol == port.protocol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(portNumber, protocol);
    }
}
