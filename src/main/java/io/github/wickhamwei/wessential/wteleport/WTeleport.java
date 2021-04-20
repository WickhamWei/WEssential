package io.github.wickhamwei.wessential.wteleport;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class WTeleport {
    public static HashMap<String, Integer> teleportWaitingList = new HashMap<>();

    public static void addInWaitingList(WPlayer player, int teleportTaskID) {
        teleportWaitingList.put(player.getName(), teleportTaskID);
    }

    public static void removeFromWaitingList(WPlayer player) {
        teleportWaitingList.remove(player.getName());
    }

    public static boolean isInWaitingList(WPlayer player){
        return teleportWaitingList.containsKey(player.getName());
    }

    public static int getTaskId(WPlayer player) {
        if (teleportWaitingList.containsKey(player.getName())) {
            return teleportWaitingList.get(player.getName());
        } else {
            return 0;
        }
    }


}
