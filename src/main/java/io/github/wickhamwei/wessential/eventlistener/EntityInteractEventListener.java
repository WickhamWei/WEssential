package io.github.wickhamwei.wessential.eventlistener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityInteractEventListener implements Listener {
    @EventHandler
    public void farmlandProtect(EntityInteractEvent event) {
        if (event.getBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }
}
