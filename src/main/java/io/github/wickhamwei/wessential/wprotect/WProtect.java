package io.github.wickhamwei.wessential.wprotect;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import io.github.wickhamwei.wessential.wtools.WTime;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.*;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WProtect {
    public static void lockChest(WPlayer player, Block chestBlock, Block signBlock,BlockFace signFace) {
        signBlock.setType(Material.OAK_WALL_SIGN);
        Objects.requireNonNull(signBlock.getLocation().getWorld()).playSound(signBlock.getLocation(), Sound.BLOCK_WOOD_PLACE, 1, 0);
        Sign targetBlockStateSign = (Sign) signBlock.getState();
        WallSign targetBlockStateBlockDataWallSign = (WallSign) targetBlockStateSign.getBlockData();
        targetBlockStateBlockDataWallSign.setFacing(signFace);
        targetBlockStateSign.setLine(0, "[WProtect]已上锁");
        targetBlockStateSign.setLine(1, player.getName());
        targetBlockStateSign.setBlockData(targetBlockStateBlockDataWallSign);
        targetBlockStateSign.update();

        ItemStack playerMainHand = player.getBukkitPlayer().getInventory().getItemInMainHand();
        playerMainHand.setAmount(playerMainHand.getAmount() - 1);
        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_lock"));

        String playerName = player.getName();
        String world = Objects.requireNonNull(chestBlock.getLocation().getWorld()).getName();
        double X = Math.floor(chestBlock.getLocation().getX());
        double Y = Math.floor(chestBlock.getLocation().getY());
        double Z = Math.floor(chestBlock.getLocation().getZ());
        long time = WTime.getTime();
        WEssentialMain.chestProtectConfig.getConfig().set(playerName + "." + time + ".world", world);
        WEssentialMain.chestProtectConfig.getConfig().set(playerName + "." + time + ".X", X);
        WEssentialMain.chestProtectConfig.getConfig().set(playerName + "." + time + ".Y", Y);
        WEssentialMain.chestProtectConfig.getConfig().set(playerName + "." + time + ".Z", Z);
        WEssentialMain.chestProtectConfig.saveConfig();
    }

    public static void ownerClickChest(WPlayer owner, Block chestBlock) {
        owner.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_already_lock_by_you"));
        setLastChest(owner.getName(), chestBlock);
        owner.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_select"));
        Set<String> moreUsers = WProtect.getMoreUsers(chestBlock);
        if (moreUsers.size() != 0) {
            owner.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_all_user") + moreUsers);
        } else {
            owner.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_no_more_user"));
        }
    }

    public static String getChestOwnerName(Block chestBlock) { // 判断是否被锁上
        Chest chest = (Chest) chestBlock.getState();
        InventoryHolder holder = chest.getInventory().getHolder();
        if (holder instanceof DoubleChest) {
            DoubleChest doubleChest = ((DoubleChest) holder);
            Chest leftChest = (Chest) doubleChest.getLeftSide();
            Chest rightChest = (Chest) doubleChest.getRightSide();
            assert leftChest != null;
            String chestOwnerName = getChestOwnerNameInChestBlock(leftChest);
            if (chestOwnerName != null) {
                return chestOwnerName;
            }
            assert rightChest != null;
            return getChestOwnerNameInChestBlock(rightChest);
        } else {
            return getChestOwnerNameInChestBlock(chest);
        }
    }

    private static String getChestOwnerNameInChestBlock(Chest chestBlock) {
        Location chestLocation = chestBlock.getLocation();
        Set<String> playerNames = new HashSet<>();
        ConfigurationSection nameConfigSection = WEssentialMain.chestProtectConfig.getConfig().getConfigurationSection("");
        if (nameConfigSection != null) {
            playerNames = nameConfigSection.getKeys(false);
        }
        for (String playerName : playerNames) {
            Set<String> times = new HashSet<>();
            ConfigurationSection configurationSection = WEssentialMain.chestProtectConfig.getConfig().getConfigurationSection(playerName);
            if (configurationSection != null) {
                times = configurationSection.getKeys(false);
            }
            for (String time : times) {
                if (Objects.requireNonNull(chestLocation.getWorld()).getName().equals(WEssentialMain.chestProtectConfig.getConfig().getString(playerName + "." + time + "." + "world"))) {
                    if (Math.floor(chestLocation.getX()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "X")) {
                        if (Math.floor(chestLocation.getY()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Y")) {
                            if (Math.floor(chestLocation.getZ()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Z")) {
                                return playerName;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean unlockChest(Block chestBlock) {
        int lockNumber = 0;

        Location chestLocation = chestBlock.getLocation();
        Set<String> playerNames = new HashSet<>();
        ConfigurationSection nameConfigSection = WEssentialMain.chestProtectConfig.getConfig().getConfigurationSection("");
        if (nameConfigSection != null) {
            playerNames = nameConfigSection.getKeys(false);
        }
        for (String playerName : playerNames) {
            Set<String> times = new HashSet<>();
            ConfigurationSection configurationSection = WEssentialMain.chestProtectConfig.getConfig().getConfigurationSection(playerName);
            if (configurationSection != null) {
                times = configurationSection.getKeys(false);
            }
            for (String time : times) {
                if (Objects.requireNonNull(chestLocation.getWorld()).getName().equals(WEssentialMain.chestProtectConfig.getConfig().getString(playerName + "." + time + "." + "world"))) {
                    if (Math.floor(chestLocation.getX()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "X")) {
                        if (Math.floor(chestLocation.getY()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Y")) {
                            if (Math.floor(chestLocation.getZ()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Z")) {
                                WEssentialMain.chestProtectConfig.getConfig().set(playerName + "." + time, null);
                                WEssentialMain.chestProtectConfig.saveConfig();
                                lockNumber++;
                            }
                        }
                    }
                }
            }
        }

        return lockNumber > 0;
    }

    public static HashMap<String, Block> playerLastChestList = new HashMap<>();

    public static Block getLastChest(WPlayer player) {
        return playerLastChestList.get(player.getName());
    }

    public static void setLastChest(String playerName, Block targetChest) {
        playerLastChestList.put(playerName, targetChest);
    }

    public static boolean setMoreUser(Block chestBlock, String moreUserName) {
        Chest chest = (Chest) chestBlock.getState();
        InventoryHolder holder = chest.getInventory().getHolder();
        if (holder instanceof DoubleChest) {
            DoubleChest doubleChest = ((DoubleChest) holder);
            Chest leftChest = (Chest) doubleChest.getLeftSide();
            Chest rightChest = (Chest) doubleChest.getRightSide();
            assert leftChest != null;
            if (setMoreUserInChestBlock(leftChest, moreUserName)) {
                return true;
            } else {
                assert rightChest != null;
                return setMoreUserInChestBlock(rightChest, moreUserName);
            }
        } else {
            return setMoreUserInChestBlock(chest, moreUserName);
        }
    }

    private static boolean setMoreUserInChestBlock(Chest chestBlock, String moreUserName) {
        Location chestLocation = chestBlock.getLocation();
        Set<String> playerNames = new HashSet<>();
        ConfigurationSection nameConfigSection = WEssentialMain.chestProtectConfig.getConfig().getConfigurationSection("");
        if (nameConfigSection != null) {
            playerNames = nameConfigSection.getKeys(false);
        }
        for (String playerName : playerNames) {
            Set<String> times = new HashSet<>();
            ConfigurationSection configurationSection = WEssentialMain.chestProtectConfig.getConfig().getConfigurationSection(playerName);
            if (configurationSection != null) {
                times = configurationSection.getKeys(false);
            }
            for (String time : times) {
                if (Objects.requireNonNull(chestLocation.getWorld()).getName().equals(WEssentialMain.chestProtectConfig.getConfig().getString(playerName + "." + time + "." + "world"))) {
                    if (Math.floor(chestLocation.getX()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "X")) {
                        if (Math.floor(chestLocation.getY()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Y")) {
                            if (Math.floor(chestLocation.getZ()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Z")) {
                                WEssentialMain.chestProtectConfig.getConfig().set(playerName + "." + time + "." + "moreUser" + "." + moreUserName, WTime.getTime());
                                WEssentialMain.chestProtectConfig.saveConfig();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Set<String> getMoreUsers(Block chestBlock) {
        Chest chest = (Chest) chestBlock.getState();
        InventoryHolder holder = chest.getInventory().getHolder();
        if (holder instanceof DoubleChest) {
            DoubleChest doubleChest = ((DoubleChest) holder);
            Chest leftChest = (Chest) doubleChest.getLeftSide();
            Chest rightChest = (Chest) doubleChest.getRightSide();
            assert leftChest != null;
            Set<String> moreUsersLeftChest = getMoreUsersInChestBlock(leftChest);
            if (moreUsersLeftChest.size() != 0) {
                return moreUsersLeftChest;
            } else {
                assert rightChest != null;
                return getMoreUsersInChestBlock(rightChest);
            }
        } else {
            return getMoreUsersInChestBlock(chest);
        }
    }

    private static Set<String> getMoreUsersInChestBlock(Chest chestBlock) {
        Set<String> users = new HashSet<>();

        Location chestLocation = chestBlock.getLocation();
        Set<String> playerNames = new HashSet<>();
        ConfigurationSection nameConfigSection = WEssentialMain.chestProtectConfig.getConfig().getConfigurationSection("");
        if (nameConfigSection != null) {
            playerNames = nameConfigSection.getKeys(false);
        }
        for (String playerName : playerNames) {
            Set<String> times = new HashSet<>();
            ConfigurationSection configurationSection = WEssentialMain.chestProtectConfig.getConfig().getConfigurationSection(playerName);
            if (configurationSection != null) {
                times = configurationSection.getKeys(false);
            }
            for (String time : times) {
                if (Objects.requireNonNull(chestLocation.getWorld()).getName().equals(WEssentialMain.chestProtectConfig.getConfig().getString(playerName + "." + time + "." + "world"))) {
                    if (Math.floor(chestLocation.getX()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "X")) {
                        if (Math.floor(chestLocation.getY()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Y")) {
                            if (Math.floor(chestLocation.getZ()) == WEssentialMain.chestProtectConfig.getConfig().getDouble(playerName + "." + time + "." + "Z")) {
                                ConfigurationSection usersConfigSection = WEssentialMain.chestProtectConfig.getConfig().getConfigurationSection(playerName + "." + time + "." + "moreUser");
                                if (usersConfigSection != null) {
                                    users = usersConfigSection.getKeys(false);
                                }
                                return users;
                            }
                        }
                    }
                }
            }
        }
        return users;
    }
}
