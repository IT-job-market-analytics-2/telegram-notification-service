package by.asckarugin.service;

import by.asckarugin.dto.TelegramNotificationDto;
import by.asckarugin.telegrambot.TelegramBot;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {
    private final TelegramBot telegramBot;

    public RabbitService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.telegram-notifications}")
    public void listen(TelegramNotificationDto message){
        telegramBot.sendMessage(message.getChatId(), message.getMessage());
    }
}
