package com.deeplake.crowflight.items;

import com.deeplake.crowflight.client.creativetabs.AllTabs;
import com.deeplake.crowflight.init.ModPotions;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static com.deeplake.crowflight.util.IDLGeneral.TICK_PER_SECOND;

public class ItemTigerEatPudding extends ItemBase {
    // /give @p crowflight:tiger_pudding
    private final static float cdTime = 5f;
    private final static float durTime = 60f;


    private int GetMaxTick()
    {
        return (int)(cdTime * TICK_PER_SECOND);
    }

    public ItemTigerEatPudding(String name) {
        super(name);
        setMaxStackSize(1);
        setMaxDamage(GetMaxTick());
        setNoRepair();
        setCreativeTab(AllTabs.MISC_TAB);;
    }

//    @Override
//    public boolean isDamageable() {
//        return true;
//    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItemDamage() == 0) {
            player.addPotionEffect(new PotionEffect(ModPotions.TIGER_PUDDING, (int)((durTime - 2) * TICK_PER_SECOND) , 0));
            stack.setItemDamage(GetMaxTick());
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (isSelected)
        {
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String mainDesc = String.format(I18n.format( stack.getUnlocalizedName()+".desc")) ;
        tooltip.add(mainDesc);
    }
}
