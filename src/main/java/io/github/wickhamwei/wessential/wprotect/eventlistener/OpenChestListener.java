package io.github.wickhamwei.wessential.wprotect.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wprotect.WProtect;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class OpenChestListener implements Listener {
    @EventHandler
    public void onPlayerOpenChest(PlayerInteractEvent event) {
        WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) {
                Block targetBlock = event.getClickedBlock();
                String chestOwnerName = WProtect.getChestOwnerName(targetBlock);
                if (chestOwnerName != null) {
                    if (!player.getName().equals(chestOwnerName)) {
                        Set<String> moreUsers = WProtect.getMoreUsers(targetBlock);
                        if (!moreUsers.contains(player.getName())) {
                            event.setCancelled(true);
                            player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_not_owner") + chestOwnerName);
                        }
                    }
                }
            }
        }
    }
}
