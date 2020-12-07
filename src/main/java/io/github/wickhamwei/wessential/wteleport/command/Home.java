package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Home implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length == 0) {
                Location playerHomeLocation = player.getBedSpawnLocation();
                if (playerHomeLocation == null) {
                    WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.home_null"));
                } else {
                    WTeleport.teleport(player, playerHomeLocation);
                }
                return true;
            } else if (strings.length == 1) {
                String playerUniqueId = player.getUniqueId().toString();
                if (WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId + "." + strings[0])) {
                    String worldString = WEssentialMain.homeLocationConfig.getConfig().getString(playerUniqueId + "." + strings[0] + ".world");
                    World world = Bukkit.getWorld(worldString);
                    if (world == null) {
                        WEssentialMain.wEssentialMain.getLogger().warning("找不到用户 " + player.getName() + " 想传送的世界 " + worldString);
                        WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.error"));
                    } else {
                        double X = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + strings[0] + ".X");
                        double Y = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + strings[0] + ".Y");
                        double Z = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + strings[0] + ".Z");
                        Location location = new Location(world, X, Y, Z);
                        WTeleport.teleport(player, location);
                    }
                } else {
                    WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.home_null") + " " + strings[0]);
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
