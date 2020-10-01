package net.maroonangel.magicka.spell;

import net.maroonangel.magicka.entity.projectile.MagicHealingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HealingSpell extends Spell {
    protected HealingSpell(Rarity weight, SpellTarget type, EquipmentSlot[] slotTypes, int manaCost) {
        super(weight, type, slotTypes, manaCost);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void cast(ItemStack stack, World world, PlayerEntity playerEntity) {
        Vec3d newPos = playerEntity.getPos(); //.crossProduct(new Vec3d(0, playerEntity.yaw, playerEntity.pitch));
        MagicHealingEntity healingball = new MagicHealingEntity(world, (LivingEntity)playerEntity, playerEntity.getRotationVec(0f).x, playerEntity.getRotationVec(0f).y, playerEntity.getRotationVec(0f).z);
        healingball.updatePosition(newPos.x, newPos.y+1.4, newPos.z);
        healingball.setPower(SpellHelper.getLevel(this, stack));
        healingball.setProperties(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0F, 2.5F + (float) 0 * 0.5F, 1.0F);
        world.spawnEntity(healingball);
        world.playSoundFromEntity(playerEntity, healingball, SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1.0F, 1.0F);
        /*
        */
        useMana(playerEntity, stack, world);
    }




}
