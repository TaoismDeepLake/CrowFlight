package com.deeplake.crowflight.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCrowOnPlane extends ItemBase {
    ///give @p crowflight:crow_on_plane
    public ItemCrowOnPlane(String name) {
        super(name);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (isSelected)
        {
            if (entityIn.motionY < -0.1f)
            {
                entityIn.motionY = -0.1f;
            }
        }
    }
}
