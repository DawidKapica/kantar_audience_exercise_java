package com.kantar.sessionsjob.sessionparser.records;

import com.kantar.sessionsjob.sessionparser.Activity;
import com.kantar.sessionsjob.sessionparser.ApplicationConstants;

import java.time.LocalDateTime;

public record Statement(int homeNo, short channel, LocalDateTime startTime, Activity activity) {

    public static Statement createStatement(String statement) {
        String[] parts = statement.split("\\" + ApplicationConstants.FIELD_SEPARATOR);
        int homeNo = Integer.parseInt(parts[0]);
        short channel = Short.parseShort(parts[1]);
        LocalDateTime startTime = LocalDateTime.parse(parts[2], ApplicationConstants.DATE_TIME_FORMAT);
        Activity activity = Activity.valueOf(parts[3]);
        return new Statement(homeNo, channel, startTime, activity);
    }
}
