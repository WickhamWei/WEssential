package io.github.wickhamwei.wessential.wresidence.eventlistener;

import io.github.wickhamwei.wessential.wresidence.WResidence;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ResidenceProtectListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (WResidence.isBlockInResidence(WPlayer.getWPlayer(event.getPlayer().getName()), event.getBlock(), false)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (WResidence.isBlockInResidence(WPlayer.getWPlayer(event.getPlayer().getName()), event.getBlockPlaced(), false)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFire(BlockIgniteEvent event) {
        // 允许领地拥有者手动点火
        // 是不是手动点火
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL && event.getPlayer() != null) {
            if (WResidence.isBlockInResidence(WPlayer.getWPlayer(event.getPlayer().getName()), event.getBlock(), true)) {
                event.setCancelled(true);
            }
        } else {
            if (WResidence.isBlockInResidence(event.getBlock(), true)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockFire(BlockBurnEvent event) {
        if (WResidence.isBlockInResidence(event.getBlock(), true)) {
            event.setCancelled(true);
        }
    }
}
