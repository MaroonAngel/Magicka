package net.maroonangel.magicka.spell;

import net.maroonangel.magicka.mana.IManaManager;
import net.maroonangel.magicka.mana.Mana;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class Spell extends Enchantment {

    private final SpellTarget target;
    public int manaCost;

    protected Spell(Rarity weight, SpellTarget type, EquipmentSlot[] slotTypes, int manaCost) {
        super(weight, EnchantmentTarget.VANISHABLE, slotTypes);
        this.target = type;
        this.manaCost = manaCost;
    }

    public void cast(ItemStack stack, World world, PlayerEntity playerEntity) {

    }

    protected void useMana(PlayerEntity playerEntity, ItemStack stack, World world) {
        if (playerEntity instanceof ServerPlayerEntity && !playerEntity.isCreative()) {
            //Mana.Instance.value += this.manaCost;
            /*
            int damage = stack.getDamage();
            damage += this.manaCost;
            if (damage > stack.getMaxDamage())
                damage = stack.getMaxDamage();
            stack.setDamage(damage);
            */

            IManaManager mana = (IManaManager)playerEntity;
            mana.getManager().mana += this.manaCost;
            //stack.setDamage(mana.getManager().mana);

            //stack.damage(this.manaCost, world.random, (ServerPlayerEntity) playerEntity);

        }
    }


}
