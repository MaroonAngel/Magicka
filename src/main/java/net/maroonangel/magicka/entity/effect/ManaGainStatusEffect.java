package net.maroonangel.magicka.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public class ManaGainStatusEffect extends StatusEffect {
    protected ManaGainStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        for (int i = 0; i < 15; i++) {

            int yo = this.getColor();

            double d = (double)(yo >> 16 & 255) / 255.0D;
            double e = (double)(yo >> 8 & 255) / 255.0D;
            double f = (double)(yo >> 0 & 255) / 255.0D;

            double c = entity.world.random.nextInt(200) - 100;
            double x = Math.cos(c) * 0.8f;
            double y = Math.sin(c) * 0.8f;

            entity.world.addParticle(ParticleTypes.ENTITY_EFFECT, entity.getX() + (x), entity.getY() + 0.2f, entity.getZ() + (y), d, e, f);
        }
    }
}
