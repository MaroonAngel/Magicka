package net.maroonangel.magicka.item;

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
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100));

        return user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode ? stack : new ItemStack(Items.BOWL);
    }
}
