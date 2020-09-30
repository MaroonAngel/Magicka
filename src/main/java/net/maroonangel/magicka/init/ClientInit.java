package net.maroonangel.magicka.init;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.maroonangel.magicka.Magicka;
import net.maroonangel.magicka.block.Blocks;
import net.maroonangel.magicka.block.ImbuingTableBlock;
import net.maroonangel.magicka.block.ImbuingTableBlockEntity;
import net.maroonangel.magicka.block.ImbuingTableBlockEntityRenderer;
import net.maroonangel.magicka.particle.BloopParticle;
import net.maroonangel.magicka.particle.BloopParticle.SpriteFactory;
import net.maroonangel.magicka.screen.ImbuingTableScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ClientInit implements ClientModInitializer {

    public static DefaultParticleType BLOOP;


    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MANA_FLOWER, RenderLayer.getCutout());

        //BLOOP = Registry.register(Registry.PARTICLE_TYPE, new Identifier(Magicka.MODID, "bloop"), FabricParticleTypes.simple(true));
        //ParticleFactoryRegistry.getInstance().register(BLOOP, SpriteFactory::new);

        BlockEntityRendererRegistry.INSTANCE.register(Blocks.IMBUING_TABLE_BLOCK_ENTITY, ImbuingTableBlockEntityRenderer::new);
        ScreenRegistry.register(Magicka.IMBUING_TABLE_SCREEN_HANDLER, ImbuingTableScreen::new);
    }
}
