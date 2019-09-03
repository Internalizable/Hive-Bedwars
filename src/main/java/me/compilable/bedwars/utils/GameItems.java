package me.compilable.bedwars.utils;

import me.compilable.bedwars.Bedwars;
import me.compilable.bedwars.utils.versioning.XMaterial;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class GameItems {

    public static String RIGHT_CLICK = "&b» Right click when held.";

    public static ItemStack RULES_AND_INFO = Bedwars.getInstance().getItemCreator().createBook(1, Arrays.asList("", "&7Read the basics of", "&7our rules and how", "&7to play BedWars!", "", RIGHT_CLICK), "&b&lRules &7&l& &e&lInfo",
            "&eQueen Bee", Arrays.asList("&5BedWars\n&7-=-=-=-=-=-=-=-=-\n\n&cRules\n&71|Do not cheat\n&72|Do not glitch\n&73| Clean chat\n&74|Respect others\n&7Full rules are available at hivemc.com",
                    "&5BedWars\n&7-=-=-=-=-=-=-=-=-\n\n&7What is BedWars?\n\n&0BedWars is a strategic PVP game with many styles that can lead you to victory or defeat!",
                    "&5BedWars\n&7-=-=-=-=-=-=-=-=-\n\n&7How do i win?\n\n&0You must protect your own bed, and destroy the beds of other players/teams. Once a bed is down, the team can no longer respawn. To win, you need to be the last team standing."));


    public static ItemStack VOTE_MAP = Bedwars.getInstance().getItemCreator().createItem(XMaterial.DIAMOND, 1, 0, "&6&lVote for a Map",
            Arrays.asList("", "&7Vote for a map", "&7to play on!", "", RIGHT_CLICK));

    public static ItemStack UNLOCK_SELECTOR = Bedwars.getInstance().getItemCreator().createItem(XMaterial.SUNFLOWER, 1, 0 ,"&e&lUnlock Selector",
            Arrays.asList("", "&7Browse and select", "&7your purchase unlocks", "", RIGHT_CLICK));

    public static ItemStack ACHIEVEMENTS = Bedwars.getInstance().getItemCreator().createItem(XMaterial.BOOK, 1, 0 ,"&aAchievements",
            Arrays.asList("", "&7View your BED", "&7achievement progress!", "", RIGHT_CLICK));

    public static ItemStack BACK_TO_HUB = Bedwars.getInstance().getItemCreator().createItem(XMaterial.SLIME_BALL, 1, 0 ,"&6&lBack to &e&lHub",
            Arrays.asList("", "&7Returns you to", "&7the main hub!", "", RIGHT_CLICK));


}
