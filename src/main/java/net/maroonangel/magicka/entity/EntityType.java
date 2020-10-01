package net.maroonangel.magicka.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.maroonangel.magicka.entity.projectile.MagicFireballEntity;
import net.maroonangel.magicka.entity.projectile.MagicHealingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.registry.Registry;

public class EntityType {

    //public static final net.minecraft.entity.EntityType<MagicFireballEntity> HEALING_PROJECTILE;

    public static void register() {

    }

    static {
        //HEALING_PROJECTILE = (net.minecraft.entity.EntityType)Registry.register(Registry.ENTITY_TYPE, "healing_projectile", FabricEntityTypeBuilder.create(SpawnGroup.MISC, MagicFireballEntity::new).build());
    }

}
