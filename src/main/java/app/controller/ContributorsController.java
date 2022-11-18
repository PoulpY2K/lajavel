package app.controller;

import app.repository.ContributorRepository;
import lajavel.Controller;
import lajavel.Response;
import lajavel.View;

import java.util.Map;

public class ContributorsController extends Controller {
    public void index(Response response) {
        response.html(
                View.make("contributors",
                        Map.entry("contributors", ContributorRepository.getAll())
                )
        );
    }
}
