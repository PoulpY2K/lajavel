package app.controller;

import lajavel.Controller;
import lajavel.Response;
import lajavel.View;

public class GetStartedController extends Controller {
    public void index(Response response) {
        response.html(View.make("getstarted"));
    }
}
