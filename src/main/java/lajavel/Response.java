package lajavel;

import io.javalin.http.Context;

import javax.servlet.http.HttpServletRequest;

public class Response {
    private Context context;
    public final HttpServletRequest request;

    public Response(Context context) {
        this.context = context;
        this.request = context.req;
    }

    public Response html(String html) {
        this.context.html(html);
        return this;
    }

    public Response result(String content) {
        this.context.result(content);
        return this;
    }

    public Response contentType(String type) {
        this.context.contentType(type);
        return this;
    }

    public Response header(String name, String value) {
        this.context.header(name, value);
        return this;
    }

    public void redirect(String url) {
        this.context.redirect(url);
    }

    public void json(Object object) {
        this.context.json(object);
    }

    public void status(int code) {
        this.context.status(code);
    }
}
