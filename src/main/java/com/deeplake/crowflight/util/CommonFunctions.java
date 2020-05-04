package com.deeplake.crowflight.util;

import com.deeplake.crowflight.CrowFlight;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class CommonFunctions {
    public static int SecondToTicks(int ticks) {
        return ticks * CommonDef.TICK_PER_SECOND;
    }

    public static int SecondToTicks(float ticks) {
        return (int)(ticks * CommonDef.TICK_PER_SECOND);
    }

    public static void BroadCastByKey(String key) {
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentTranslation(key));
    }

    public static void BroadCastToPlayersNearAPlayer(World worldIn, EntityPlayer playerIn, String key)
    {
        if (worldIn.isRemote)
        {
            return;
        }

        float base_range = 32;
        Vec3d basePos = playerIn.getPositionVector();
        List<EntityPlayerMP> entities = worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range)));
        for (EntityPlayerMP living: entities
        ) {
            SendMsgToPlayer(living, key);
        }
    }

    public static void SafeSendMsgToPlayer(EntityPlayer player, String key, Object... args)
    {
        if (player instanceof EntityPlayerMP)
        {
            player.sendMessage((new TextComponentTranslation(key, args)));
        }
    }

    public static void SendMsgToPlayer(EntityPlayerMP playerMP, String key)
    {
        playerMP.sendMessage(new TextComponentTranslation(key));
    }

    public static void SendMsgToPlayerStyled(EntityPlayerMP playerMP, String key, TextFormatting style, Object... args)
    {
        TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(key, args);
        textcomponenttranslation.getStyle().setColor(style);
        playerMP.sendMessage(textcomponenttranslation);
    }


    public static void SendMsgToPlayer(EntityPlayerMP playerMP, String key, Object... args)
    {
        playerMP.sendMessage((new TextComponentTranslation(key, args)));
    }

    @SideOnly(Side.CLIENT)
    public static String GetStringLocalTranslated(String key) {
        //return "WIP";
        return I18n.format(key);
    }

    public static void FillWithBlockCornered(World world, BlockPos origin, int lengthX, int lengthY, int lengthZ, IBlockState newState) {
        BlockPos target;
        for(int x = 0;
            x < lengthX;x++) {
            for (int y = 0; y < lengthY; y++) {
                for (int z = 0; z < lengthZ; z++) {
                    target = origin.add(x, y, z);
                    //IBlockState targetBlock = world.getBlockState(target);
                    world.setBlockState(target, newState);
                }
            }
        }
    }

    public static void FillWithBlockCentered(World world, BlockPos origin, int rangeX, int rangeY, int rangeZ, IBlockState newState) {
        BlockPos target;
        for(int x = -rangeX;
        x <=rangeX;x++) {
            for (int y = -rangeY; y <= rangeY; y++) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    target = origin.add(x, y, z);
                    //IBlockState targetBlock = world.getBlockState(target);
                    world.setBlockState(target, newState);
                }
            }
        }
    }

    public static void BuildWallWithBlockCentered(World world, BlockPos origin, int rangeX, int height, int rangeZ, IBlockState newState) {
        BlockPos target;
        for(int x = -rangeX;
            x <=rangeX;x++) {
            for (int y = 0; y < height; y++) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    target = origin.add(x, y, z);
                    //IBlockState targetBlock = world.getBlockState(target);
                    world.setBlockState(target, newState);
                }
            }
        }
    }

    public static void LogPlayerAction(EntityLivingBase living, String action){
        CrowFlight.Log(String.format("%s(%s): %s",living.getName(), living.getUniqueID(), action));
    }
}
