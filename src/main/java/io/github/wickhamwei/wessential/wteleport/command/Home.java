package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
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
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length == 0) {
                // home
                Location playerHomeLocation = player.getBukkitPlayer().getBedSpawnLocation();
                if (playerHomeLocation == null) {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.home_null"));
                } else {
                    player.teleport(playerHomeLocation);
                }
                return true;
            } else if (strings.length == 1) {
                // home <家名称>
                String playerUniqueId = player.getUniqueId();
                if (WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId + "." + strings[0])) {
                    String worldString = WEssentialMain.homeLocationConfig.getConfig().getString(playerUniqueId + "." + strings[0] + ".world");
                    assert worldString != null;
                    World world = Bukkit.getWorld(worldString);
                    if (world == null) {
                        WEssentialMain.wEssentialMain.getLogger().warning("找不到用户 " + player.getName() + " 想传送的世界 " + worldString);
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.error"));
                    } else {
                        double X = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + strings[0] + ".X");
                        double Y = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + strings[0] + ".Y");
                        double Z = WEssentialMain.homeLocationConfig.getConfig().getDouble(playerUniqueId + "." + strings[0] + ".Z");
                        Location location = new Location(world, X, Y, Z);
                        player.teleport(location);
                    }
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.home_null") + " " + strings[0]);
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
