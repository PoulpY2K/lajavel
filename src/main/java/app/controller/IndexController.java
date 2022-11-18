package app.controller;

import app.model.Sponsor;
import app.repository.SponsorRepository;
import lajavel.Controller;
import lajavel.Response;
import lajavel.View;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class IndexController extends Controller {

    public void index(Response response) {
        SponsorRepository.add(new Sponsor("Sponsor 1", "test", "url", new Date()));
        SponsorRepository.add(new Sponsor("Sponsor 2", "test", "url", new Date()));

        response.html(View.make("index", Map.entry("sponsors", SponsorRepository.getAll())));
    }
}
