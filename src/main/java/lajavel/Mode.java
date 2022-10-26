package lajavel;

public enum Mode {
    DEVELOPMENT(3),
    TEST(1),
    PRODUCTION(0);

    public final int level;

    Mode(int level) {
        this.level = level;
    }
 }
