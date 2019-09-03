package me.compilable.bedwars.utils;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * This class allows you to track damage performed by a player, so a more specific "killed by" message can be shown.
 */
public class DamageTracker implements Listener {
    /**
     * Specifies how long a hit will last until it "ages out".
     */
    private static final long AGE_OUT = TimeUnit.SECONDS.toMillis(15);

    private final Map<UUID, Attack> attacks = new HashMap<>();
    private final CombatLogHandler combatLogHandler;

    public DamageTracker() {
        this((ignored1, ignored2) -> {});
    }

    public DamageTracker(@NonNull CombatLogHandler combatLogHandler) {
        this.combatLogHandler = combatLogHandler;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && !event.isCancelled()) {
            Entity attackingEntity = event.getDamager();
            Player attackingPlayer = null;
            if (attackingEntity instanceof Player) {
                attackingPlayer = (Player) attackingEntity;
            } else if (attackingEntity instanceof Projectile) {
                ProjectileSource ps = ((Projectile) attackingEntity).getShooter();
                if (ps instanceof Player) {
                    attackingPlayer = (Player) ps;
                }
            }

            if (attackingPlayer != null) {
                attacks.put(event.getEntity().getUniqueId(), new Attack(attackingPlayer.getUniqueId()));
            }
        }
    }

    public Optional<Player> getAndRemoveAttacker(Player player) {
        Attack attack = attacks.remove(player.getUniqueId());
        if (attack != null && !attack.isAgedOut()) {
            return Optional.ofNullable(Bukkit.getPlayer(attack.attacker));
        }
        return Optional.empty();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Optional<Player> attacker = getAndRemoveAttacker(event.getPlayer());
        if (attacker.isPresent()) {
            // combat logging detected
            combatLogHandler.onCombatLog(event.getPlayer(), attacker.get());
        }
    }

    private class Attack {
        private final UUID attacker;
        private final long attackTimestamp;

        public Attack(UUID attacker) {
            this.attacker = attacker;
            this.attackTimestamp = System.currentTimeMillis();
        }

        private boolean isAgedOut() {
            return attackTimestamp + AGE_OUT <= System.currentTimeMillis();
        }
    }

    @FunctionalInterface
    public interface CombatLogHandler {
        void onCombatLog(Player logged, Player attacker);
    }
}
