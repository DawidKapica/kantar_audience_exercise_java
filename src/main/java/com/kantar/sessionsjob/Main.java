package com.kantar.sessionsjob;


import com.kantar.sessionsjob.sessionparser.StatementsProcessor;
import lombok.extern.log4j.Log4j2;

import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
public class Main {

    // See README.md for usage example

    public static void main(String[] args) {
        if (!validateArguments(args))
            System.exit(1);

        String inputStatementsPath = args[0];
        String outputPath = args[1];

        StatementsProcessor statementsProcessor = new StatementsProcessor();
        long startTime = System.currentTimeMillis();
        statementsProcessor.readAndProcess(inputStatementsPath, outputPath);
        long stopTime = System.currentTimeMillis();
        log.trace("processing time {}ms",stopTime - startTime);
    }

    private static boolean validateArguments(String[] args) {
        if (args.length != 2) {
            help();
            return false;
        } else if (!Files.exists(Path.of(args[0]))) {
            log.warn("passed path does not exits path {}", args[0]);
            return false;
        }
        return true;
    }

    private static void help() {
        log.warn("passed arguments were wrong");
        log.info("example usage -  java -jar target/SessionsJob-1.0.jar 'src/test/resources/input-statements.psv' 'target/actual-sessions.psv'\"");
    }
}
