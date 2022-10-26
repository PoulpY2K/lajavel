package app.model;

public abstract class Individual {
    public String name;
    public String imageUrl;

    public Individual(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
