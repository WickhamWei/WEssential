package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Home implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location playerHomeLocation = player.getBedSpawnLocation();
            if (playerHomeLocation == null) {
                WEssentialMain.sendMessage(player, WEssentialMain.languageConfig.getConfig().getString("message.home_null"));
            } else {
                new WTeleport(player, playerHomeLocation);
            }
            return true;
        }
        return false;
    }
}
