package app.controller;

import lajavel.Controller;
import lajavel.Person;
import lajavel.Response;
import lajavel.View;

import java.util.Map;

public class IndexController extends Controller {

    public void index(Response response) {
        Person john = new Person("John", "Doe");
        Person mary = new Person("Mary", "Jane");

        response.html(View.make("index", Map.entry("john", john), Map.entry("mary", mary)));
    }
}
