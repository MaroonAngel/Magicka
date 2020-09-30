package net.maroonangel.magicka.gen;

import com.mojang.serialization.Codec;
import net.maroonangel.magicka.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.Random;

public class ManaFlowerFeature extends Feature {
    public ManaFlowerFeature(Codec configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, FeatureConfig config) {
        BlockPos topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos);
        BlockPos belowPos = topPos.down();

        if (world.getBlockState(belowPos).getBlock() == net.minecraft.block.Blocks.GRASS_BLOCK)
            world.setBlockState(topPos, Blocks.MANA_FLOWER.getDefaultState(), 3);
        return true;
    }
}
