package com.deeplake.crowflight.items;

import com.deeplake.crowflight.CrowFlight;
import com.deeplake.crowflight.init.ModItems;
import com.deeplake.crowflight.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

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
}
