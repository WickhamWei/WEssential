package io.github.wickhamwei.wessential.wprotect.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wprotect.WProtect;
import io.github.wickhamwei.wessential.wresidence.WResidence;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestLockListener implements Listener {

    @EventHandler
    public void onPlayerOpenChest(PlayerInteractEvent event) {
        WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) {
                Block chestBlock = event.getClickedBlock();
                if (event.getItem() != null && event.getMaterial() == Material.OAK_SIGN) {
                    if (event.getBlockFace() == BlockFace.EAST || event.getBlockFace() == BlockFace.SOUTH || event.getBlockFace() == BlockFace.WEST || event.getBlockFace() == BlockFace.NORTH) {
                        Block signBlock = chestBlock.getRelative(event.getBlockFace());
                        if (signBlock.getType() == Material.AIR) {
                            if (player.getBukkitPlayer().getGameMode() == GameMode.CREATIVE) {
                                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_survival_only"));
                                return;
                            }
                            if (WResidence.isBlockInResidence(player, chestBlock,false)) {
                                return;
                            }
                            String chestOwnerName = WProtect.getChestOwnerName(chestBlock);
                            if (chestOwnerName == null) {
                                WProtect.lockChest(player, chestBlock, signBlock, event.getBlockFace());
                            } else if (chestOwnerName.equals(player.getName())) {
                                WProtect.ownerClickChest(player, chestBlock);
                            } else {
                                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_already_lock"));
                            }
                        }
                    }
                }
            }
        }
    }

}
