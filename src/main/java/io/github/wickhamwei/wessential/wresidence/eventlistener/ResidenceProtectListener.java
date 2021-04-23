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
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event) {
//        WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
//        if (WPlayer.isLogin(event.getPlayer().getName())) {
//            String[] residence = WResidence.getResidence(player.getLocation(), true);
//            if (residence != null) {
//                player.sendMessage(residence[0] + " " + residence[1]);
//                event.setCancelled(true);
//            }
//        }
//    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (WResidence.isStopEventInResidence(WPlayer.getWPlayer(event.getPlayer().getName()), event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (WResidence.isStopEventInResidence(WPlayer.getWPlayer(event.getPlayer().getName()), event.getBlockPlaced())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFire(BlockIgniteEvent event) {
        if (WResidence.isStopEventInResidence(event.getBlock(), true)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFire(BlockBurnEvent event) {
        if (WResidence.isStopEventInResidence(event.getBlock(), true)) {
            event.setCancelled(true);
        }
    }
}
