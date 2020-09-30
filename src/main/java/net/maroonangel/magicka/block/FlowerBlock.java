package net.maroonangel.magicka.block;

import net.maroonangel.magicka.Magicka;
import net.maroonangel.magicka.init.ClientInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Random;

public class FlowerBlock extends net.minecraft.block.FlowerBlock {

    public FlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }

    @Override
    public VoxelShape getOutlineShape (BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
        VoxelShape shape = Block.createCuboidShape(3, 0, 3, 13, 14, 13);
        return shape;
    }

}
