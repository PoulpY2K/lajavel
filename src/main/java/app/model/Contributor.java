package app.model;

public class Contributor extends Individual {
    public String lastName;
    public String githubUrl;
    public int contributionPercentage;

    public Contributor(String name, String imageUrl, String lastName, String githubUrl, int contributionPercentage) {
        super(name, imageUrl);
        this.lastName = lastName;
        this.githubUrl = githubUrl;
        this.contributionPercentage = contributionPercentage;
    }
}
