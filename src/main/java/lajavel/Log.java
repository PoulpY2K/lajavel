package lajavel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private enum Level {
        DEBUG(3),
        INFO(2),
        WARNING(1),
        ERROR(0);

        public final int level;

        Level(int level) {
            this.level = level;
        }
    }

    public static Log instance;

    private final Logger logger;

    private Log() {
        this.logger = LoggerFactory.getLogger("lajavel");
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public static void debug(String message) {
        Log.print(Level.DEBUG, message);
    }

    public static void info(String message) {
        Log.print(Level.INFO, message);
    }

    public static void warn(String message) {
        Log.print(Level.WARNING, message);
    }

    public static void error(String message) {
        Log.print(Level.ERROR, message);
    }

    private static void print(Level level, String message) {
        int applicationLevel = Application.getInstance().mode.level;

        switch (level) {
            case DEBUG:
                if (applicationLevel >= Level.DEBUG.level)
                    getInstance().logger.debug(message);
                break;
            case INFO:
                if (applicationLevel >= Level.INFO.level)
                    getInstance().logger.info(message);
                break;
            case WARNING:
                if (applicationLevel >= Level.WARNING.level)
                    getInstance().logger.warn(message);
                break;
            case ERROR:
                if (applicationLevel >= Level.ERROR.level)
                    getInstance().logger.error(message);
                break;
        }
    }
}
