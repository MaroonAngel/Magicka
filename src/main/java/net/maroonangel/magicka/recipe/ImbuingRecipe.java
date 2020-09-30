package net.maroonangel.magicka.recipe;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.maroonangel.magicka.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class ImbuingRecipe implements Recipe<Inventory> {
    private final Ingredient staff;
    private final Ingredient component1;
    private final Ingredient component2;
    private final ItemStack result;
    private final Identifier id;

    public ImbuingRecipe(Identifier id, Ingredient staff, Ingredient component1, Ingredient component2, ItemStack result) {
        this.id = id;
        this.staff = staff;
        this.component1 = component1;
        this.component2 = component2;
        this.result = result;
    }

    public boolean matches(Inventory inv, World world) {
        return this.staff.test(inv.getStack(0)) && this.component1.test(inv.getStack(1)) && this.component2.test(inv.getStack(2));
    }

    public ItemStack craft(Inventory inv) {
        ItemStack itemStack = this.result.copy();
        CompoundTag compoundTag = inv.getStack(0).getTag();
        if (compoundTag != null) {
            itemStack.setTag(compoundTag.copy());
        }

        return itemStack;
    }

    @Environment(EnvType.CLIENT)
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public ItemStack getOutput() {
        return this.result;
    }

    public boolean method_30029(ItemStack itemStack) {
        return this.component1.test(itemStack);
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getRecipeKindIcon() {
        return new ItemStack(Blocks.IMBUING_TABLE);
    }

    public Identifier getId() {
        return this.id;
    }

    public RecipeSerializer<?> getSerializer() {
        return Recipes.IMBUING;
    }

    public RecipeType<?> getType() {
        return Recipes.IMBUING_TYPE;
    }

    public static class Serializer implements RecipeSerializer<ImbuingRecipe> {
        public Serializer() {
        }

        public ImbuingRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "staff"));
            Ingredient ingredient2 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "component1"));
            Ingredient ingredient3 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "component2"));
            ItemStack itemStack = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
            return new ImbuingRecipe(identifier, ingredient, ingredient2, ingredient3, itemStack);
        }

        public ImbuingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            Ingredient ingredient2 = Ingredient.fromPacket(packetByteBuf);
            Ingredient ingredient3 = Ingredient.fromPacket(packetByteBuf);
            ItemStack itemStack = packetByteBuf.readItemStack();
            return new ImbuingRecipe(identifier, ingredient, ingredient2, ingredient3, itemStack);
        }

        public void write(PacketByteBuf packetByteBuf, ImbuingRecipe imbuingRecipe) {
            imbuingRecipe.staff.write(packetByteBuf);
            imbuingRecipe.component1.write(packetByteBuf);
            imbuingRecipe.component2.write(packetByteBuf);
            packetByteBuf.writeItemStack(imbuingRecipe.result);
        }
    }
}
