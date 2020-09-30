package net.maroonangel.magicka.spell;

import net.maroonangel.magicka.item.StaffItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public enum SpellTarget {
    STAVES {
        public boolean isAcceptableItem(Item item) {
            return item instanceof StaffItem;
        }
    },
}
