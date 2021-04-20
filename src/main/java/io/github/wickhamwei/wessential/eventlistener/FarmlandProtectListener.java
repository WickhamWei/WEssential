package io.github.wickhamwei.wessential.eventlistener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FarmlandProtectListener implements Listener {
    @EventHandler
    public void farmlandProtect(EntityInteractEvent event) {
        if (event.getBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void farmlandProtect(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if(event.getClickedBlock()!=null){
                if (event.getClickedBlock().getType() == Material.FARMLAND) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
