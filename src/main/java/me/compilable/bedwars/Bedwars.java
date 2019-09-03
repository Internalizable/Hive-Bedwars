package me.compilable.bedwars;

import lombok.Getter;
import me.compilable.bedwars.utils.GameStates;
import me.compilable.bedwars.utils.api.gui.ItemCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class Bedwars extends JavaPlugin {

    @Getter public static Bedwars instance;

    @Getter public static ItemCreator itemCreator = new ItemCreator();

    @Override
    public void onEnable() {
        instance = this;

        GameStates.setState(GameStates.STARTING);
    }

    @Override
    public void onDisable() {

    }
}
