package ru.usma.bot.db;

import java.util.Set;

public class DataBase {

    private static final Set<String> GROUPS = Set.of("ОП-301", "ОП-303");

    public static Set<String> getGroups() {
        return GROUPS;
    }
}
