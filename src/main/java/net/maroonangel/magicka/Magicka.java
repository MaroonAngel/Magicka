package net.maroonangel.magicka;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.maroonangel.magicka.block.Blocks;
import net.maroonangel.magicka.gen.Features;
import net.maroonangel.magicka.item.Items;
import net.maroonangel.magicka.mana.Mana;
import net.maroonangel.magicka.recipe.Recipes;
import net.maroonangel.magicka.screen.ImbuingTableScreenHandler;
import net.maroonangel.magicka.spell.Spells;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Magicka implements ModInitializer {

	public static final String MODID = "magicka";
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, "general"), () -> new ItemStack(net.minecraft.item.Items.CORNFLOWER));
	public static final ScreenHandlerType<ImbuingTableScreenHandler> IMBUING_TABLE_SCREEN_HANDLER;

	@Override
	public void onInitialize() {
		Items.register();
		Blocks.register();
		Features.register();
		Recipes.register();
		Spells.register();

		//Registry.register(Registry.ENCHANTMENT)
	}

	static {
		IMBUING_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MODID, "imbuing_table"), ImbuingTableScreenHandler::new);
	}
}
