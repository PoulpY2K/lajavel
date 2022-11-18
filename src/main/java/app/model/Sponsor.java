package app.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Sponsor {
    private static final AtomicInteger count = new AtomicInteger(0);
    public final int id;
    public String name;
    public String imageUrl;
    public String websiteUrl;
    public Date startDate;

    public Sponsor(String name, String imageUrl, String websiteUrl, Date startDate) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.imageUrl = imageUrl;
        this.websiteUrl = websiteUrl;
        this.startDate = startDate;
    }
}
