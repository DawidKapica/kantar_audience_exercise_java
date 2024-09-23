package com.kantar.sessionsjob.sessionparser;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StatementsProcessorTest {

    private final Writer writer = mock(Writer.class);

    private final FileWriter fileWriter = mock(FileWriter.class);

    @Test
    public void generateDataBasedOnInputFilesTest() throws IOException {
        StatementsProcessor statementsProcessor = spy(new StatementsProcessor());
        doReturn(writer).when(statementsProcessor).getBufferedWriter(any());
        doReturn(fileWriter).when(statementsProcessor).getFileWrite(anyString(), anyBoolean());
        InOrder orderVerifier = Mockito.inOrder(writer);

        statementsProcessor.readAndProcess("src/test/resources/input-statements.psv", "");

        verify(writer, times(11)).write(anyString());
        orderVerifier.verify(writer).write(eq("HomeNo|Channel|Starttime|Activity|EndTime|Duration\n"));
        orderVerifier.verify(writer).write(eq("1234|603|20200101070000|PlayBack|20200101175959|39600\n"));
        orderVerifier.verify(writer).write(eq("1234|101|20200101180000|Live|20200101182959|1800\n"));
        orderVerifier.verify(writer).write(eq("1234|102|20200101183000|Live|20200101202959|7200\n"));
        orderVerifier.verify(writer).write(eq("1234|601|20200101203000|PlayBack|20200101235959|12600\n"));
        orderVerifier.verify(writer).write(eq("45678|621|20200101060000|PlayBack|20200101185959|46800\n"));
        orderVerifier.verify(writer).write(eq("45678|103|20200101190000|PlayBack|20200101192959|1800\n"));
        orderVerifier.verify(writer).write(eq("45678|104|20200101193000|Live|20200101205959|5400\n"));
        orderVerifier.verify(writer).write(eq("45678|104|20200101210000|PlayBack|20200101235959|10800\n"));
        orderVerifier.verify(writer).write(eq("777|602|20200101200000|Live|20200101235959|14400\n"));
        orderVerifier.verify(writer).write(eq("900|621|20200102060000|PlayBack|20200102235959|64800\n"));
    }
}
