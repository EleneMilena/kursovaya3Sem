package ru.makridina.kursovaya.util;

import java.text.SimpleDateFormat;

public class DateTimeUtil {
    public static SimpleDateFormat getCustomFormat()
    {
        return new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss.SSS'Z'");
    }
}
