package net.maroonangel.magicka.mana;

import net.maroonangel.magicka.mana.ManaManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

public interface IManaManager {
    public ManaManager manaManager = new ManaManager();

    public ManaManager getManager();

}
