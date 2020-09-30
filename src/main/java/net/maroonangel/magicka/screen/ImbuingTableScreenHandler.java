package net.maroonangel.magicka.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.maroonangel.magicka.Magicka;
import net.maroonangel.magicka.block.Blocks;
import net.maroonangel.magicka.item.StaffItem;
import net.maroonangel.magicka.recipe.ImbuingRecipe;
import net.maroonangel.magicka.recipe.Recipes;
import net.maroonangel.magicka.spell.Spells;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ImbuingTableScreenHandler extends ScreenHandler {

    protected final CraftingResultInventory output = new CraftingResultInventory();
    private final Inventory inventory;
    private final ScreenHandlerContext context;
    private final Random random;
    private final Property seed;

    private World world;

    private ImbuingRecipe recipe;

    public ImbuingTableScreenHandler(int syncId, PlayerInventory playerInventory)  {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public ImbuingTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(Magicka.IMBUING_TABLE_SCREEN_HANDLER, syncId);
        this.inventory = new SimpleInventory(3) {
            public void markDirty() {
                super.markDirty();
                ImbuingTableScreenHandler.this.onContentChanged(this);
            }
        };
        this.random = new Random();
        this.seed = Property.create();
        this.context = context;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(this.inventory, 0, 41, 8) {

        });
        this.addSlot(new Slot(this.inventory, 1, 41, 52) {

        });
        this.addSlot(new Slot(this.inventory, 2, 62, 30) {
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof StaffItem;
            }
        });
        this.addSlot(new Slot(this.output, 3, 119, 30) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }
            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                return ImbuingTableScreenHandler.this.onTakeOutput(player, stack);
            }
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return true;
            }
        });


        int k;
        for(k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }


    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return true;
    }


    protected ItemStack onTakeOutput(PlayerEntity player, ItemStack stack) {
        this.output.markDirty();
        ItemStack topStack = this.inventory.getStack(0);
        ItemStack bottomStack = this.inventory.getStack(1);
        ItemStack staffStack = this.inventory.getStack(2);
        topStack.decrement(1);
        bottomStack.decrement(1);
        staffStack.decrement(1);
        return stack;
    }


    protected boolean canUse(BlockState state) {
        return true;
    }


    public void updateResult()
    {
        ItemStack topStack = this.inventory.getStack(0);
        ItemStack bottomStack = this.inventory.getStack(1);
        ItemStack staffStack = this.inventory.getStack(2);

        if (!staffStack.isEmpty() && !topStack.isEmpty() && !bottomStack.isEmpty()) {
            if (recipeMatches(topStack, bottomStack, Items.BLAZE_POWDER, net.maroonangel.magicka.item.Items.MANA_PETALS)) {
                if (!staffStack.hasEnchantments()) {
                    ItemStack output = staffStack.copy();
                    output.addEnchantment(Spells.FIREBALL, 1);
                    this.output.setStack(0, output);
                }
            }
        } else {
            this.output.setStack(0, ItemStack.EMPTY);
        }






        /*
        List<ImbuingRecipe> list = this.world.getRecipeManager().getAllMatches(Recipes.IMBUING_TYPE, this.inventory, this.world);
        if (list.isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
        } else {
            this.recipe = (ImbuingRecipe) list.get(0);
            ItemStack itemStack = this.recipe.craft(this.inventory);
            this.output.setLastRecipe(this.recipe);
            this.output.setStack(0, itemStack);
        }

         */
    }

    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.inventory) {
            this.updateResult();
        }

    }

    public boolean recipeMatches(ItemStack stack1, ItemStack stack2, Item item1, Item item2) {

        if (stack1.getItem() == item1)
            if (stack2.getItem() == item2)
                return true;

        if (stack2.getItem() == item1)
            if (stack1.getItem() == item2)
                return true;

        return false;
    }

    public boolean onButtonClick(PlayerEntity player, int id) {
        /*
        ItemStack itemStack = this.inventory.getStack(0);
        ItemStack itemStack2 = this.inventory.getStack(1);
        int i = id + 1;
        if ((itemStack2.isEmpty() || itemStack2.getCount() < i) && !player.abilities.creativeMode) {
            return false;
        } else if (itemStack.isEmpty() || (player.experienceLevel < i) && !player.abilities.creativeMode) {
            return false;
        } else {
            this.context.run((world, blockPos) -> {
                ItemStack itemStack3 = itemStack;
                List<EnchantmentLevelEntry> list = null; //this.generateEnchantments(itemStack, id, this.enchantmentPower[id]);
                if (!list.isEmpty()) {
                    player.applyEnchantmentCosts(itemStack, i);
                    boolean bl = itemStack.getItem() == Items.BOOK;
                    if (bl) {
                        itemStack3 = new ItemStack(Items.ENCHANTED_BOOK);
                        CompoundTag compoundTag = itemStack.getTag();
                        if (compoundTag != null) {
                            itemStack3.setTag(compoundTag.copy());
                        }

                        this.inventory.setStack(0, itemStack3);
                    }

                    for(int k = 0; k < list.size(); ++k) {
                        EnchantmentLevelEntry enchantmentLevelEntry = (EnchantmentLevelEntry)list.get(k);
                        if (bl) {
                            EnchantedBookItem.addEnchantment(itemStack3, enchantmentLevelEntry);
                        } else {
                            itemStack3.addEnchantment(enchantmentLevelEntry.enchantment, enchantmentLevelEntry.level);
                        }
                    }

                    if (!player.abilities.creativeMode) {
                        itemStack2.decrement(i);
                        if (itemStack2.isEmpty()) {
                            this.inventory.setStack(1, ItemStack.EMPTY);
                        }
                    }

                    player.incrementStat(Stats.ENCHANT_ITEM);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ENCHANTED_ITEM.trigger((ServerPlayerEntity)player, itemStack3, i);
                    }

                    this.inventory.markDirty();
                    this.seed.set(player.getEnchantmentTableSeed());
                    this.onContentChanged(this.inventory);
                    world.playSound((PlayerEntity)null, blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
                }

            });
            return true;
        }

         */
        return true;
    }

    private List<EnchantmentLevelEntry> generateEnchantments(ItemStack stack, int slot, int level) {
        this.random.setSeed((long)(this.seed.get() + slot));
        List<EnchantmentLevelEntry> list = EnchantmentHelper.generateEnchantments(this.random, stack, level, false);
        if (stack.getItem() == Items.BOOK && list.size() > 1) {
            list.remove(this.random.nextInt(list.size()));
        }

        return list;
    }

    @Environment(EnvType.CLIENT)
    public int getLapisCount() {
        ItemStack itemStack = this.inventory.getStack(1);
        return itemStack.isEmpty() ? 0 : itemStack.getCount();
    }

    @Environment(EnvType.CLIENT)
    public int getSeed() {
        return this.seed.get();
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> {
            this.dropInventory(player, player.world, this.inventory);
        });
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, Blocks.IMBUING_TABLE);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == 1) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == 2) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (((Slot)this.slots.get(0)).hasStack() || !((Slot)this.slots.get(0)).canInsert(itemStack2)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemStack3 = itemStack2.copy();
                itemStack3.setCount(1);
                itemStack2.decrement(1);
                ((Slot)this.slots.get(0)).setStack(itemStack3);
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }
}

