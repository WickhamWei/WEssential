package io.github.wickhamwei.wessential.wprotect.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wprotect.WProtect;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ChestBreakListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.CHEST) {
            String chestOwnerName = WProtect.getChestOwnerName(e.getBlock());
            if (chestOwnerName != null) {
                if (!e.getPlayer().getName().equals(chestOwnerName)) {
                    WPlayer.getWPlayer(e.getPlayer().getName()).sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_not_owner") + chestOwnerName);
                    e.setCancelled(true);
                } else {
                    if (WProtect.unlockChest(e.getBlock())) {
                        WPlayer.getWPlayer(e.getPlayer().getName()).sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_unlock"));
                    }
                }
            }
        }
    }
}
