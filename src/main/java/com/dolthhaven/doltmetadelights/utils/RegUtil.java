package com.dolthhaven.doltmetadelights.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RegUtil {
    public static Item item(ResourceLocation location) {
        if (BuiltInRegistries.ITEM.keySet().contains(location)) {
            return BuiltInRegistries.ITEM.get(location);
        } else return null;
    }

    public static Item item(String namespace, String path) {
        return item(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static Holder<Item> itemHolder(ResourceLocation location) {
        return BuiltInRegistries.ITEM.getHolder(location).orElse(null);
    }

    public static Holder<Item> itemHolderOr(ResourceLocation location, Item item) {
        return BuiltInRegistries.ITEM.getHolder(location).orElse(item.builtInRegistryHolder());
    }


    public static Holder<Item> itemHolder(String namespace, String path) {
        return itemHolder(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static Block block(ResourceLocation location) {
        if (BuiltInRegistries.BLOCK.keySet().contains(location)) {
            return BuiltInRegistries.BLOCK.get(location);
        } else return null;
    }

    public static Block block(String namespace, String path) {
        return block(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static ResourceLocation itemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static ResourceLocation blockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
