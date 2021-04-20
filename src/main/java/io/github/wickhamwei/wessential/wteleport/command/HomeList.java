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

public class HomeList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length == 0) {
                // homelist
                String playerUniqueId = player.getUniqueId();
                Location playerHomeLocation = player.getBukkitPlayer().getBedSpawnLocation();
                Set<String> playerHomeList = new HashSet<>();
                ConfigurationSection configurationSection = WEssentialMain.homeLocationConfig.getConfig().getConfigurationSection(playerUniqueId);
                if (configurationSection != null) {
                    playerHomeList = configurationSection.getKeys(false);
                }
                if (playerHomeLocation == null) {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.no_default_home"));
                } else {
                    String worldString = Objects.requireNonNull(playerHomeLocation.getWorld()).getName();
                    double X = Math.floor(playerHomeLocation.getX());
                    double Y = Math.floor(playerHomeLocation.getY());
                    double Z = Math.floor(playerHomeLocation.getZ());
                    player.sendMessage("Bed" + WEssentialMain.languageConfig.getConfig().getString("message.home_location") + worldString + " X:" + X + " Y:" + Y + " Z:" + Z);
                }
                if (playerHomeList.size() != 0) {
                    for (String str : playerHomeList) {
                        String worldString = WEssentialMain.homeLocationConfig.getConfig().getString(playerUniqueId + "." + str + ".world");
                        double X = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + str + ".X");
                        double Y = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + str + ".Y");
                        double Z = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + str + ".Z");
                        player.sendMessage(str + WEssentialMain.languageConfig.getConfig().getString("message.home_location") + worldString + " X:" + X + " Y:" + Y + " Z:" + Z);
                    }
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.no_home"));
                }
                return true;
            }
        }
        return false;
    }
}
