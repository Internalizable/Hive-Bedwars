package me.compilable.bedwars.utils.api.gui;

import java.util.List;

import me.compilable.bedwars.utils.api.Colorizer;
import me.compilable.bedwars.utils.versioning.XMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCreator {
	
	public ItemStack createSkull(String owner, int amount, String name, List<String> lore) {
		ItemStack item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setOwner(owner);
		
		meta.setDisplayName(Colorizer.color(name));
		meta.setLore(Colorizer.colorList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack createSkull(String owner, int amount, String name) {
		ItemStack item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setOwner(owner);
		
		meta.setDisplayName(Colorizer.color(name));
		
		item.setItemMeta(meta);
		
		return item;
	}
}
