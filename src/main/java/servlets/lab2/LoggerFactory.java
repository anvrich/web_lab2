package servlets.lab2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.*;

import org.json.JSONObject;

public class LoggerFactory {
    private static final String LOG_FILE = "lab2.log";
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler(System.getProperty("jboss.server.log.dir") + "/" + LOG_FILE, true);
            fileHandler.setFormatter(new JsonFormatter());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(LoggerFactory.class.getName());
            logger.severe("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static Logger createLogger(String className) {
        Logger logger = Logger.getLogger(className);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.INFO);
        return logger;
    }

    public static void logInfo(Logger logger, String message, Map<String, Object> parameters) {
        JSONObject log = new JSONObject();
        log.put("timestamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
        log.put("level", "INFO");
        log.put("source", logger.getName());
        log.put("message", message);
        log.put("parameters", new JSONObject(parameters));

        logger.log(Level.INFO, log.toString(4));
    }


    private static class JsonFormatter extends Formatter {
        @Override
        public synchronized String format(LogRecord record) {
            JSONObject log = new JSONObject();

            log.put("timestamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(record.getMillis())));
            log.put("level", record.getLevel().getLocalizedName());
            log.put("source", record.getLoggerName());

            try {
                JSONObject messageJson = new JSONObject(record.getMessage());
                log.put("message", messageJson.optString("message", ""));
                log.put("parameters", messageJson.optJSONObject("parameters"));
            } catch (Exception e) {
                log.put("message", record.getMessage());
            }

            return log.toString(4) + "\n";
        }
    }
}
