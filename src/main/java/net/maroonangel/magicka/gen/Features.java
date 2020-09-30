package net.maroonangel.magicka.gen;

import net.maroonangel.magicka.Magicka;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class Features {

    private static final ManaFlowerFeature MANA_FLOWER = new ManaFlowerFeature(DefaultFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> MANA_FLOWER_CONFIGURED = MANA_FLOWER.configure(FeatureConfig.DEFAULT)
            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(20)));

    public static void register() {
        Registry.register(Registry.FEATURE, new Identifier(Magicka.MODID, "mana_flower"), MANA_FLOWER);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Magicka.MODID, "mana_flower"), MANA_FLOWER_CONFIGURED);
    }

}
