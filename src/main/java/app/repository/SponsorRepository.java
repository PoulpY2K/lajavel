package app.repository;

import app.model.Sponsor;
import lajavel.Log;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// SINGLETON WITH ENUM INSTANCE
// https://stackoverflow.com/questions/26285520/implementing-singleton-with-an-enum-in-java

public enum SponsorRepository {
    INSTANCE;

    private final List<Sponsor> sponsors;

    SponsorRepository() {
        Sponsor sponsor = new Sponsor("MyDigitalSchool",
                "https://www.lacuisineduweb.com/wp-content/uploads/2020/01/logo-grand-detoure.png",
                "https://www.mydigitalschool.com/",
                new Date());
        this.sponsors = List.of(sponsor);
    }

    public static Sponsor get(int id) {
        Optional<Sponsor> sponsor = SponsorRepository.INSTANCE.sponsors.stream()
                .filter(c -> c.id == id)
                .findFirst();

        if (sponsor.isPresent()) {
            return sponsor.get();
        } else {
            Log.error("No sponsor found with id " + id);
            throw new IndexOutOfBoundsException("No sponsor found with id " + id);
        }
    }

    public static List<Sponsor> getAll() {
        return SponsorRepository.INSTANCE.sponsors;
    }

    public static Sponsor add(Sponsor sponsor) {
        SponsorRepository.INSTANCE.sponsors.add(sponsor);
        return sponsor;
    }

    public static Sponsor update(Sponsor sponsor) {
        Sponsor oldContributor = SponsorRepository.get(sponsor.id);

        SponsorRepository.INSTANCE.sponsors.set(
                SponsorRepository.INSTANCE.sponsors.indexOf(oldContributor),
                sponsor
        );

        return sponsor;
    }

    public static void remove(int id) {
        SponsorRepository.INSTANCE.sponsors.remove(SponsorRepository.get(id));
    }
}
