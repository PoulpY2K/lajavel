package app.model;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Individual {
    private static final AtomicInteger count = new AtomicInteger(0);
    public final int id;
    public String name;
    public String imageUrl;

    public Individual(String name, String imageUrl) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
