package me.compilable.bedwars.utils.api.gui;

import org.bukkit.inventory.ItemStack;

public abstract class GUICallback {

	public static enum CallbackType {
		INIT,
		CLICK,
		CLOSE,
	}
	
	public abstract void callback(ChestGUI gui, CallbackType callback, ItemStack item);
	
	public abstract void onSecond(ChestGUI gui);
}
