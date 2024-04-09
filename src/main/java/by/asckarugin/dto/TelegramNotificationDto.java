package by.asckarugin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TelegramNotificationDto {

    private String message;

    @JsonProperty("chat_id")
    private Integer chatId;
}
