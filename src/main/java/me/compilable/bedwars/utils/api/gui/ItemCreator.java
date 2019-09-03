package me.compilable.bedwars.utils.api.gui;

import java.util.List;

import me.compilable.bedwars.utils.api.Colorizer;
import me.compilable.bedwars.utils.versioning.XMaterial;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class ItemCreator {

	public ItemStack createItem(XMaterial type, int amount, int data, String name, List<String> lore) {
		ItemStack item = new ItemStack(type.parseMaterial(), 1, (short) data);
		ItemMeta meta = item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setDisplayName(Colorizer.color(name));
		meta.setLore(Colorizer.colorList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack createItemPotion(XMaterial type, int amount, int data, String name, List<String> lore) {
		ItemStack item = new ItemStack(type.parseMaterial(), 1, (short) data);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		
		meta.clearCustomEffects();
		
		item.setAmount(amount);
		
		meta.setDisplayName(Colorizer.color(name));
		meta.setLore(Colorizer.colorList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}

	public ItemStack createBook(int amount, List<String> lore, String title, String author, List<String> pages) {
		ItemStack writtenBook = new ItemStack(XMaterial.WRITTEN_BOOK.parseMaterial(), 1);
		BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();
		bookMeta.setTitle(Colorizer.color(title));
		bookMeta.setAuthor(Colorizer.color(author));
		bookMeta.setPages(Colorizer.colorList(pages));
		bookMeta.setLore(lore);

		writtenBook.setItemMeta(bookMeta);

		return writtenBook;
	}
	
	public ItemStack createItem(XMaterial type, int amount, int data, String name) {
		ItemStack item = new ItemStack(type.parseMaterial(), 1, (short) data);
		ItemMeta meta = item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setDisplayName(Colorizer.color(name));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack createArmour(XMaterial type, int amount, Color color, String name) {
		ItemStack item = new ItemStack(type.parseMaterial(), 1, (short) 0);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setDisplayName(Colorizer.color(name));
		
		meta.setColor(color);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack createArmour(XMaterial type, int amount, Color color, String name, List<String> lore) {
		ItemStack item = new ItemStack(type.parseMaterial(), 1, (short) 0);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setDisplayName(Colorizer.color(name));
		
		meta.setLore(Colorizer.colorList(lore));
		
		meta.setColor(color);
		
		item.setItemMeta(meta);
		
		return item;
	}
}
