package net.maroonangel.magicka.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public abstract class AbstractMagicEntity extends AbstractFireballEntity implements FlyingItemEntity {

    protected int power = 1;


    public AbstractMagicEntity(EntityType<? extends AbstractFireballEntity> entityType, double d, double e, double f, double g, double h, double i, World world) {
        super(entityType, d, e, f, g, h, i, world);
    }

    public AbstractMagicEntity(EntityType<? extends AbstractFireballEntity> entityType, LivingEntity livingEntity, double d, double e, double f, World world) {
        super(entityType, livingEntity, d, e, f, world);
    }

    public AbstractMagicEntity(EntityType<? extends AbstractFireballEntity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    public void setPower(int power) {
        this.power = power;
    }

    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult)hitResult);
        } else if (type == HitResult.Type.BLOCK) {
            this.onBlockHit((BlockHitResult)hitResult);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.world.isClient) {
            Entity entity = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
        }
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (this.world.isClient || (entity == null || !entity.removed) && this.world.isChunkLoaded(this.getBlockPos())) {

            HitResult hitResult = ProjectileUtil.getCollision(this, this::method_26958);
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
            }

            this.checkBlockCollision();
            Vec3d vec3d = this.getVelocity();
            double d = this.getX() + vec3d.x;
            double e = this.getY() + vec3d.y;
            double f = this.getZ() + vec3d.z;
            ProjectileUtil.method_7484(this, 0.2F);
            float g = this.getDrag();
            if (this.isTouchingWater()) {
                for(int i = 0; i < 4; ++i) {
                    float h = 0.25F;
                    this.world.addParticle(ParticleTypes.BUBBLE, d - vec3d.x * 0.25D, e - vec3d.y * 0.25D, f - vec3d.z * 0.25D, vec3d.x, vec3d.y, vec3d.z);
                }

                g = 0.8F;
            }

            this.setVelocity(vec3d.add(this.posX, this.posY, this.posZ).multiply((double)g));
            this.updateParticles(d, e, f);
            this.updatePosition(d, e, f);
        } else {
            this.remove();
        }
    }

    public void updateParticles(double d, double e, double f) {
        this.world.addParticle(this.getParticleType(), d, e + 0.5D, f, 0.0D, 0.0D, 0.0D);
    }

    protected ParticleEffect getParticleType() {
        return null;
    }

    @Override
    public ItemStack getStack() {
        return null;
    }

    @Override
    protected ItemStack getItem() {
        return null;
    }
}
