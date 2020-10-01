package net.maroonangel.magicka.item;


import net.maroonangel.magicka.mana.IManaManager;
import net.maroonangel.magicka.mana.ManaManager;
import net.maroonangel.magicka.spell.Spell;
import net.maroonangel.magicka.spell.SpellHelper;
import net.maroonangel.magicka.spell.Spells;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Vanishable;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class StaffItem extends RangedWeaponItem implements Vanishable {
    int regen = 1;
    int index = 0;
    Spell currentSpell;

    public StaffItem(Settings settings, int regenRate) {
        super(settings);
        this.regen = regenRate;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return null;
    }

    @Override
    public int getRange() {
        return 20;
    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (this.currentSpell == null)
            this.currentSpell = selectSpell(index, stack);
        if (world.isClient() && Screen.hasShiftDown()) {
            index++;
            if (index > stack.getEnchantments().size() - 1)
                index = 0;
            this.currentSpell = selectSpell(index, stack);
            PlayerEntity playerEntity = (PlayerEntity) user;
            playerEntity.sendMessage(new TranslatableText(Spells.getName(this.currentSpell)), true);
        } else {
            if (user instanceof PlayerEntity && this.currentSpell != null) {
                PlayerEntity playerEntity = (PlayerEntity) user;
                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                IManaManager inter = (IManaManager)playerEntity;
                if (i >= 10) {

                    if (inter.getManager().active && (inter.getManager().maxMana - inter.getManager().mana) >= this.currentSpell.manaCost || playerEntity.isCreative()) {
                        this.currentSpell.cast(stack, world, playerEntity);
                        //playerEntity.getItemCooldownManager().set(this, 20);
                    } else {
                        world.playSound(playerEntity, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 1F, 1F);
                    }

                }
            }
        }
    }

    private Spell selectSpell(int index, ItemStack stack) {
        return SpellHelper.getSpellAtIndex(stack, index);
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        boolean bl = itemStack.getDamage() < itemStack.getMaxDamage();
        if (!user.abilities.creativeMode && !bl) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

}
