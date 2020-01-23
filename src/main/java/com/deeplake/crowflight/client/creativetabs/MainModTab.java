package com.deeplake.crowflight.client.creativetabs;

import com.deeplake.crowflight.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MainModTab extends CreativeTabs {
    public MainModTab(final int id, final String name) {
        super(id, name);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.CROW_ON_PLANE, 1, 0);
    }
}
