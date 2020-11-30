package io.github.wickhamwei.wessential.eventlistener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeEventListener implements Listener {
    @EventHandler
    public void creeperExplode(EntityExplodeEvent event) {
        if (event.getEntityType().equals(EntityType.CREEPER)) {
            event.setCancelled(true);
        }
    }
}
