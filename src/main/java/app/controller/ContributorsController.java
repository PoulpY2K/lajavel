package app.controller;

import lajavel.Controller;
import lajavel.Response;
import lajavel.View;

public class ContributorsController extends Controller {
    public void index(Response response) {
        response.html(View.make("contributors"));
    }
}
