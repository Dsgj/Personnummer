package support.log;

import java.util.logging.*;

public class SysLog {
    public static final Logger logger;

    static {
        logger = Logger.getLogger("Exception Log");
        logger.setUseParentHandlers(false); //Printar inte Exceptions i terminalen, men sparar stacktrace i loggfilen.
    }

    public static void init() {
        try {
            FileHandler fh = new FileHandler("ExceptionLogger.log");

            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info("Log initialized");
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception", e);

        }
    }
}