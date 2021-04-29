package io.github.wickhamwei.wessential.wresidence;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WResidence {
    public static HashMap<String, Location> firstPoint = new HashMap<>();
    public static HashMap<String, Location> secondPoint = new HashMap<>();

    public static void setPoint(WPlayer player, boolean isFirst, Location location) {
        if (isFirst) {
            firstPoint.put(player.getName(), location);
            player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_residence_first_print_set") + Objects.requireNonNull(location.getWorld()).getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ());
        } else {
            secondPoint.put(player.getName(), location);
            player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_residence_second_print_set") + Objects.requireNonNull(location.getWorld()).getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ());
        }
    }

    public static Location getPoint(WPlayer player, boolean isFirst) {
        if (isFirst) {
            return firstPoint.get(player.getName());
        } else {
            return secondPoint.get(player.getName());
        }
    }

    public static void setResidence(WPlayer player, String residenceName, Location locationOne, Location locationTwo) {
        WEssentialMain.residenceConfig.getConfig().set(player.getName() + "." + residenceName + "." + "locationOne", locationOne);
        WEssentialMain.residenceConfig.getConfig().set(player.getName() + "." + residenceName + "." + "locationTwo", locationTwo);
        player.sendMessage(residenceName + WEssentialMain.languageConfig.getConfig().getString("message.w_residence_set"));
        WEssentialMain.residenceConfig.saveConfig();
    }

    public static String[] getResidenceOwnerAndName(Location targetLocation, boolean isPlayer, boolean isFire) {
        Set<String> playerNames = new HashSet<>();
        ConfigurationSection nameConfigSection = WEssentialMain.residenceConfig.getConfig().getConfigurationSection("");
        if (nameConfigSection != null) {
            playerNames = nameConfigSection.getKeys(false);
        }
        for (String playerName : playerNames) {
            Set<String> residenceNames = new HashSet<>();
            ConfigurationSection residenceConfigSection = WEssentialMain.residenceConfig.getConfig().getConfigurationSection(playerName);
            if (residenceConfigSection != null) {
                residenceNames = residenceConfigSection.getKeys(false);
            }
            for (String residenceName : residenceNames) {
                Location locationOne = WEssentialMain.residenceConfig.getConfig().getLocation(playerName + "." + residenceName + "." + "locationOne");
                Location locationTwo = WEssentialMain.residenceConfig.getConfig().getLocation(playerName + "." + residenceName + "." + "locationTwo");
                assert locationOne != null;
                assert locationTwo != null;
                if (isInCube(targetLocation, locationOne, locationTwo, isPlayer, isFire)) {
                    return new String[]{playerName, residenceName};
                }
            }
        }
        return null;
    }

    public static Set<String> getAllResidence(WPlayer player) {
        Set<String> residenceNames = new HashSet<>();
        ConfigurationSection residenceConfigSection = WEssentialMain.residenceConfig.getConfig().getConfigurationSection(player.getName());
        if (residenceConfigSection != null) {
            residenceNames = residenceConfigSection.getKeys(false);
        }
        return residenceNames;
    }

    public static void removeResidence(WPlayer player, String residenceName) {
        WEssentialMain.residenceConfig.getConfig().set(player.getName() + "." + residenceName, null);
        WEssentialMain.residenceConfig.saveConfig();
    }

    private static boolean isInCube(Location targetLocation, Location locationOne, Location locationTwo, boolean isPlayer, boolean isFire) {
        double x1 = locationOne.getX();
        double x2 = locationTwo.getX();
        double y1 = locationOne.getY();
        double y2 = locationTwo.getY();
        double z1 = locationOne.getZ();
        double z2 = locationTwo.getZ();

        double x = targetLocation.getX();
        double y = targetLocation.getY();
        double z = targetLocation.getZ();

        if (isFire) {
            if ((y <= y2 + 1 && y >= y1 - 1) || (y >= y2 - 1 && y <= y1 + 1)) {
                if ((x <= x2 + 1 && x >= x1 - 1) || (x >= x2 - 1 && x <= x1 + 1)) {
                    return (z <= z2 + 1 && z >= z1 - 1) || (z >= z2 - 1 && z <= z1 + 1);
                }
            }
        }

        if ((y <= y2 && y >= y1) || (y >= y2 && y <= y1)) {
            if (isPlayer) {
                if ((x <= x2 + 1 && x >= x1) || (x >= x2 && x <= x1 + 1)) {
                    return (z <= z2 + 1 && z >= z1) || (z >= z2 && z <= z1 + 1);
                }
            } else {
                if ((x <= x2 && x >= x1) || (x >= x2 && x <= x1)) {
                    return (z <= z2 && z >= z1) || (z >= z2 && z <= z1);
                }
            }
        }
        return false;
    }

    public static boolean isBlockInResidence(WPlayer player, Block targetBlock, boolean isFire) {
        if (WPlayer.isLogin(player.getName())) {
            String[] residence = WResidence.getResidenceOwnerAndName(targetBlock.getLocation(), false, isFire);
            if (residence != null) {
                if (!residence[0].equals(player.getName())) {
                    player.sendMessage("&c" + residence[0] + " " + residence[1] + WEssentialMain.languageConfig.getConfig().getString("message.w_residence_disable_event"));
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isBlockInResidence(Block targetBlock, boolean isFire) {
        String[] residence = WResidence.getResidenceOwnerAndName(targetBlock.getLocation(), false, isFire);
        return residence != null;
    }
}
