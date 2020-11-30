package io.github.wickhamwei.wessential.wteleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WTeleport {
    public WTeleport(Player player, Location targetLocation) {
        player.teleport(targetLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
}
