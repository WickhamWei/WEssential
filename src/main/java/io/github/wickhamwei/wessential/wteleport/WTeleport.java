package io.github.wickhamwei.wessential.wteleport;

import io.github.wickhamwei.wessential.WEssentialMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public abstract class WTeleport {
    private static HashMap<String, WTeleportPlayer> TELEPORT_WAITING_LIST = new HashMap<String, WTeleportPlayer>();

    public static void teleport(Player player, Location targetLocation) {
        if (!isInWaitingList(player)) {
            final String playerName = player.getName();
            final Location location = targetLocation;

            BukkitRunnable teleportBukkitRunnable = new BukkitRunnable() {
                int timeLeft = WEssentialMain.wEssentialMain.getConfig().getInt("teleport_setting.teleport_waiting_time");

                @Override
                public void run() {

                    Player player = Bukkit.getPlayer(playerName);
                    if (player != null) {
                        if (isInWaitingList(player)) {
                            if (timeLeft > 0) {
                                WEssentialMain.sendMessage(player, timeLeft + WEssentialMain.languageConfig.getConfig().getString("message.time_left_teleport"));
                                timeLeft--;
                                return;
                            } else {
                                player.teleport(location);
                                removeFromWaitingList(player);
                                WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.teleport_successful"));
                                cancel();
                                return;
                            }
                        } else {
                            cancel();
                        }
                    } else {
                        removeFromWaitingList(player);
                        cancel();
                    }
                }
            };
            teleportBukkitRunnable.runTaskTimer(WEssentialMain.wEssentialMain, 0, 20);

            int teleportTaskID = teleportBukkitRunnable.getTaskId();
            addInWaitingList(player, teleportTaskID);
        } else {
            WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.already_waiting_teleport"));
        }
    }

    public WTeleport(Player player, Player targetPlayer) {
        player.teleport(targetPlayer, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    private static void addInWaitingList(Player player, int teleportTaskID) {
        TELEPORT_WAITING_LIST.put(player.getName(), new WTeleportPlayer(player.getName(), teleportTaskID));
    }

    private static boolean isInWaitingList(Player player) {
        return TELEPORT_WAITING_LIST.containsKey(player.getName());
    }

    private static void removeFromWaitingList(Player player) {
        TELEPORT_WAITING_LIST.remove(player.getName());
    }
}
