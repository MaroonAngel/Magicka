package net.maroonangel.magicka.mana;

public class ManaManager {
    public int mana = 1000;
    public int maxMana = 1000;
    public boolean active = false;
    public int activating = 0;

    public ManaManager() {

    }

    public void activate() {
        if (active)
            return;

        active = true;
        activating = 60;
        mana = 1000;
    }

    public void update() {
        if (!active)
            return;

        if (activating > 0) {
            mana -= 20;
            activating--;
        }


        if (mana > 0)
            mana -= 3;
        if (mana < 0)
            mana = 0;
    }
}
