package net.maroonangel.magicka.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class MagicHealingEntity extends AbstractMagicEntity {

    int age = 0;

    public MagicHealingEntity(double d, double e, double f, double g, double h, double i, World world) {
        super(EntityType.FIREBALL, d, e, f, g, h, i, world);
    }

    public MagicHealingEntity(World world, LivingEntity livingEntity, double d, double e, double f) {
        super(EntityType.FIREBALL, livingEntity, d, e, f, world);
    }

    public MagicHealingEntity(EntityType entityType, World world) {
        super((EntityType<? extends AbstractFireballEntity>)entityType, world);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.remove();
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.world.isClient) {
            LivingEntity entity = (LivingEntity)entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
            entity.applyStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 10, this.power));
        }
    }

    public void tick() {
        super.tick();
        age++;
    }


    public void updateParticles(double d, double e, double f) {
        int yo = 3468117;

        double x = (double)(yo >> 16 & 255) / 255.0D;
        double y = (double)(yo >> 8 & 255) / 255.0D;
        double z = (double)(yo >> 0 & 255) / 255.0D;

        double a = Math.sin(this.age);
        double b = Math.cos(this.age);

        for (int i = 0; i < 3; i++)
            this.world.addParticle(this.getParticleType(), d + a, e + 0.5D + b, f + a, x, y, z);
    }

    protected ParticleEffect getParticleType() {
        return ParticleTypes.ENTITY_EFFECT;
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(Items.SLIME_BALL);
    }

    @Override
    protected ItemStack getItem() {
        return new ItemStack(Items.SLIME_BALL);
    }



    /*
    public void tick() {
        Entity entity = this.getOwner();
        if (this.world.isClient || (entity == null || !entity.removed) && this.world.isChunkLoaded(this.getBlockPos())) {
            super.tick();
        }
    }
     */
}
