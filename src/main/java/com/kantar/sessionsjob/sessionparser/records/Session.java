package com.kantar.sessionsjob.sessionparser.records;

import com.kantar.sessionsjob.sessionparser.ApplicationConstants;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public record Session(Statement statement, LocalDateTime endTime, long duration) {
    public static Session createSession(Statement currentStatement, Statement nextStatement) {
        LocalDateTime endTime;

        if (checkIfStatementsAreNotRelated(currentStatement, nextStatement)) {
            endTime = currentStatement.startTime().toLocalDate().atTime(LocalTime.MIDNIGHT.minusSeconds(1));
        } else {
            endTime = nextStatement.startTime().minusSeconds(1);
        }
        return new Session(currentStatement, endTime, currentStatement.startTime().until(endTime, ChronoUnit.SECONDS) + 1);
    }

    private static boolean checkIfStatementsAreNotRelated(Statement currentStatement, Statement nextStatement) {
        return nextStatement == null
                || !currentStatement.startTime().toLocalDate().equals(nextStatement.startTime().toLocalDate())
                || currentStatement.homeNo() != nextStatement.homeNo();
    }

    @Override
    public String toString() {
        return statement.homeNo() + ApplicationConstants.FIELD_SEPARATOR
                + statement.channel() + ApplicationConstants.FIELD_SEPARATOR
                + statement.startTime().format(ApplicationConstants.DATE_TIME_FORMAT) + ApplicationConstants.FIELD_SEPARATOR
                + statement.activity() + ApplicationConstants.FIELD_SEPARATOR
                + endTime.format(ApplicationConstants.DATE_TIME_FORMAT) + ApplicationConstants.FIELD_SEPARATOR
                + duration;
    }
}
