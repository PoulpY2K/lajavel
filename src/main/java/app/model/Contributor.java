package app.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Contributor {
    private static final AtomicInteger count = new AtomicInteger(0);
    public final int id;
    public String imageUrl;
    public String firstName;
    public String lastName;
    public String githubUrl;
    public String contributionPercentage;

    public Contributor(String firstName, String imageUrl, String lastName, String githubUrl, String contributionPercentage) {
        this.id = count.incrementAndGet();
        this.imageUrl = imageUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.githubUrl = githubUrl;
        this.contributionPercentage = contributionPercentage;
    }
}
