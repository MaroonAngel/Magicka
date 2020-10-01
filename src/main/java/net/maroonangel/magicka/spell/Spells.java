package net.maroonangel.magicka.spell;

import net.maroonangel.magicka.Magicka;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Spells {

    public static FireballSpell FIREBALL = new FireballSpell(Enchantment.Rarity.COMMON, SpellTarget.STAVES, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, 200);
    public static JumpSpell JUMP = new JumpSpell(Enchantment.Rarity.COMMON, SpellTarget.STAVES, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, 500);
    public static RegenSpell REGEN = new RegenSpell(Enchantment.Rarity.UNCOMMON, SpellTarget.STAVES, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, 600);
    public static HealingSpell HEAL = new HealingSpell(Enchantment.Rarity.UNCOMMON, SpellTarget.STAVES, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, 350);

    public static void register() {

        Registry.register(Registry.ENCHANTMENT, new Identifier(Magicka.MODID, "fireball"), FIREBALL);
        Registry.register(Registry.ENCHANTMENT, new Identifier(Magicka.MODID, "jump"), JUMP);
        Registry.register(Registry.ENCHANTMENT, new Identifier(Magicka.MODID, "regen"), REGEN);
        Registry.register(Registry.ENCHANTMENT, new Identifier(Magicka.MODID, "heal"), HEAL);
    }

    public static String getName(Spell spell) {
        if (spell instanceof FireballSpell)
            return "Fireball";

        if (spell instanceof JumpSpell)
            return "Jump";

        if (spell instanceof RegenSpell)
            return "Regeneration";

        if (spell instanceof HealingSpell)
            return "Healing";


        return "";
    }


}
