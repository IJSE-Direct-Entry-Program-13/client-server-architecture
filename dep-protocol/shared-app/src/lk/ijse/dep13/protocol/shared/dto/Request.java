package lk.ijse.dep13.protocol.shared.dto;

import lk.ijse.dep13.protocol.shared.util.Command;

import java.io.Serializable;

public record Request(Command command, User user) implements Serializable {
}
