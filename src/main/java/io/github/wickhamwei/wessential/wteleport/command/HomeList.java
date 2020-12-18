package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;

public class HomeList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length == 0) {
                String playerUniqueId = player.getUniqueId().toString();
                Location playerHomeLocation = player.getBedSpawnLocation();
                Set<String> homes = Collections.emptySet();
                try {
                    homes = WEssentialMain.homeLocationConfig.getConfig().getConfigurationSection(playerUniqueId).getKeys(false);
                } catch (NullPointerException exception) {
                } finally {
                    if (playerHomeLocation == null) {
                        WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.no_default_home"));
                    } else {
                        String worldString = playerHomeLocation.getWorld().getName();
                        double X = playerHomeLocation.getX();
                        double Y = playerHomeLocation.getY();
                        double Z = playerHomeLocation.getZ();
                        WEssentialMain.sendMessage(player, "Bed" + WEssentialMain.languageConfig.getConfig().getString("message.home_location") + worldString + " X:" + X + " Y:" + Y + " Z:" + Z);
                    }
                    if (homes.size() == 0) {
                        WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.no_home"));
                    } else {
                        for (String str : homes) {
                            String worldString = WEssentialMain.homeLocationConfig.getConfig().getString(playerUniqueId + "." + str + ".world");
                            double X = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + str + ".X");
                            double Y = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + str + ".Y");
                            double Z = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + str + ".Z");
                            WEssentialMain.sendMessage(player, str + WEssentialMain.languageConfig.getConfig().getString("message.home_location") + worldString + " X:" + X + " Y:" + Y + " Z:" + Z);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
