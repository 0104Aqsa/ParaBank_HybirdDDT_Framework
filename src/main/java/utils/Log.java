package utils;

import org.apache.log4j.Logger;

public class Log {
    public static Logger logger = Logger.getLogger(Log.class);

    public static void startTest(String testName) {
        logger.info("=== STARTING TEST: " + testName + " ===");
    }

    public static void endTest(String testName) {
        logger.info("=== ENDING TEST: " + testName + " ===");
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

}
