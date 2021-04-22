package io.github.wickhamwei.wessential.wprotect;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import io.github.wickhamwei.wessential.wtools.WTime;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WProtect {
    public static boolean lockChest(WPlayer player, Block chestBlock) {
        if (chestBlock.getType() == Material.CHEST) {   // 判断是否是箱子
            String chestOwnerName = getChestOwnerName(chestBlock);
            if (chestOwnerName == null) {  // 判断是否被锁上
                String playerName = player.getName();
                String world = Objects.requireNonNull(chestBlock.getLocation().getWorld()).getName();
                double X = Math.floor(chestBlock.getLocation().getX());
                double Y = Math.floor(chestBlock.getLocation().getY());
                double Z = Math.floor(chestBlock.getLocation().getZ());
                long time = WTime.getTime();
                WEssentialMain.wProtectConfig.getConfig().set(playerName + "." + time + ".world", world);
                WEssentialMain.wProtectConfig.getConfig().set(playerName + "." + time + ".X", X);
                WEssentialMain.wProtectConfig.getConfig().set(playerName + "." + time + ".Y", Y);
                WEssentialMain.wProtectConfig.getConfig().set(playerName + "." + time + ".Z", Z);
                WEssentialMain.wProtectConfig.saveConfig();
                return true;
            }else if (chestOwnerName.equals(player.getName())){
                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_already_lock_by_you"));
            }else {
                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_already_lock"));
            }
        }
        return false;
    }

    public static String getChestOwnerName(Block chestBlock) { // 判断是否被锁上
        Chest chest = (Chest) chestBlock.getState();
        InventoryHolder holder = chest.getInventory().getHolder();
        if (holder instanceof DoubleChest) {
            DoubleChest doubleChest = ((DoubleChest) holder);
            Chest leftChest = (Chest) doubleChest.getLeftSide();
            Chest rightChest = (Chest) doubleChest.getRightSide();
            assert leftChest != null;
            String chestOwnerName = getAroundSignOwnerName(leftChest);
            if (chestOwnerName != null) {
                return chestOwnerName;
            }
            assert rightChest != null;
            return getAroundSignOwnerName(rightChest);
        } else {
            return getAroundSignOwnerName(chest);
        }
    }

    private static String getAroundSignOwnerName(Chest chestBlock) {

//        // 先找牌子
//        BlockFace[] around = {BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH};
//        for (BlockFace blockFace : around) {
//            if (chestBlock.getBlock().getRelative(blockFace).getType() == Material.OAK_WALL_SIGN) {
//                Block sign = chestBlock.getBlock().getRelative(blockFace);
//                if (sign.getState() instanceof Sign) {
//                    Sign signBlockState = (Sign) sign.getState();
//                    if (signBlockState.getLine(0).equals("[WProtect]已上锁")) {
//                        if (isLockInDatabase(WPlayer.getWPlayer(signBlockState.getLine(1)), chestBlock.getLocation())) {
//                            return signBlockState.getLine(1);
//                        }
//                    }
//                }
//            }
//        }
//        return null;

        // 不找牌子，直接遍历

        Location chestLocation = chestBlock.getLocation();
        Set<String> playerNames = new HashSet<>();
        ConfigurationSection nameConfigSection = WEssentialMain.wProtectConfig.getConfig().getConfigurationSection("");
        if (nameConfigSection != null) {
            playerNames = nameConfigSection.getKeys(false);
        }
        for (String playerName : playerNames) {
            Set<String> times = new HashSet<>();
            ConfigurationSection configurationSection = WEssentialMain.wProtectConfig.getConfig().getConfigurationSection(playerName);
            if (configurationSection != null) {
                times = configurationSection.getKeys(false);
            }
            for (String time : times) {
                if (Objects.requireNonNull(chestLocation.getWorld()).getName().equals(WEssentialMain.wProtectConfig.getConfig().getString(playerName + "." + time + "." + "world"))) {
                    if (Math.floor(chestLocation.getX()) == WEssentialMain.wProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "X")) {
                        if (Math.floor(chestLocation.getY()) == WEssentialMain.wProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Y")) {
                            if (Math.floor(chestLocation.getZ()) == WEssentialMain.wProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Z")) {
                                return playerName;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

//    private static boolean isLockInDatabase(WPlayer player, Location chestLocation) {
//        Set<String> times = new HashSet<>();
//        ConfigurationSection configurationSection = WEssentialMain.wProtectConfig.getConfig().getConfigurationSection(player.getName());
//        if (configurationSection != null) {
//            times = configurationSection.getKeys(false);
//        }
//        for (String time : times) {
//            if (Objects.requireNonNull(chestLocation.getWorld()).getName().equals(WEssentialMain.wProtectConfig.getConfig().getString(player.getName() + "." + time + "." + "world"))) {
//                if (Math.floor(chestLocation.getX()) == WEssentialMain.wProtectConfig.getConfig().getDouble(player.getName() + "." + time + "." + "X")) {
//                    if (Math.floor(chestLocation.getY()) == WEssentialMain.wProtectConfig.getConfig().getDouble(player.getName() + "." + time + "." + "Y")) {
//                        if (Math.floor(chestLocation.getZ()) == WEssentialMain.wProtectConfig.getConfig().getDouble(player.getName() + "." + time + "." + "Z")) {
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }

    public static boolean unlockChest(Block chestBlock) {

        int lockNumber = 0;
        Location chestLocation = chestBlock.getLocation();

        Set<String> playerNames = new HashSet<>();
        ConfigurationSection nameConfigSection = WEssentialMain.wProtectConfig.getConfig().getConfigurationSection("");
        if (nameConfigSection != null) {
            playerNames = nameConfigSection.getKeys(false);
        }
        for (String playerName : playerNames) {
            Set<String> times = new HashSet<>();
            ConfigurationSection configurationSection = WEssentialMain.wProtectConfig.getConfig().getConfigurationSection(playerName);
            if (configurationSection != null) {
                times = configurationSection.getKeys(false);
            }
            for (String time : times) {
                if (Objects.requireNonNull(chestLocation.getWorld()).getName().equals(WEssentialMain.wProtectConfig.getConfig().getString(playerName + "." + time + "." + "world"))) {
                    if (Math.floor(chestLocation.getX()) == WEssentialMain.wProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "X")) {
                        if (Math.floor(chestLocation.getY()) == WEssentialMain.wProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Y")) {
                            if (Math.floor(chestLocation.getZ()) == WEssentialMain.wProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Z")) {
                                WEssentialMain.wProtectConfig.getConfig().set(playerName + "." + time, null);
                                WEssentialMain.wProtectConfig.saveConfig();
                                lockNumber++;
                            }
                        }
                    }
                }
            }
        }

        return lockNumber > 0;
    }
}
