package app;

import app.controller.ContributorsController;
import app.controller.GetStartedController;
import app.controller.IndexController;
import lajavel.*;

public class Main {
    public static void main(String[] args) {
        Application.start(7070, Mode.DEVELOPMENT);

        Log.debug("debug");
        Log.info("info");
        Log.warn("warn");
        Log.error("error");

        Route.register(HttpVerb.GET, "/", IndexController.class, "index");
        Route.register(HttpVerb.GET, "/get-started", GetStartedController.class, "index");
        Route.register(HttpVerb.GET, "/contributors", ContributorsController.class, "index");
    }
}