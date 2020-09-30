package net.maroonangel.magicka.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.maroonangel.magicka.Magicka;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks {

    public static final FlowerBlock MANA_FLOWER = new FlowerBlock(StatusEffects.NAUSEA, 5,
            FabricBlockSettings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).noCollision().lightLevel(6));

    public static final ImbuingTableBlock IMBUING_TABLE = new ImbuingTableBlock(FabricBlockSettings.of(Material.STONE).hardness(1f).lightLevel(3));
    public static BlockEntityType<ImbuingTableBlockEntity> IMBUING_TABLE_BLOCK_ENTITY;


    public static void register() {
        Registry.register(Registry.BLOCK, new Identifier(Magicka.MODID, "mana_flower"), MANA_FLOWER);
        Registry.register(Registry.ITEM, new Identifier(Magicka.MODID, "mana_flower"), new BlockItem(MANA_FLOWER, new Item.Settings().group(Magicka.GROUP)));

        Registry.register(Registry.BLOCK, new Identifier(Magicka.MODID, "imbuing_table"), IMBUING_TABLE);
        Registry.register(Registry.ITEM, new Identifier(Magicka.MODID, "imbuing_table"), new BlockItem(IMBUING_TABLE, new Item.Settings().group(Magicka.GROUP)));


        //IMBUING_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Magicka.MODID, "imbuing_table"),
        //        BlockEntityType.Builder.create(() -> new ImbuingTableBlockEntity::new, IMBUING_TABLE).build(null));

        IMBUING_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Magicka.MODID, "imbuing_table"),
                BlockEntityType.Builder.create(ImbuingTableBlockEntity::new, IMBUING_TABLE).build(null));

    }

}
