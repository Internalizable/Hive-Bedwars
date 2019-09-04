package me.compilable.bedwars.player;

import lombok.Getter;
import me.compilable.bedwars.utils.GameItems;
import org.bukkit.entity.Player;

public class GamePlayer {

    @Getter public Player player;

    public GamePlayer(Player player) {
        this.player = player;
    }

    public void lobbyOptions() {
        cleanPlayer();

        player.getInventory().setItem(0, GameItems.RULES_AND_INFO);
        player.getInventory().setItem(1, GameItems.VOTE_MAP);
        player.getInventory().setItem(4, GameItems.UNLOCK_SELECTOR);
        player.getInventory().setItem(7, GameItems.ACHIEVEMENTS);
        player.getInventory().setItem(8, GameItems.BACK_TO_HUB);

        player.updateInventory();
    }

    public void cleanPlayer() {

        if(isOnline()) {
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);

            player.updateInventory();
        }

    }

    public boolean isOnline() {
        return player.isOnline();
    }
}
