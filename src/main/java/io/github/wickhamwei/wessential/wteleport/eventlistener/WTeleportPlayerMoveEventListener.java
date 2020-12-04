package io.github.wickhamwei.wessential.wteleport.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WTeleportPlayerMoveEventListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        String playerName = event.getPlayer().getName();
        if (WTeleport.removeFromWaitingList(playerName)) {
            WEssentialMain.wEssentialMain.sendMessage(playerName, WEssentialMain.languageConfig.getConfig().getString("message.teleport_interrupt"));
        }
    }
}
