package net.maroonangel.magicka.spell;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class RegenSpell extends Spell {
    protected RegenSpell(Rarity weight, SpellTarget type, EquipmentSlot[] slotTypes, int manaCost) {
        super(weight, type, slotTypes, manaCost);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void cast(ItemStack stack, World world, PlayerEntity playerEntity) {

        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 5*20, SpellHelper.getLevel(this, stack) - 1));

        useMana(playerEntity, stack, world);
    }
}
