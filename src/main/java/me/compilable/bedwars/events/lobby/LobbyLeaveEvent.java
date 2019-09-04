package me.compilable.bedwars.events.lobby;

import me.compilable.bedwars.Bedwars;
import me.compilable.bedwars.utils.GameStates;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyLeaveEvent implements Listener {

    @EventHandler
    public void lobbyQuitEvent(PlayerQuitEvent event) {

        if(GameStates.getState() == GameStates.STARTING || GameStates.getState() == GameStates.LOBBY) {

            event.setQuitMessage("");

            if(Bukkit.getOnlinePlayers().size() < Bedwars.getInstance().getBedwarsCfg().getMinPlayers()) {
                GameStates.setState(GameStates.LOBBY);

                //todo send stop timer
            }
        }

    }
}
