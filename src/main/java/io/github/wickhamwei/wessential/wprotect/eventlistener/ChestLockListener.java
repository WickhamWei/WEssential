package io.github.wickhamwei.wessential.wprotect.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wprotect.WProtect;
import io.github.wickhamwei.wessential.wresidence.WResidence;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ChestLockListener implements Listener {

    @EventHandler
    public void onPlayerOpenChest(PlayerInteractEvent event) {
        WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) {
                if (event.getItem() != null && event.getMaterial() == Material.OAK_SIGN) {
                    if (event.getBlockFace() == BlockFace.EAST || event.getBlockFace() == BlockFace.SOUTH || event.getBlockFace() == BlockFace.WEST || event.getBlockFace() == BlockFace.NORTH) {
                        Block targetBlock = event.getClickedBlock().getRelative(event.getBlockFace());
                        if (targetBlock.getType() == Material.AIR) {

                            if (WResidence.isStopEventInResidence(WPlayer.getWPlayer(event.getPlayer().getName()), event.getClickedBlock())) {
                                return;
                            }

                            if (WProtect.lockChest(player, event.getClickedBlock())) {
                                targetBlock.setType(Material.OAK_WALL_SIGN);
                                if (targetBlock.getState() instanceof Sign) {
                                    Sign targetBlockStateSign = (Sign) targetBlock.getState();
                                    if (targetBlockStateSign.getBlockData() instanceof WallSign) {
                                        WallSign targetBlockStateBlockDataWallSign = (WallSign) targetBlockStateSign.getBlockData();
                                        targetBlockStateBlockDataWallSign.setFacing(event.getBlockFace());
                                        targetBlockStateSign.setLine(0, "[WProtect]已上锁");
                                        targetBlockStateSign.setLine(1, player.getName());
                                        targetBlockStateSign.setBlockData(targetBlockStateBlockDataWallSign);
                                        targetBlockStateSign.update();
                                    }
                                    ItemStack playerMainHand = player.getBukkitPlayer().getInventory().getItemInMainHand();
                                    playerMainHand.setAmount(playerMainHand.getAmount() - 1);
                                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_lock"));
                                    Objects.requireNonNull(targetBlock.getLocation().getWorld()).playSound(targetBlock.getLocation(), Sound.BLOCK_WOOD_PLACE, 1, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
