package io.github.wickhamwei.wessential.wteleport;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WTeleport {
    public static Set<String> teleportingList = new HashSet<>();

    public static Map<String, Integer> teleportCoolingList = new HashMap<>();

    public static void setTeleporting(WPlayer player) {
        teleportingList.add(player.getName());
    }

    public static void stopTeleporting(WPlayer player) {
        teleportingList.remove(player.getName());
    }

    public static boolean isTeleporting(WPlayer player) {
        return teleportingList.contains(player.getName());
    }

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
                if (WPlayer.isOnline(player.getName())) {
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
}
