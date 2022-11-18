package app.controller;

import app.repository.ContributorRepository;
import app.repository.SponsorRepository;
import lajavel.Controller;
import lajavel.Response;
import lajavel.View;

import java.util.Map;

public class IndexController extends Controller {

    public void index(Response response) {
        response.html(
                View.make(
                        "index",
                        Map.entry("sponsors", SponsorRepository.getAll()),
                        Map.entry("stephane", ContributorRepository.get(1))
                )
        );
    }
}
