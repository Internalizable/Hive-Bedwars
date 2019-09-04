package me.compilable.bedwars.runnable;

import lombok.Getter;
import me.compilable.bedwars.Bedwars;
import me.compilable.bedwars.utils.GameStates;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyCountdown {

    @Getter public static int timer = 60;

    public void startTimer() {

        GameStates.setState(GameStates.STARTING);

        new BukkitRunnable() {

            @Override
            public void run() {

                if(GameStates.getState() != GameStates.STARTING) {
                    timer = 60;
                    //todo some more stuff
                    this.cancel();
                }

                timer--;
                //todo announce at intervals

                if(timer <= 0) {
                    //todo start game
                }

            }

        }.runTaskTimer(Bedwars.getInstance(), 0L, 20L);
    }

}
