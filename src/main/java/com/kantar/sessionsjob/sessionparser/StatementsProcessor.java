package com.kantar.sessionsjob.sessionparser;

import com.kantar.sessionsjob.sessionparser.exceptions.LoadingRecordException;
import com.kantar.sessionsjob.sessionparser.exceptions.SaveSessionException;
import com.kantar.sessionsjob.sessionparser.records.Session;
import com.kantar.sessionsjob.sessionparser.records.Statement;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class StatementsProcessor {
    private static final String HEADER_OUTPUT = "HomeNo|Channel|Starttime|Activity|EndTime|Duration";
    private static final char LINE_SEPARATOR = '\n';

    public void readAndProcess(String inputStatementsPath, String outputSessionsPath) {
        long startTime = System.currentTimeMillis();

        Map<Integer, List<Statement>> records = loadRecord(Path.of(inputStatementsPath));
        long loadingRecordsFinishTime = System.currentTimeMillis();

        generateAndSaveSessions(records, outputSessionsPath);
        long generatedAndSavedSessions = System.currentTimeMillis();
        log.trace("times - reading lines {}ms generating and saving sessions {}ms", loadingRecordsFinishTime - startTime, generatedAndSavedSessions - loadingRecordsFinishTime);
    }

    private Map<Integer, List<Statement>> loadRecord(Path path) {
        log.info("start read statement from path {}", path);
        Map<Integer, List<Statement>> records;

        try (Stream<String> stream = Files.lines(path)) {
            records = stream.skip(1)
                    .map(Statement::createStatement)
                    .parallel()
                    .collect(Collectors.groupingBy(Statement::homeNo));
        } catch (IOException e) {
            log.error("exception {} during reading statements from {}", e.getMessage(), path);
            throw new LoadingRecordException(e);
        }
        return records;
    }

    private void generateAndSaveSessions(Map<Integer, List<Statement>> groupedStatements, String savePath) {
        log.info("start generate and save session to path {}", savePath);
        List<Integer> homeNos = groupedStatements.keySet()
                .stream()
                .sorted(Comparator.comparing(Object::toString))
                .toList();
        groupedStatements.entrySet()
                .stream()
                .parallel()
                .forEach(k -> k.getValue().sort(Comparator.comparing(Statement::startTime)));

        try (FileWriter fw = getFileWrite(savePath, true); Writer writer = getBufferedWriter(fw)) {
            writer.write(HEADER_OUTPUT + LINE_SEPARATOR);
            for (Integer homeNo : homeNos) {
                writeSessionsForSingleHouseNo(groupedStatements.get(homeNo), writer);
            }
        } catch (IOException e) {
            log.error("exception {} during saving sessions to path {}", e.getMessage(), savePath);
            throw new SaveSessionException(e);
        }
    }

    private void writeSessionsForSingleHouseNo(List<Statement> statements, Writer writer) throws IOException {
        for (int i = 0; i < statements.size(); i++) {
            Session session = Session.createSession(statements.get(i), i + 1 == statements.size() ? null : statements.get(i + 1));
            writer.write(session.toString() + LINE_SEPARATOR);
        }
    }

    // default modifier for tests
    Writer getBufferedWriter(FileWriter fileWriter) {
        return new BufferedWriter(fileWriter);
    }

    // default modifier for tests
    FileWriter getFileWrite(String savePath, boolean append) throws IOException {
        return new FileWriter(savePath, append);
    }
}
