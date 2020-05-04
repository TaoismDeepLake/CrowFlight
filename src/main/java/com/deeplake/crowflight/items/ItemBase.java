package com.deeplake.crowflight.items;

import com.deeplake.crowflight.CrowFlight;
import com.deeplake.crowflight.init.ModItems;
import com.deeplake.crowflight.util.IHasModel;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBase extends Item implements IHasModel {
	public ItemBase() {
		
	}
	
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		//setCreativeTab(ModCreativeTab.DW_MISC);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		CrowFlight.proxy.registerItemRenderer(this, 0, "inventory");
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		String mainDesc = String.format(I18n.format(stack.getUnlocalizedName() + ".desc"));
		tooltip.add(mainDesc);
	}
}
