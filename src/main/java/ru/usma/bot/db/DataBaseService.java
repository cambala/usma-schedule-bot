package ru.usma.bot.db;

public class DataBaseService {

    public static boolean hasGroup(String group) {
        return DataBase.getGroups().contains(group);
    }
}
