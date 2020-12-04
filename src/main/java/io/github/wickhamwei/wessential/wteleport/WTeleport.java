package io.github.wickhamwei.wessential.wteleport;

import io.github.wickhamwei.wessential.WEssentialMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public abstract class WTeleport {
    private static HashMap<String, WTeleportPlayer> TELEPORT_WAITING_LIST = new HashMap<>();

    public static void teleport(Player player, Location targetLocation) {
        if (getTaskIDInWaitingList(player) == 0) {
            final String playerName = player.getName();
            final Location location = targetLocation;

            BukkitRunnable teleportBukkitRunnable = new BukkitRunnable() {
                int timeLeft = WEssentialMain.wEssentialMain.getConfig().getInt("teleport_setting.teleport_waiting_time");

                @Override
                public void run() {

                    Player player = Bukkit.getPlayer(playerName);
                    if (player != null) {
                        if (getTaskIDInWaitingList(player) == getTaskId()) {
                            if (timeLeft > 0) {
                                WEssentialMain.sendMessage(player, timeLeft + WEssentialMain.languageConfig.getConfig().getString("message.time_left_teleport"));
                                timeLeft--;
                            } else {
                                player.teleport(location);
                                removeFromWaitingList(playerName);
                                WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.teleport_successful"));
                                cancel();
                            }
                        } else {
                            cancel();
                        }
                    } else {
                        removeFromWaitingList(playerName);
                        cancel();
                    }
                }
            };
            teleportBukkitRunnable.runTaskTimer(WEssentialMain.wEssentialMain, 0, 20);
            addInWaitingList(player, teleportBukkitRunnable.getTaskId());
            // 总是获取到taskId以后，run()才开始执行
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

    private static int getTaskIDInWaitingList(Player player) {
        if (TELEPORT_WAITING_LIST.containsKey(player.getName())) {
            WTeleportPlayer wTeleportPlayer = TELEPORT_WAITING_LIST.get(player.getName());
            return wTeleportPlayer.teleportTaskID;
        } else {
            return 0;
        }
    }

    public static boolean removeFromWaitingList(String playerName) {
        if (TELEPORT_WAITING_LIST.remove(playerName) == null) {
            return false;
        } else {
            return true;
        }
    }
}
