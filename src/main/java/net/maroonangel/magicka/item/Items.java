package net.maroonangel.magicka.item;

import net.maroonangel.magicka.Magicka;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {

    public static final StewItem MANA_STEW = new StewItem(new Item.Settings().group(Magicka.GROUP).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1f).alwaysEdible().build()));
    public static final StaffItem WOODEN_STAFF = new StaffItem(new Item.Settings().group(Magicka.GROUP).maxCount(1).maxDamage(1000), 1);
    public static final Item MANA_PETALS = new Item(new Item.Settings().group(Magicka.GROUP));



    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(Magicka.MODID, "mana_stew"), MANA_STEW);
        Registry.register(Registry.ITEM, new Identifier(Magicka.MODID, "wooden_staff"), WOODEN_STAFF);
        Registry.register(Registry.ITEM, new Identifier(Magicka.MODID, "mana_petals"), MANA_PETALS);

    }

}
