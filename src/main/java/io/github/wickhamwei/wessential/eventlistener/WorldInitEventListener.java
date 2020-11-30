package io.github.wickhamwei.wessential.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class WorldInitEventListener implements Listener {
    @EventHandler
    public void keepInventory(WorldInitEvent event) {
        World world = event.getWorld();
        if (world.setGameRule(GameRule.KEEP_INVENTORY, true)) {
            WEssentialMain.wEssentialMain.getLogger().info("死亡是否保留背包内的物品在 " + world.getName() + " 设置为 " + true);
        }
    }
}
