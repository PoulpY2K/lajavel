package org.lajavel;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Application app = Application.start(7070);

        app.server.get("/", ctx -> ctx.html(View.make("index")));
    }
}