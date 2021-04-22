package io.github.wickhamwei.wessential.wprotect.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wprotect.WProtect;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SignBreakListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.OAK_WALL_SIGN) {
            Block targetBlock = e.getBlock();
            Sign signBlockState = (Sign) targetBlock.getState();
            if (signBlockState.getLine(0).equals("[WProtect]已上锁")) {
                WallSign targetBlockStateBlockDataWallSign = (WallSign) targetBlock.getState().getBlockData();
                if (targetBlock.getRelative(targetBlockStateBlockDataWallSign.getFacing().getOppositeFace()).getType() == Material.CHEST) {
                    Block chestBlock = targetBlock.getRelative(targetBlockStateBlockDataWallSign.getFacing().getOppositeFace());
                    String chestOwnerName = WProtect.getChestOwnerName(chestBlock);
                    if (chestOwnerName != null) {
                        if (!e.getPlayer().getName().equals(chestOwnerName)) {
                            e.setCancelled(true);
                            WPlayer.getWPlayer(e.getPlayer().getName()).sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_not_owner") + chestOwnerName);
                        } else {
                            if (WProtect.unlockChest(chestBlock)) {
                                WPlayer.getWPlayer(e.getPlayer().getName()).sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_unlock"));
                            }
                        }
                    }
                }
            }
        }
    }
}
