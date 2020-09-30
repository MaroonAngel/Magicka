package net.maroonangel.magicka.mixin;

import net.maroonangel.magicka.mana.IManaManager;
import net.maroonangel.magicka.mana.ManaManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IManaManager {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="tick", at = @At(value ="TAIL"))
    public void tickMixin(CallbackInfo ci) {
        if (!super.world.isClient())
            manaManager.update();
    }

    @Override
    public ManaManager getManager() {
        return manaManager;
    }

}
