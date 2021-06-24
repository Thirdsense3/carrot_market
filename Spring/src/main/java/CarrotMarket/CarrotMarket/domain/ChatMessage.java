package CarrotMarket.CarrotMarket.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
public class ChatMessage {
    private int userId;
    private String message;
    private Timestamp time;
    private int roomIdl;
}
