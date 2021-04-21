package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Back implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length == 0) {
                // back
                if (WTeleport.getBackLocation(player) != null) {
                    Location backLocation = WTeleport.getBackLocation(player);
                    player.teleport(backLocation);
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.back_null"));
                }
                return true;
            }
        }
        return false;
    }
}
