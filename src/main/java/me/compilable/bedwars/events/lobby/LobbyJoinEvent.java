package me.compilable.bedwars.events.lobby;

import me.compilable.bedwars.Bedwars;
import me.compilable.bedwars.player.GamePlayer;
import me.compilable.bedwars.runnable.LobbyCountdown;
import me.compilable.bedwars.utils.GameStates;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyJoinEvent implements Listener {

    @EventHandler
    public void onLobbyJoinEvent(PlayerJoinEvent event) {

        if(GameStates.getState() == GameStates.LOBBY) {
            Player player = event.getPlayer();

            GamePlayer gamePlayer = new GamePlayer(player);
            gamePlayer.lobbyOptions();

            if(Bedwars.getInstance().getBedwarsCfg().getMinPlayers() == Bukkit.getOnlinePlayers().size()) {
                LobbyCountdown lobbyCountdown = new LobbyCountdown();
                lobbyCountdown.startTimer();
            }

        }

    }

}
