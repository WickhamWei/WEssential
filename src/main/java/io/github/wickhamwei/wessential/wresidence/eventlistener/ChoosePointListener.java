package io.github.wickhamwei.wessential.wresidence.eventlistener;

import io.github.wickhamwei.wessential.wresidence.WResidence;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChoosePointListener implements Listener {

    @EventHandler
    public void onPlayerUseStick(PlayerInteractEvent event) {
        WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            boolean first = event.getAction() == Action.LEFT_CLICK_BLOCK;
            if (event.getClickedBlock() != null) {
                if (event.getItem() != null && event.getMaterial() == Material.STICK) {
                    Block targetBlock = event.getClickedBlock();
                    if (player.isOp() && player.getBukkitPlayer().getGameMode() == GameMode.CREATIVE) {
                        WResidence.setPoint(player, first, targetBlock.getLocation());
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
