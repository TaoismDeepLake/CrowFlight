package com.deeplake.crowflight.items;

import com.deeplake.crowflight.CrowFlight;
import com.deeplake.crowflight.client.creativetabs.AllTabs;
import com.deeplake.crowflight.init.ModPotions;
import com.deeplake.crowflight.util.CommonFunctions;
import com.deeplake.crowflight.util.IDLGeneral;
import com.deeplake.crowflight.util.MathUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static com.deeplake.crowflight.util.IDLGeneral.TICK_PER_SECOND;

public class ItemCrowOnPlane extends ItemBase {
    // /give @p crowflight:crow_on_plane
    private final static float cdTime = 10f;


    private int GetMaxTick()
    {
        return (int)(cdTime * TICK_PER_SECOND);
    }

    public ItemCrowOnPlane(String name) {
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
        //CrowFlight.LogWarning("hhh");
        //CrowFlight.Log(String.format("(%d/%d)", stack.getItemDamage(), stack.getMaxDamage()));
        if (stack.getItemDamage() == 0) {
            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 20, 3));
            player.addPotionEffect(new PotionEffect(ModPotions.CROW_FLIGHT, (int)((cdTime - 2) * TICK_PER_SECOND) , 0));
            stack.setItemDamage(GetMaxTick());
            //CrowFlight.Log(String.format("Damage to(%d/%d)", stack.getItemDamage(), stack.getMaxDamage()));

//            if (!world.isRemote)
//            {
//                player.sendMessage(new TextComponentTranslation(getUnlocalizedName() + ".name"));
//            }

            CommonFunctions.BroadCastToPlayersNearAPlayer(world, player, getUnlocalizedName() + ".name");
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (entityIn.onGround)
        {
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
//        String mainDesc = String.format(I18n.format("item.crow_on_plane.desc")) ;
//        tooltip.add(mainDesc);
//    }
}
