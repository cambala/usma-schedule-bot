package ru.usma.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.usma.bot.commands.group.GroupCommandExecutor;
import ru.usma.bot.commands.start.StartCommandExecutor;

import java.util.function.Function;

public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "UsmaScheduleBot";
    }

    @Override
    public String getBotToken() {
        return "7693066471:AAFAfi7Bdxj_8oSL8oD_C_cJOUQFC9_GfbA";
    }

    private static final Function<Long, SendMessage> ERROR_MSG =
            (Long chatId) -> SendMessage.builder()
                .chatId(chatId)
                .text("""
                    Мне жаль, но я не смог обработать твой запрос 🫠
                    Проверь правильность своего запроса и попробуй ещё раз""")
                .build();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            SendMessage message;

            if (messageText.equals(StartCommandExecutor.START_CMD)) {
                message = StartCommandExecutor.start(chatId);
            } else if (messageText.contains(GroupCommandExecutor.GROUP_CMD)) {
                message = GroupCommandExecutor.group(chatId, messageText);
            } else {
                message = ERROR_MSG.apply(chatId);
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
