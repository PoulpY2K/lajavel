package app.model;

import java.util.Date;

public class Sponsor extends Individual {
    public String websiteUrl;
    public Date startDate;

    public Sponsor(String name, String imageUrl, String websiteUrl, Date startDate) {
        super(name, imageUrl);
        this.websiteUrl = websiteUrl;
        this.startDate = startDate;
    }
}
