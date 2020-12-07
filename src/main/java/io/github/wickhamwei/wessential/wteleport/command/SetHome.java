package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;

public class SetHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location playerLocation = player.getLocation();
            if (strings.length == 0) {
                player.setBedSpawnLocation(playerLocation, true);
                WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.home_set_default"));
                return true;
            } else if (strings.length == 1) {
                String playerUniqueId = player.getUniqueId().toString();
                if (!WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId)) {
                    WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId, "");
                }
                Set<String> homes = Collections.emptySet();
                try {
                    homes = WEssentialMain.homeLocationConfig.getConfig().getConfigurationSection(playerUniqueId).getKeys(false);
                } catch (NullPointerException exception) {
                } finally {
                    if ((WEssentialMain.wEssentialMain.getConfig().getInt("teleport_setting.max_other_home") > homes.size()) || WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId + "." + strings[0])) {
                        if (WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId + "." + strings[0])) {
                            WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.home_cover") + " " + strings[0]);
                        }
                        String world = playerLocation.getWorld().getName();
                        double X = playerLocation.getX();
                        double Y = playerLocation.getY();
                        double Z = playerLocation.getZ();
                        WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId + "." + strings[0] + ".world", world);
                        WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId + "." + strings[0] + ".X", X);
                        WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId + "." + strings[0] + ".Y", Y);
                        WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId + "." + strings[0] + ".Z", Z);
                        WEssentialMain.homeLocationConfig.saveConfig();
                        WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.home_set") + " " + strings[0]);
                    } else {
                        WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.no_more_home"));
                    }
                    return true;
                }
            } else {
                return false;
            }
        }else {
            return false;
        }
    }
}
