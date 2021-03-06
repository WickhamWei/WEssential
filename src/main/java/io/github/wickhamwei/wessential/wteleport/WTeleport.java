package io.github.wickhamwei.wessential.wteleport;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class WTeleport {
    public static Set<String> teleportingList = new HashSet<>();

    public static void setTeleporting(WPlayer player) {
        teleportingList.add(player.getName());
    }

    public static void stopTeleporting(WPlayer player) {
        teleportingList.remove(player.getName());
    }

    public static boolean isTeleporting(WPlayer player) {
        return teleportingList.contains(player.getName());
    }

    public static Map<String, Integer> teleportCoolingList = new HashMap<>();

    public static void newTeleportCooling(WPlayer player) {
        int coolingTimeSecond = WEssentialMain.wEssentialMain.getConfig().getInt("teleport_setting.teleport_cooling_time");
        teleportCoolingList.put(player.getName(), coolingTimeSecond);
        newTeleportCoolingTask(player);
    }

    public static void setTeleportCooling(WPlayer player, int coolingTimeSecond) {
        teleportCoolingList.put(player.getName(), coolingTimeSecond);
    }

    public static void stopTeleportCooling(WPlayer player) {
        teleportCoolingList.remove(player.getName());
    }

    public static int getCoolingTimeLeft(WPlayer player) {
        if (teleportCoolingList.containsKey(player.getName())) {
            return teleportCoolingList.get(player.getName());
        } else {
            return 0;
        }
    }

    private static void newTeleportCoolingTask(final WPlayer player) {
        BukkitRunnable teleportBukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (WPlayer.isLogin(player.getName())) {
                    if (getCoolingTimeLeft(player) == 0) {
                        stopTeleportCooling(player);
                        cancel();
                    } else {
                        setTeleportCooling(player, getCoolingTimeLeft(player) - 1);
                    }
                } else {
                    stopTeleportCooling(player);
                    cancel();
                }
            }
        };
        teleportBukkitRunnable.runTaskTimer(WEssentialMain.wEssentialMain, 0, 20);
    }

    public static Map<String, String> teleportRequestList = new HashMap<>();
    public static Map<String, String> teleportUnderRequestList = new HashMap<>();

    public static void newTeleportRequest(WPlayer mainPlayer, WPlayer targetPlayer) {
        teleportRequestList.put(mainPlayer.getName(), targetPlayer.getName());
        teleportUnderRequestList.put(targetPlayer.getName(), mainPlayer.getName());

        newTeleportRequestTask(mainPlayer, targetPlayer);
    }

    public static void stopTeleportRequest(WPlayer mainPlayer, WPlayer targetPlayer) {
        teleportRequestList.remove(mainPlayer.getName());
        teleportUnderRequestList.remove(targetPlayer.getName());
    }

    public static void stopTeleportRequest(WPlayer mainPlayer) {
        teleportUnderRequestList.remove(teleportRequestList.remove(mainPlayer.getName()));
    }

    public static boolean isInRequest(WPlayer mainPlayer) {
        return teleportRequestList.containsKey(mainPlayer.getName());
    }

    public static boolean isUnderRequest(WPlayer targetPlayer) {
        return teleportUnderRequestList.containsKey(targetPlayer.getName());
    }

    public static WPlayer getTargetPlayer(WPlayer mainPlayer) {
        return WPlayer.getWPlayer(teleportRequestList.get(mainPlayer.getName()));
    }

    public static WPlayer getMainPlayer(WPlayer targetPlayer) {
        return WPlayer.getWPlayer(teleportUnderRequestList.get(targetPlayer.getName()));
    }

    private static void newTeleportRequestTask(final WPlayer mainPlayer, final WPlayer targetPlayer) {
        BukkitRunnable teleportBukkitRunnable = new BukkitRunnable() {
            int timeLeftSecond = 20;

            @Override
            public void run() {
                if (WPlayer.isLogin(mainPlayer.getName()) && WPlayer.isLogin(targetPlayer.getName())) {
                    if (WTeleport.isInRequest(mainPlayer) && WTeleport.isUnderRequest(targetPlayer)) {
                        if (timeLeftSecond == 0) {
                            stopTeleportRequest(mainPlayer, targetPlayer);
                            mainPlayer.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_main_under_refuse"));
                            targetPlayer.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_target_refuse"));
                            cancel();
                        } else {
                            timeLeftSecond--;
                        }
                    } else {
                        stopTeleportRequest(mainPlayer, targetPlayer);
                        cancel();
                    }
                } else {
                    stopTeleportRequest(mainPlayer, targetPlayer);
                    cancel();
                }
            }
        };
        teleportBukkitRunnable.runTaskTimer(WEssentialMain.wEssentialMain, 0, 20);
    }

    public static void setBackLocation(WPlayer player, Location location) {
        String playerUniqueId = player.getBukkitPlayer().getUniqueId().toString();
        String world = Objects.requireNonNull(location.getWorld()).getName();
        double X = Math.floor(location.getX());
        double Y = Math.floor(location.getY());
        double Z = Math.floor(location.getZ());
        WEssentialMain.backLocationConfig.getConfig().set(playerUniqueId + ".world", world);
        WEssentialMain.backLocationConfig.getConfig().set(playerUniqueId + ".X", X);
        WEssentialMain.backLocationConfig.getConfig().set(playerUniqueId + ".Y", Y);
        WEssentialMain.backLocationConfig.getConfig().set(playerUniqueId + ".Z", Z);
        WEssentialMain.backLocationConfig.saveConfig();
    }

    public static Location getBackLocation(WPlayer player) {
        String playerUniqueId = player.getBukkitPlayer().getUniqueId().toString();
        if (WEssentialMain.backLocationConfig.getConfig().contains(playerUniqueId)) {
            String worldString = WEssentialMain.backLocationConfig.getConfig().getString(playerUniqueId + ".world");
            assert worldString != null;
            World world = Bukkit.getWorld(worldString);
            double X = WEssentialMain.backLocationConfig.getConfig().getDouble(playerUniqueId + ".X");
            double Y = WEssentialMain.backLocationConfig.getConfig().getDouble(playerUniqueId + ".Y");
            double Z = WEssentialMain.backLocationConfig.getConfig().getDouble(playerUniqueId + ".Z");
            return new Location(world, X, Y, Z);
        } else {
            return null;
        }
    }
}
