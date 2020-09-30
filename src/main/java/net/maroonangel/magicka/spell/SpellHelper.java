package net.maroonangel.magicka.spell;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.*;
import java.util.function.Predicate;

public class SpellHelper {
    public static int getLevel(Enchantment enchantment, ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Identifier identifier = Registry.ENCHANTMENT.getId(enchantment);
            ListTag listTag = stack.getEnchantments();

            for(int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                Identifier identifier2 = Identifier.tryParse(compoundTag.getString("id"));
                if (identifier2 != null && identifier2.equals(identifier)) {
                    return MathHelper.clamp(compoundTag.getInt("lvl"), 0, 255);
                }
            }

            return 0;
        }
    }

    public static Spell getFirstSpell(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        } else {
            //Identifier identifier = Registry.ENCHANTMENT.getId(enchantment);
            ListTag listTag = stack.getEnchantments();

            for(int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                Identifier identifier2 = Identifier.tryParse(compoundTag.getString("id"));
                if (identifier2 != null) {
                    Enchantment ench = Registry.ENCHANTMENT.get(identifier2);
                    if (ench instanceof Spell)
                        return (Spell)ench;
                }
                return null;
            }

            return null;
        }
    }

    public static Spell getSpellAtIndex(ItemStack stack, int index) {
        if (stack.isEmpty()) {
            return null;
        } else {
            //Identifier identifier = Registry.ENCHANTMENT.getId(enchantment);
            ListTag listTag = stack.getEnchantments();

            CompoundTag compoundTag = listTag.getCompound(index);
            Identifier identifier2 = Identifier.tryParse(compoundTag.getString("id"));
            if (identifier2 != null) {
                Enchantment ench = Registry.ENCHANTMENT.get(identifier2);
                if (ench instanceof Spell)
                    return (Spell)ench;
            }
            return null;
        }
    }

    public static List<Spell> getSpells(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        } else {
            //Identifier identifier = Registry.ENCHANTMENT.getId(enchantment);
            ListTag listTag = stack.getEnchantments();

            List<Spell> list = new ArrayList<Spell>();
            for(int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                Identifier identifier2 = Identifier.tryParse(compoundTag.getString("id"));
                if (identifier2 != null) {
                    Enchantment ench = Registry.ENCHANTMENT.get(identifier2);
                    if (ench instanceof Spell) {

                    }
                        list.add((Spell)ench);
                }

            }

            return list;
        }
    }

    public static Map<Enchantment, Integer> get(ItemStack stack) {
        ListTag listTag = stack.getItem() == Items.ENCHANTED_BOOK ? EnchantedBookItem.getEnchantmentTag(stack) : stack.getEnchantments();
        return fromTag(listTag);
    }

    public static Map<Enchantment, Integer> fromTag(ListTag tag) {
        Map<Enchantment, Integer> map = Maps.newLinkedHashMap();

        for(int i = 0; i < tag.size(); ++i) {
            CompoundTag compoundTag = tag.getCompound(i);
            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(compoundTag.getString("id"))).ifPresent((enchantment) -> {
                Integer var10000 = (Integer)map.put(enchantment, compoundTag.getInt("lvl"));
            });
        }

        return map;
    }

    public static void set(Map<Enchantment, Integer> enchantments, ItemStack stack) {
        ListTag listTag = new ListTag();
        Iterator var3 = enchantments.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<Enchantment, Integer> entry = (Map.Entry)var3.next();
            Enchantment enchantment = (Enchantment)entry.getKey();
            if (enchantment != null) {
                int i = (Integer)entry.getValue();
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putString("id", String.valueOf(Registry.ENCHANTMENT.getId(enchantment)));
                compoundTag.putShort("lvl", (short)i);
                listTag.add(compoundTag);
                if (stack.getItem() == Items.ENCHANTED_BOOK) {
                    EnchantedBookItem.addEnchantment(stack, new EnchantmentLevelEntry(enchantment, i));
                }
            }
        }

        if (listTag.isEmpty()) {
            stack.removeSubTag("Enchantments");
        } else if (stack.getItem() != Items.ENCHANTED_BOOK) {
            stack.putSubTag("Enchantments", listTag);
        }

    }

    private static void forEachEnchantment(SpellHelper.Consumer consumer, ItemStack stack) {
        if (!stack.isEmpty()) {
            ListTag listTag = stack.getEnchantments();

            for(int i = 0; i < listTag.size(); ++i) {
                String string = listTag.getCompound(i).getString("id");
                int j = listTag.getCompound(i).getInt("lvl");
                Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(string)).ifPresent((enchantment) -> {
                    consumer.accept(enchantment, j);
                });
            }

        }
    }

    private static void forEachEnchantment(SpellHelper.Consumer consumer, Iterable<ItemStack> stacks) {
        Iterator var2 = stacks.iterator();

        while(var2.hasNext()) {
            ItemStack itemStack = (ItemStack)var2.next();
            forEachEnchantment(consumer, itemStack);
        }

    }

    public static int getProtectionAmount(Iterable<ItemStack> equipment, DamageSource source) {
        MutableInt mutableInt = new MutableInt();
        forEachEnchantment((enchantment, level) -> {
            mutableInt.add(enchantment.getProtectionAmount(level, source));
        }, equipment);
        return mutableInt.intValue();
    }

    public static float getAttackDamage(ItemStack stack, EntityGroup group) {
        MutableFloat mutableFloat = new MutableFloat();
        forEachEnchantment((enchantment, level) -> {
            mutableFloat.add(enchantment.getAttackDamage(level, group));
        }, stack);
        return mutableFloat.floatValue();
    }

    public static float getSweepingMultiplier(LivingEntity entity) {
        int i = getEquipmentLevel(Enchantments.SWEEPING, entity);
        return i > 0 ? SweepingEnchantment.getMultiplier(i) : 0.0F;
    }

    public static void onUserDamaged(LivingEntity user, Entity attacker) {
        SpellHelper.Consumer consumer = (enchantment, level) -> {
            enchantment.onUserDamaged(user, attacker, level);
        };
        if (user != null) {
            forEachEnchantment(consumer, user.getItemsEquipped());
        }

        if (attacker instanceof PlayerEntity) {
            forEachEnchantment(consumer, user.getMainHandStack());
        }

    }

    public static void onTargetDamaged(LivingEntity user, Entity target) {
        SpellHelper.Consumer consumer = (enchantment, level) -> {
            enchantment.onTargetDamaged(user, target, level);
        };
        if (user != null) {
            forEachEnchantment(consumer, user.getItemsEquipped());
        }

        if (user instanceof PlayerEntity) {
            forEachEnchantment(consumer, user.getMainHandStack());
        }

    }

    public static int getEquipmentLevel(Enchantment enchantment, LivingEntity entity) {
        Iterable<ItemStack> iterable = enchantment.getEquipment(entity).values();
        if (iterable == null) {
            return 0;
        } else {
            int i = 0;
            Iterator var4 = iterable.iterator();

            while(var4.hasNext()) {
                ItemStack itemStack = (ItemStack)var4.next();
                int j = getLevel(enchantment, itemStack);
                if (j > i) {
                    i = j;
                }
            }

            return i;
        }
    }

    public static int getFireball(ItemStack stack) {
        return getLevel(Spells.FIREBALL, stack);
    }

    public static int getKnockback(LivingEntity entity) {
        return getEquipmentLevel(Enchantments.KNOCKBACK, entity);
    }

    public static int getFireAspect(LivingEntity entity) {
        return getEquipmentLevel(Enchantments.FIRE_ASPECT, entity);
    }

    public static int getRespiration(LivingEntity entity) {
        return getEquipmentLevel(Enchantments.RESPIRATION, entity);
    }

    public static int getDepthStrider(LivingEntity entity) {
        return getEquipmentLevel(Enchantments.DEPTH_STRIDER, entity);
    }

    public static int getEfficiency(LivingEntity entity) {
        return getEquipmentLevel(Enchantments.EFFICIENCY, entity);
    }

    public static int getLuckOfTheSea(ItemStack stack) {
        return getLevel(Enchantments.LUCK_OF_THE_SEA, stack);
    }

    public static int getLure(ItemStack stack) {
        return getLevel(Enchantments.LURE, stack);
    }

    public static int getLooting(LivingEntity entity) {
        return getEquipmentLevel(Enchantments.LOOTING, entity);
    }

    public static boolean hasAquaAffinity(LivingEntity entity) {
        return getEquipmentLevel(Enchantments.AQUA_AFFINITY, entity) > 0;
    }

    public static boolean hasFrostWalker(LivingEntity entity) {
        return getEquipmentLevel(Enchantments.FROST_WALKER, entity) > 0;
    }

    public static boolean hasSoulSpeed(LivingEntity entity) {
        return getEquipmentLevel(Enchantments.SOUL_SPEED, entity) > 0;
    }

    public static boolean hasBindingCurse(ItemStack stack) {
        return getLevel(Enchantments.BINDING_CURSE, stack) > 0;
    }

    public static boolean hasVanishingCurse(ItemStack stack) {
        return getLevel(Enchantments.VANISHING_CURSE, stack) > 0;
    }

    public static int getLoyalty(ItemStack stack) {
        return getLevel(Enchantments.LOYALTY, stack);
    }

    public static int getRiptide(ItemStack stack) {
        return getLevel(Enchantments.RIPTIDE, stack);
    }

    public static boolean hasChanneling(ItemStack stack) {
        return getLevel(Enchantments.CHANNELING, stack) > 0;
    }


    public static Map.Entry<EquipmentSlot, ItemStack> chooseEquipmentWith(Enchantment enchantment, LivingEntity entity) {
        return chooseEquipmentWith(enchantment, entity, (stack) -> {
            return true;
        });
    }


    public static Map.Entry<EquipmentSlot, ItemStack> chooseEquipmentWith(Enchantment enchantment, LivingEntity entity, Predicate<ItemStack> condition) {
        Map<EquipmentSlot, ItemStack> map = enchantment.getEquipment(entity);
        if (map.isEmpty()) {
            return null;
        } else {
            List<Map.Entry<EquipmentSlot, ItemStack>> list = Lists.newArrayList();
            Iterator var5 = map.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<EquipmentSlot, ItemStack> entry = (Map.Entry)var5.next();
                ItemStack itemStack = (ItemStack)entry.getValue();
                if (!itemStack.isEmpty() && getLevel(enchantment, itemStack) > 0 && condition.test(itemStack)) {
                    list.add(entry);
                }
            }

            return list.isEmpty() ? null : (Map.Entry)list.get(entity.getRandom().nextInt(list.size()));
        }
    }

    public static int calculateRequiredExperienceLevel(Random random, int slotIndex, int bookshelfCount, ItemStack stack) {
        Item item = stack.getItem();
        int i = item.getEnchantability();
        if (i <= 0) {
            return 0;
        } else {
            if (bookshelfCount > 15) {
                bookshelfCount = 15;
            }

            int j = random.nextInt(8) + 1 + (bookshelfCount >> 1) + random.nextInt(bookshelfCount + 1);
            if (slotIndex == 0) {
                return Math.max(j / 3, 1);
            } else {
                return slotIndex == 1 ? j * 2 / 3 + 1 : Math.max(j, bookshelfCount * 2);
            }
        }
    }

    public static ItemStack enchant(Random random, ItemStack target, int level, boolean treasureAllowed) {
        List<EnchantmentLevelEntry> list = generateEnchantments(random, target, level, treasureAllowed);
        boolean bl = target.getItem() == Items.BOOK;
        if (bl) {
            target = new ItemStack(Items.ENCHANTED_BOOK);
        }

        Iterator var6 = list.iterator();

        while(var6.hasNext()) {
            EnchantmentLevelEntry enchantmentLevelEntry = (EnchantmentLevelEntry)var6.next();
            if (bl) {
                EnchantedBookItem.addEnchantment(target, enchantmentLevelEntry);
            } else {
                target.addEnchantment(enchantmentLevelEntry.enchantment, enchantmentLevelEntry.level);
            }
        }

        return target;
    }

    public static List<EnchantmentLevelEntry> generateEnchantments(Random random, ItemStack stack, int level, boolean treasureAllowed) {
        List<EnchantmentLevelEntry> list = Lists.newArrayList();
        Item item = stack.getItem();
        int i = item.getEnchantability();
        if (i <= 0) {
            return list;
        } else {
            level += 1 + random.nextInt(i / 4 + 1) + random.nextInt(i / 4 + 1);
            float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
            level = MathHelper.clamp(Math.round((float)level + (float)level * f), 1, 2147483647);
            List<EnchantmentLevelEntry> list2 = getPossibleEntries(level, stack, treasureAllowed);
            if (!list2.isEmpty()) {
                list.add(WeightedPicker.getRandom(random, list2));

                while(random.nextInt(50) <= level) {
                    removeConflicts(list2, (EnchantmentLevelEntry) Util.getLast(list));
                    if (list2.isEmpty()) {
                        break;
                    }

                    list.add(WeightedPicker.getRandom(random, list2));
                    level /= 2;
                }
            }

            return list;
        }
    }

    public static void removeConflicts(List<EnchantmentLevelEntry> possibleEntries, EnchantmentLevelEntry pickedEntry) {
        Iterator iterator = possibleEntries.iterator();

        while(iterator.hasNext()) {
            if (!pickedEntry.enchantment.canCombine(((EnchantmentLevelEntry)iterator.next()).enchantment)) {
                iterator.remove();
            }
        }

    }

    public static boolean isCompatible(Collection<Enchantment> existing, Enchantment candidate) {
        Iterator var2 = existing.iterator();

        Enchantment enchantment;
        do {
            if (!var2.hasNext()) {
                return true;
            }

            enchantment = (Enchantment)var2.next();
        } while(enchantment.canCombine(candidate));

        return false;
    }

    public static List<EnchantmentLevelEntry> getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed) {
        List<EnchantmentLevelEntry> list = Lists.newArrayList();
        Item item = stack.getItem();
        boolean bl = stack.getItem() == Items.BOOK;
        Iterator var6 = Registry.ENCHANTMENT.iterator();

        while(true) {
            while(true) {
                Enchantment enchantment;
                do {
                    do {
                        do {
                            if (!var6.hasNext()) {
                                return list;
                            }

                            enchantment = (Enchantment)var6.next();
                        } while(enchantment.isTreasure() && !treasureAllowed);
                    } while(!enchantment.isAvailableForRandomSelection());
                } while(!enchantment.type.isAcceptableItem(item) && !bl);

                for(int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                    if (power >= enchantment.getMinPower(i) && power <= enchantment.getMaxPower(i)) {
                        list.add(new EnchantmentLevelEntry(enchantment, i));
                        break;
                    }
                }
            }
        }
    }

    @FunctionalInterface
    interface Consumer {
        void accept(Enchantment enchantment, int level);
    }
}
