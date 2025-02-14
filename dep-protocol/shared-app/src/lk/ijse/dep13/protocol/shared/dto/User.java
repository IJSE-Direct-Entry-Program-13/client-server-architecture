package lk.ijse.dep13.protocol.shared.dto;

import java.io.Serializable;

public record User(String username, String password) implements Serializable {
}
