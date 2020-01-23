package com.deeplake.crowflight.items;

import io.netty.util.internal.StringUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static net.minecraft.init.PotionTypes.*;

public class ItemCrowOnPlane extends ItemBase {
    ///give @p crowflight:crow_on_plane
    public ItemCrowOnPlane(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        player.setActiveHand(hand);
        ItemStack stack = player.getHeldItem(hand);

        player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 20, 1));

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
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
            entityIn.fallDistance = 0;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String mainDesc = String.format(I18n.format("item.crow_on_plane.desc")) ;
        tooltip.add(mainDesc);
    }
}
