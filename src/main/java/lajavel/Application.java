package lajavel;

import io.javalin.Javalin;

public class Application {

    private static Application instance;
    public Javalin server;
    public int port;
    public Mode mode;
    private Application(int port, Mode mode) {
        this.port = port;
        this.mode = mode;
        this.server = Javalin.create().start(this.port);
    }

    /**
     * Start the application
     * @param port The port to run the application on
     */
    public static void start(int port) {
        start(port, Mode.PRODUCTION);
    }

    /**
     * @param port The port to run the application on
     * @param mode The mode of the application (DEVELOPMENT, TEST, PRODUCTION)
     * @return
     */
    public static Application start(int port, Mode mode) {
        if(instance == null) {
            instance = new Application(port, mode);
        } else {
            throw new RuntimeException("Application already started");
        }
        return instance;
    }

    public static Application getInstance() {
        if (instance == null) {
            throw new RuntimeException("Application not started");
        }
        return instance;
    }
}
