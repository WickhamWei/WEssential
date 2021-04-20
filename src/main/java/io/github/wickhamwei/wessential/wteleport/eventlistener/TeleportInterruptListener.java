package io.github.wickhamwei.wessential.wteleport.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TeleportInterruptListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
        if (WTeleport.isTeleporting(player)) {
            WTeleport.stopTeleporting(player);
            player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_interrupt"));
        }
    }

    @EventHandler
    public void onPlayerDamaged(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            WPlayer player = WPlayer.getWPlayer(event.getEntity().getName());
            if (WTeleport.isTeleporting(player)) {
                WTeleport.stopTeleporting(player);
                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_interrupt"));
            }
        }
    }
}
