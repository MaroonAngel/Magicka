package net.maroonangel.magicka.item;

import net.maroonangel.magicka.mana.IManaManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.World;

public class StewItem extends Item {
    public StewItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 160));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100));
        user.addStatusEffect(new StatusEffectInstance(net.maroonangel.magicka.entity.effect.StatusEffects.GAIN_MANA, 160));

        if (user instanceof PlayerEntity) {
            PlayerEntity pe = (PlayerEntity)user;
            IManaManager manaplayer = (IManaManager)pe;
            manaplayer.getManager().activate();
        }

        return user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode ? stack : new ItemStack(Items.BOWL);
    }
}
