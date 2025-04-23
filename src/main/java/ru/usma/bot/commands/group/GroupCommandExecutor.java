package ru.usma.bot.commands.group;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import ru.usma.bot.db.DataBaseService;
import ru.usma.bot.files.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GroupCommandExecutor {

    public static final String GROUP_CMD = "/group";

    private static final String EMPTY_GROUP_MSG = """
            Укажи номер группы после команды\n
            Пример: `/group ОП-101`
            """;

    private static final String WRONG_GROUP_MSG = """
            Я не смог найти в базе данных группу с таким номером 😱
            Пожалуйста, проверь корректность ввода и попробуй ещё раз
            """;

    private static final Function<Long, SendMessage> ERROR_MSG =
            (Long chatId) -> SendMessage.builder()
                    .chatId(chatId)
                    .text(EMPTY_GROUP_MSG)
                    .build();

    public static SendMessage group(long chatId, String messageText) {
        String[] parts = messageText.split(GROUP_CMD, 2);

        SendMessage message;
        if (parts.length >= 2 && !parts[1].isEmpty()) {
            String groupName = parts[1].trim();
            if (DataBaseService.hasGroup(groupName)) {
                message = SendMessage.builder()
                        .chatId(chatId)
                        .text(String.format("""
                                ✅ Вот ссылка на календарь группы %s:
                                ```%s```
                                
                                Пожалуйста, скопируй её и открой инструкцию, чтобы продолжить настройку
                                """, groupName.replace("-", "\\-"), FileService.getCalendarLink(groupName)))
                        .build();
                message.enableMarkdownV2(true);

                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rows = new ArrayList<>();

                InlineKeyboardButton instructionButton = new InlineKeyboardButton();
                instructionButton.setText("Инструкция 📱");
                instructionButton.setWebApp(new WebAppInfo("https://cambala.github.io"));

                List<InlineKeyboardButton> row = new ArrayList<>();
                row.add(instructionButton);
                rows.add(row);

                inlineKeyboard.setKeyboard(rows);
                message.setReplyMarkup(inlineKeyboard);

            } else {
                message = SendMessage.builder()
                        .chatId(chatId)
                        .text(WRONG_GROUP_MSG)
                        .build();
            }

        } else {
            message = ERROR_MSG.apply(chatId);
            message.enableMarkdownV2(true);
        }

        return message;
    }
}
