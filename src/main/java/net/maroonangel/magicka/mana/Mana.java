package net.maroonangel.magicka.mana;

public class Mana {
    public static final Mana Instance = new Mana();

    public int max = 1000;
    public int value = 0;

    public int regen = 1;

    public void tick() {
        if (value > 0) {
            value -= regen;
        }
    }
}
