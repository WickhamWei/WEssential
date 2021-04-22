package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SetHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            Location playerLocation = player.getLocation();
            if (strings.length == 0) {
                // sethome
                player.getBukkitPlayer().setBedSpawnLocation(playerLocation, true);
                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.home_set_default"));
                return true;
            } else if (strings.length == 1) {
                // sethome <家名称>
                String playerUniqueId = player.getBukkitPlayer().getUniqueId().toString();
                if (!WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId)) {
                    WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId, "");
                }
                ConfigurationSection configurationSection = WEssentialMain.homeLocationConfig.getConfig().getConfigurationSection(playerUniqueId);
                Set<String> homes = new HashSet<>();
                if (configurationSection != null) {
                    homes = configurationSection.getKeys(false);
                }

                if ((WEssentialMain.wEssentialMain.getConfig().getInt("teleport_setting.max_other_home") > homes.size()) || WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId + "." + strings[0])) {
                    // 如果未达到家的上限或者修改已有的家
                    if (WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId + "." + strings[0])) {
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.home_cover") + " " + strings[0]);
                    }
                    String world = Objects.requireNonNull(playerLocation.getWorld()).getName();
                    double X = Math.floor(playerLocation.getX());
                    double Y = Math.floor(playerLocation.getY());
                    double Z = Math.floor(playerLocation.getZ());
                    WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId + "." + strings[0] + ".world", world);
                    WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId + "." + strings[0] + ".X", X);
                    WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId + "." + strings[0] + ".Y", Y);
                    WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId + "." + strings[0] + ".Z", Z);
                    WEssentialMain.homeLocationConfig.saveConfig();
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.home_set") + " " + strings[0]);
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.no_more_home"));
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
