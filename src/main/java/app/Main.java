package app;

import app.controller.IndexController;
import lajavel.*;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Application.start(7070, Mode.DEVELOPMENT);

        Log.debug("debug");
        Log.info("info");
        Log.warn("warn");
        Log.error("error");

        Route.register(HttpVerb.GET, "/", IndexController.class, "index");
    }
}