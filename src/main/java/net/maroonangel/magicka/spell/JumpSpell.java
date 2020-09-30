package net.maroonangel.magicka.spell;

import net.maroonangel.magicka.entity.projectile.MagicFireballEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class JumpSpell extends Spell {
    protected JumpSpell(Rarity weight, SpellTarget type, EquipmentSlot[] slotTypes, int manaCost) {
        super(weight, type, slotTypes, manaCost);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void cast(ItemStack stack, World world, PlayerEntity playerEntity) {

        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 15*20, SpellHelper.getLevel(this, stack)));

        useMana(playerEntity, stack, world);
    }
}
