package com.kantar.sessionsjob.sessionparser;

import com.kantar.sessionsjob.sessionparser.records.Statement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class StatementTest {

    @Test
    public void createStatementFromString() {
        String statementRecordLine = "1234|101|20200101180000|Live";

        Statement statement = Statement.createStatement(statementRecordLine);

        Assertions.assertEquals(1234, statement.homeNo());
        Assertions.assertEquals(101, statement.channel());
        Assertions.assertEquals(LocalDateTime.of(2020, 01, 01, 18, 00, 00), statement.startTime());
        Assertions.assertEquals(Activity.Live, statement.activity());
    }
}
