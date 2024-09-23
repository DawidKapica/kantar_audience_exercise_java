package com.kantar.sessionsjob.sessionparser;

import java.time.format.DateTimeFormatter;

public class ApplicationConstants {
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final String FIELD_SEPARATOR = "|";
}
