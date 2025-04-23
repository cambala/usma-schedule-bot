package ru.usma.bot.commands.start;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StartCommandExecutor {

    public static String START_CMD = "/start";

    public static SendMessage start(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("""
            Привет\\! Я бот расписания УГМУ 😊
            Я помогу тебе в несколько простых шагов настроить твой календарь
            для получения и автоматического обновления расписания занятий твоей группы\\!
            
            Пожалуйста, отправь мне команду /group с номером своей группы для начала настройки
            
            Пример: `/group ОП\\-101`
            """);
        message.enableMarkdownV2(true);
        return message;
    }
}
