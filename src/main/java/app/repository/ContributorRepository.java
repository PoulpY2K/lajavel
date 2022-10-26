package app.repository;

import app.model.Contributor;
import lajavel.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// INSTANCE
// https://stackoverflow.com/questions/26285520/implementing-singleton-with-an-enum-in-java

public enum ContributorRepository {
    INSTANCE;
    private final List<Contributor> contributors;

    private ContributorRepository() {
        this.contributors = new ArrayList<>();
    }

    public static Contributor get(int id) {
        Optional<Contributor> contributor = ContributorRepository.INSTANCE.contributors.stream()
                .filter(c -> c.id == id)
                .findFirst();

        if (contributor.isPresent()) {
            return contributor.get();
        } else {
            Log.error("No contributor found with id " + id);
            throw new IndexOutOfBoundsException("No contributor found with id " + id);
        }
    }

    public static List<Contributor> getAll() {
        return ContributorRepository.INSTANCE.contributors;
    }

    public static Contributor add(Contributor contributor) {
        ContributorRepository.INSTANCE.contributors.add(contributor);
        return contributor;
    }

    public static Contributor update(Contributor contributor) {
        Contributor oldContributor = ContributorRepository.get(contributor.id);

        ContributorRepository.INSTANCE.contributors.set(
                ContributorRepository.INSTANCE.contributors.indexOf(oldContributor),
                contributor
        );

        return contributor;
    }

    public static void remove(int id) {
        ContributorRepository.INSTANCE.contributors.remove(ContributorRepository.get(id));
    }
}
