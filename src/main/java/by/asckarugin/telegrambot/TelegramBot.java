package by.asckarugin.telegrambot;

import by.asckarugin.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final RateLimiterService rateLimiterService;

    @Value("${telegram.bot.name}")
    private String name;

    @Value("${telegram.bot.token}")
    private String token;

    public TelegramBot(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){

            long chatId = update.getMessage().getChatId();
            String getFirstName = update.getMessage().getChat().getFirstName();

            String greetings = "Привет "+getFirstName+" твой chatId: "+chatId;

            sendMessage(chatId, greetings);
        }

    }

    public void sendMessage(long chatId, String textToSend){
        try{
            rateLimiterService.requestFromQuota();

            SendMessage sendMessage = new SendMessage();
            sendMessage.disableWebPagePreview();
            sendMessage.enableHtml(true);
            sendMessage.setChatId(chatId);
            sendMessage.setText(textToSend);

            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
