package servlets.lab2;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerFactory {
    private static final String LOG_FILE = "lab2.log";
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler(System.getProperty("jboss.server.log.dir") + "/" + LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            Logger.getLogger(LoggerFactory.class.getName()).severe("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static Logger createLogger(String className) {
        Logger logger = Logger.getLogger(className);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.INFO);
        return logger;
    }
}
