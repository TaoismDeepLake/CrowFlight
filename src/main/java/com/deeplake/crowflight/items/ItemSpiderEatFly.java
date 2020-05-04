package com.deeplake.crowflight.items;

import com.deeplake.crowflight.CrowFlight;
import com.deeplake.crowflight.client.creativetabs.AllTabs;
import com.deeplake.crowflight.init.ModPotions;
import com.deeplake.crowflight.util.CommonFunctions;
import com.deeplake.crowflight.util.IDLGeneral;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static com.deeplake.crowflight.util.IDLGeneral.TICK_PER_SECOND;
import static net.minecraft.util.DamageSource.causePlayerDamage;

public class ItemSpiderEatFly extends ItemSkillBase {

    private final static float range = 10f;
    private final static float damage = 5f;


    public ItemSpiderEatFly(String name) {
        super(name);
        setCreativeTab(AllTabs.MISC_TAB);
        cool_down = 5f;
    }

//    @Override
//    public boolean isDamageable() {
//        return true;
//    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);

        if (!worldIn.isRemote)
        {
            Vec3d basePos = playerIn.getPositionVector();
            List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range)));
            for (EntityLivingBase creature: entities
            ) {
                //CrowFlight.Log(creature.getName() + " " + !creature.onGround);
                if (!creature.onGround)//when a creature is in web, it will be on ground. however, the inWeb is a protected property.
                {
                    if (creature.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), damage))
                    {
                        //same as snow golem
                        int i = 0;
                        int j = 0;
                        int k = 0;
                        for (int l = 0; l < 4; ++l)
                        {
                            i = MathHelper.floor(creature.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                            j = MathHelper.floor(creature.posY);
                            k = MathHelper.floor(creature.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                            BlockPos blockpos = new BlockPos(i, j, k);
                            if (creature.world.getBlockState(blockpos).getMaterial() == Material.AIR &&
                                    Blocks.WEB.canPlaceBlockAt(creature.world, blockpos))
                            {
                                creature.world.setBlockState(blockpos, Blocks.WEB.getDefaultState());
                            }
                        }
                    }
                }
            }

            CommonFunctions.BroadCastToPlayersNearAPlayer(worldIn, playerIn, getUnlocalizedName() + ".name");
            playerIn.swingArm(hand);
            activateCoolDown(playerIn, stack);
        }else {
            playerIn.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, playerIn.posX, playerIn.posY, playerIn.posZ, 1.0D, 0.0D, 0.0D);
            playerIn.playSound(SoundEvents.E_PARROT_IM_SPIDER, 1f, 1f);
        }
        activateCoolDown(playerIn, stack);
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (entityIn.onGround)
        {
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }
}
