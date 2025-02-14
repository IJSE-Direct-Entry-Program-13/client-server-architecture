package lk.ijse.dep13.protocol.shared.dto;

import lk.ijse.dep13.protocol.shared.util.Status;

import java.io.Serializable;

public record Response(Status status, String message) implements Serializable {
}
