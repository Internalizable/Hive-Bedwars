package me.compilable.bedwars.utils.game;

import me.compilable.bedwars.Bedwars;
import me.compilable.bedwars.runnable.LobbyCountdown;
import me.compilable.bedwars.utils.Actionbar;
import me.compilable.bedwars.utils.GameStates;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionbarManager {

    public void init() {

        Actionbar actionbar = new Actionbar();

        new BukkitRunnable() {
            @Override
            public void run() {
                GameStates gameState = GameStates.getState();

                switch(gameState) {
                    case LOBBY:

                        int currentNeededPlayers = Bedwars.getInstance().getBedwarsCfg().getMinPlayers() - Bukkit.getOnlinePlayers().size();

                        Bukkit.getOnlinePlayers().forEach(p -> actionbar.send(p, "&e" + currentNeededPlayers + " player(s) needed to start!"));

                    case STARTING:

                        Bukkit.getOnlinePlayers().forEach(p -> actionbar.send(p, "&aStarting game in &l" + LobbyCountdown.getTimer()));
                }

            }
        }.runTaskTimer(Bedwars.getInstance(), 0L, 10L);

    }
}
