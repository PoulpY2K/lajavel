package org.lajavel;

import io.javalin.Javalin;

public class Application {

    private static Application instance;
    public Javalin server;
    public int port;
    private Application(int port) {
        this.port = port;
        this.server = Javalin.create().start(this.port);
    }

    public static Application start(int port) {
        if(instance == null) {
            instance = new Application(port);
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
