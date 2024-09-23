package com.kantar.sessionsjob.sessionparser;

import com.kantar.sessionsjob.sessionparser.records.Session;
import com.kantar.sessionsjob.sessionparser.records.Statement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionTest {

    @Test
    public void createSessionBaseOnTwoStatementsInSameDayTest() {
        Statement currentStatement = getStatementMock(1, (short) 1, LocalDateTime.of(2020, 01, 01, 18, 00, 00), Activity.Live);
        Statement nextHourStatement = getStatementMock(1, (short) 1, LocalDateTime.of(2020, 01, 01, 19, 00, 00), Activity.Live);

        Session session = Session.createSession(currentStatement, nextHourStatement);

        Assertions.assertEquals(LocalDateTime.of(2020, 01, 01, 18, 59, 59), session.endTime());
        Assertions.assertEquals(3600, session.duration());
    }

    @Test
    public void createSessionForNextStatementNullTest() {
        Statement currentStatement = getStatementMock(1, (short) 1, LocalDateTime.of(2020, 01, 01, 18, 00, 00), Activity.Live);

        Session session = Session.createSession(currentStatement, null);
        Assertions.assertEquals(LocalDateTime.of(2020, 01, 01, 23, 59, 59), session.endTime());
        Assertions.assertEquals(21600, session.duration());
    }

    @Test
    public void createSessionForNextStatementInNextDayTest() {
        Statement currentStatement = getStatementMock(1, (short) 1, LocalDateTime.of(2020, 01, 01, 18, 00, 00), Activity.Live);
        Statement nextDayStatement = getStatementMock(1, (short) 1, LocalDateTime.of(2020, 01, 02, 18, 00, 00), Activity.Live);

        Session session = Session.createSession(currentStatement, nextDayStatement);
        Assertions.assertEquals(LocalDateTime.of(2020, 01, 01, 23, 59, 59), session.endTime());
        Assertions.assertEquals(21600, session.duration());
    }

    private Statement getStatementMock(int homeNo, short channel, LocalDateTime localDateTime, Activity activity) {
        Statement statement = mock(Statement.class);
        when(statement.homeNo()).thenReturn(homeNo);
        when(statement.channel()).thenReturn(channel);
        when(statement.startTime()).thenReturn(localDateTime);
        when(statement.activity()).thenReturn(activity);

        return statement;
    }
}
