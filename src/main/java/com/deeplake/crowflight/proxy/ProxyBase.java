package com.deeplake.crowflight.proxy;

import net.minecraft.item.Item;

public class ProxyBase {
	public boolean isServer()
	{
		return true;
	}

	public void registerItemRenderer(Item item, int meta, String id) {
		//Ignored
	}
}
