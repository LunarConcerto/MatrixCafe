package cafe.lunarconcerto.matrixcafe.api.data.user;

public enum Authorization {

    BLOCK(1),

    UNFRIENDLY(2),

    ORDINARY(3),

    ADMINISTRATOR(4),

    MASTER(5);

    final int level;

    Authorization(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
