package io.github.wickhamwei.wessential.wtools;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WPlayer {

    public static Set<WPlayer> playerList = new HashSet<>();

    public static WPlayer getWPlayer(String playerName) {
        for (WPlayer player : playerList) {
            if (player.playerName.equals(playerName)) {
                return player;
            }
        }
        return new WPlayer(playerName);
    }

    public String playerName;

    @Override
    public String toString() {
        return playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WPlayer wPlayer = (WPlayer) o;
        return playerName.equals(wPlayer.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }

    public WPlayer(String playerName) {
        this.playerName = playerName;
        playerList.add(this);
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(playerName) != null;
    }

    public String getName() {
        return playerName;
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(playerName);
    }

    public Location getLocation() {
        return getBukkitPlayer().getLocation();
    }

    public String getUniqueId() {
        return getBukkitPlayer().getUniqueId().toString();
    }

    public void exitGame() {
        playerList.remove(this);
    }

    public void sendMessage(String message) {
        getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void teleport(final Location targetLocation) {
        if (WTeleport.getTaskId(this) == 0) {
            final WPlayer player = this;
            BukkitRunnable teleportBukkitRunnable = new BukkitRunnable() {
                int timeLeft = WEssentialMain.wEssentialMain.getConfig().getInt("teleport_setting.teleport_waiting_time");

                @Override
                public void run() {
                    if (player.isOnline()) {
                        if (WTeleport.getTaskId(player) == getTaskId()) {
                            if (timeLeft > 0) {
                                player.sendMessage(timeLeft + WEssentialMain.languageConfig.getConfig().getString("message.time_left_teleport"));
                                timeLeft--;
                            } else {
                                player.getBukkitPlayer().teleport(targetLocation);
                                WTeleport.removeFromWaitingList(player);
                                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_successful"));
                                cancel();
                            }
                        } else {
                            cancel();
                        }
                    } else {
                        WTeleport.removeFromWaitingList(player);
                        cancel();
                    }
                }
            };
            teleportBukkitRunnable.runTaskTimer(WEssentialMain.wEssentialMain, 0, 20);
            WTeleport.addInWaitingList(player, teleportBukkitRunnable.getTaskId());
            // 总是获取到taskId以后，run()才开始执行
        } else {
            sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.already_waiting_teleport"));
        }
    }
}
