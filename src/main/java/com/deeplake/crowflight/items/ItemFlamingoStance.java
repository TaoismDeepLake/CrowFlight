package com.deeplake.crowflight.items;

import com.deeplake.crowflight.client.creativetabs.AllTabs;
import com.deeplake.crowflight.init.ModPotions;
import com.deeplake.crowflight.util.CommonFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static com.deeplake.crowflight.util.IDLGeneral.TICK_PER_SECOND;

public class ItemFlamingoStance extends ItemBase {
    // /give @p crowflight:tiger_pudding
    private final static float cdTime = 5f;
    private final static float durTime = 60f;

    private int GetMaxTick()
    {
        return (int)(cdTime * TICK_PER_SECOND);
    }

    public ItemFlamingoStance(String name) {
        super(name);
        setMaxStackSize(1);
        setMaxDamage(GetMaxTick());
        setNoRepair();
        setCreativeTab(AllTabs.MISC_TAB);;
    }
    //todo: cancel KB recv

//    @Override
//    public boolean isDamageable() {
//        return true;
//    }

//    @Nonnull
//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
//        ItemStack stack = player.getHeldItem(hand);
//        if (stack.getItemDamage() == 0) {
//            player.addPotionEffect(new PotionEffect(ModPotions.TIGER_PUDDING, (int)((durTime - 2) * TICK_PER_SECOND) , 0));
//            stack.setItemDamage(GetMaxTick());
//            CommonFunctions.BroadCastToPlayersNearAPlayer(world, player, getUnlocalizedName() + ".name");
//            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
//        }
//
//        return ActionResult.newResult(EnumActionResult.PASS, stack);
//    }

//    @Override
//    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
//    {
//        if (isSelected)
//        {
//            stack.setItemDamage(stack.getItemDamage() - 1);
//        }
//    }
}
