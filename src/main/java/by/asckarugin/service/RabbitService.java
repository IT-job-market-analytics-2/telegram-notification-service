package by.asckarugin.service;

import by.asckarugin.dto.TelegramNotificationDto;
import by.asckarugin.telegrambot.TelegramBot;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {
    private final TelegramBot telegramBot;

    @Autowired
    public RabbitService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @RabbitListener(queues = "telegram-notification-queue")
    public void listen(TelegramNotificationDto message){
        telegramBot.sendMessage(message.getChatId(), message.getMessage());
    }
}
