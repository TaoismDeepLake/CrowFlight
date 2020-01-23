package com.deeplake.crowflight.items;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.deeplake.crowflight.CrowFlight;
import com.deeplake.crowflight.init.ModItems;
import com.deeplake.crowflight.util.IHasModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.translation.I18n;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class DWeaponSwordBase extends ItemSword implements IHasModel {



	public DWeaponSwordBase(String name, ToolMaterial material)
	{
		super(material);

		setUnlocalizedName(name);
		setRegistryName(name);
		//setCreativeTab(ModCreativeTab.DW_WEAPON);

		
		ModItems.ITEMS.add(this);
	}

	//---------------------------------------------------------
	
	
	/* Only Server side */
	public boolean AttackDelegate(final ItemStack stack, final EntityPlayer player, final Entity target, float ratio)
	{
		return false;
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity target) {
		//if (player.world.isRemote)
			//return true;
		
		//if (player.getCooledAttackStrength(0.0f) > 0.8f) {
			return AttackDelegate(stack, player, target, player.getCooledAttackStrength(0.0f));
		//}

		//return true;//ignore attack
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
		//Particle;
		super.onUsingTick(stack, living, count);
		//DWeapons.LogWarning(String.format("base onUsingTick %s",count));
		
		if (living.world.isRemote)
		{
			clientUseTick(stack, living, count);
		}
		else
		{
			serverUseTick(stack, living, count);
		}
	}
	
	public void clientUseTick(ItemStack stack, EntityLivingBase living, int count)
	{
		
	}
	
	public void serverUseTick(ItemStack stack, EntityLivingBase living, int count)
	{
		
	}
	//----------------------------------------------------------------
	@Override
	public void registerModels() 
	{
		CrowFlight.proxy.registerItemRenderer(this, 0, "inventory");
		
	}
	
	//-----------------Any Item
	@Nonnull
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
	EntityItem entity = new EntityItem(world, location.posX, location.posY, location.posZ, itemstack);
		if(location instanceof EntityItem) {
		// workaround for private access on that field >_>
		  NBTTagCompound tag = new NBTTagCompound();
		  location.writeToNBT(tag);
		  entity.setPickupDelay(tag.getShort("PickupDelay"));
		}
		entity.motionX = location.motionX;
		entity.motionY = location.motionY;
		entity.motionZ = location.motionZ;
		return entity;
		//EnumRarity s = null;
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
	    return false;
	}
	  
	  /**
	     * Called when a player drops the item into the world,
	     * returning false from this will prevent the item from
	     * being removed from the players inventory and spawning
	     * in the world
	     *
	     * @param player The player that dropped the item
	     * @param item The item stack, before the item is removed.
	     */
	@Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        return false;
    }
  
    public int getItemEnchantability()
    {
        return 0;
    }
	
    //TestCode
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
        //disable this after real play
        
		
		return true;
    }
    
//    public String getUnlocalizedName(ItemStack stack)
//    {
//        //return "item." + this.unlocalizedName;
//    }
    
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {

    }

//    @SideOnly(Side.CLIENT)
//    public String getItemStackDisplayName(ItemStack stack)
//    {
//    	String strMain ="";
//    	if (IsNameHidden(stack))
//    	{
////    		if (IsManualReady(stack))
////    		{
////
////    		}
////    		else
//    		{
//    			strMain = I18n.format(getUnlocalizedName(stack) + DWNBTDef.TOOLTIP_HIDDEN);
//    		}
//
//    	}
//    	else
//    	{
//    		strMain = I18n.format(getUnlocalizedName(stack) + ".name");
//    	}
//
//    	String strLevel = "";
//    	if (IsSky(stack))
//    	{
//    		strLevel = I18n.format("postfix.is_sky.name");
//    	}else if (IsEarth(stack))
//    	{
//    		strLevel = I18n.format("postfix.is_earth.name");
//    	}
//    	String strPearl = "";
//    	if (GetPearlCount(stack) > 0) {
//    		strPearl = I18n.format("postfix.pearl_count.name", GetPearlCount(stack));
//    	}
//        return I18n.format("item.dweapon_name_format",strMain, strLevel, strPearl);
//    }
	  //----------------------------------------------------
	  /* NBT loading */

	  
	  /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
    	super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    String translateToLocalFormatted(String key, Object... format)
    {
    	return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(key, format);
    }
    	
    //translateToLocal
    String translateToLocal(String key)
    {
    	return net.minecraft.util.text.translation.I18n.translateToLocal(key);
    }
    
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	//Trys to force override the ItemStack.getTooltip failed,
        //because the damage attr are added after getTooltip.
    	//This is the way I found to hide the damage descrption.
    	//ForceHideVanillaAttr(stack);
    }

    public boolean isNeedDamageDesc = true;
	@SideOnly(Side.CLIENT)
    public float GetReferenceDamage(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	return 7.0f;
    }
//    @SideOnly(Side.CLIENT)
//    public void addDamageInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//    	if (isNeedDamageDesc) {
//	    	String dmgDesc = I18n.format(getUnlocalizedName()+DWNBTDef.TOOLTIP_DAMAGE, GetReferenceDamage(stack, worldIn, tooltip, flagIn));
//			tooltip.add(dmgDesc);
//    	}
//    }
    ///Weather related
    public float GetTemperatureHere(EntityPlayer player)
	{
		BlockPos pos = player.getPosition();
		World world = player.getEntityWorld();
		Biome biome = world.getBiomeForCoordsBody(pos);
		return biome.getTemperature(pos);
	}
	
	public boolean CanSnowHere(EntityPlayer player)
	{
		return (GetTemperatureHere(player) < 0.15f);
	}
	
	public boolean CanRainHere(EntityPlayer player)
	{
		return (GetTemperatureHere(player) > 0.15f) && (GetTemperatureHere(player) < 0.95f);
	}
	
	public boolean IsSnowingHere(EntityPlayer player)
	{
		WorldInfo worldInfo = player.world.getWorldInfo();
		boolean raining = worldInfo.isRaining();
		return CanSnowHere(player) && raining;
	}
	
	public boolean IsRainingHere(EntityPlayer player)
	{
		WorldInfo worldInfo = player.world.getWorldInfo();
		boolean raining = worldInfo.isRaining();
		return !CanSnowHere(player) && raining;
	}
}
