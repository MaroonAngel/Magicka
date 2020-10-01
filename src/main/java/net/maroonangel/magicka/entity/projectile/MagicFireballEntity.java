package net.maroonangel.magicka.entity.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class MagicFireballEntity extends AbstractMagicEntity {
    private int age = 0;

    @Environment(EnvType.CLIENT)
    public MagicFireballEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(EntityType.FIREBALL, x, y, z, velocityX, velocityY, velocityZ, world);
    }

    public MagicFireballEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(EntityType.FIREBALL, owner, velocityX, velocityY, velocityZ, world);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
            this.world.createExplosion((Entity)null, this.getX(), this.getY(), this.getZ(), (float)this.power, false, Explosion.DestructionType.NONE);
            this.remove();
        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() == this.getOwner())
            return;

        super.onEntityHit(entityHitResult);
        if (!this.world.isClient) {
            Entity entity = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
            entity.damage(DamageSource.fireball(this, entity2), this.power * 2f);
            if (entity2 instanceof LivingEntity) {
                this.dealDamage((LivingEntity)entity2, entity);
            }

        }
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("ExplosionPower", this.power);
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("ExplosionPower", 99)) {
            this.power = tag.getInt("ExplosionPower");
        }
    }

    @Override
    public ItemStack getStack() {
        return null;
    }
}
