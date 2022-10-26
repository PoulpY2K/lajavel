package lajavel;

import io.javalin.http.Context;

public class Route {
    public String path;
    public HttpVerb verb;

    public Route(HttpVerb verb, String path) {
        this.path = path;
        this.verb = verb;
    }

    public static void register(HttpVerb verb, String path, Class<?> controllerClass, String methodName) {
        Application application = Application.getInstance();

        switch (verb) {
            case GET -> application.server.get(path, ctx -> Route.invokeController(ctx, controllerClass, methodName));
            case POST -> application.server.post(path, ctx -> Route.invokeController(ctx, controllerClass, methodName));
            case PUT -> application.server.put(path, ctx -> Route.invokeController(ctx, controllerClass, methodName));
            case DELETE ->
                    application.server.delete(path, ctx -> Route.invokeController(ctx, controllerClass, methodName));
            case PATCH ->
                    application.server.patch(path, ctx -> Route.invokeController(ctx, controllerClass, methodName));
        }
    }

    private static void invokeController(Context context, Class<?> controllerClass, String methodName) {
        try {
            Response response = new Response(context);

            Controller controller = (Controller) controllerClass.getDeclaredConstructor().newInstance();

            controllerClass.getMethod(methodName, Response.class).invoke(controller, response);
        } catch (Exception e) {
            Log.error(e.toString());
        }
    }
}
