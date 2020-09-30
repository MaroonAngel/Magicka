package net.maroonangel.magicka.mana;

public class ManaManager {
    public int mana = 0;
    public int maxMana = 1000;
    public ManaManager() {

    }

    public void update() {
        if (mana > 0)
            mana--;
    }
}
