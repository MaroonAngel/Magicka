package net.maroonangel.magicka.recipe;

import net.maroonangel.magicka.Magicka;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Recipes {

    public static RecipeSerializer<ImbuingRecipe> IMBUING; // = register("smithing", new net.minecraft.recipe.SmithingRecipe.Serializer());
    public static RecipeType<ImbuingRecipe> IMBUING_TYPE;;// = register("imbuing");

    public static void register() {
        IMBUING = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Magicka.MODID, "imbuing"), new ImbuingRecipe.Serializer());

        IMBUING_TYPE = Registry.register(Registry.RECIPE_TYPE,  new Identifier(Magicka.MODID, "imbuing"), new RecipeType<ImbuingRecipe>() {
            public String toString() {
                return "imbuing";
            }
        });

        /*
         */
    }
}
