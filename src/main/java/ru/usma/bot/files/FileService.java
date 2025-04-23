package ru.usma.bot.files;

public class FileService {

    private static final String HOST_URL = "http://89.232.170.175";
    private static final String CALENDAR_FILE_TYPE = ".ics";

    public static String getCalendarLink(String group) {
        return HOST_URL + "/" + group + CALENDAR_FILE_TYPE;
    }
}
