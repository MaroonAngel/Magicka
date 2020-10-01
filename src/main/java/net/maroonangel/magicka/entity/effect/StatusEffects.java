package net.maroonangel.magicka.entity.effect;

import net.maroonangel.magicka.Magicka;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StatusEffects {

    public static StatusEffect GAIN_MANA;

    public static void register() {
        GAIN_MANA = Registry.register(Registry.STATUS_EFFECT, 32, "gain_mana", new ManaGainStatusEffect(StatusEffectType.BENEFICIAL, 254972));
    }


}
