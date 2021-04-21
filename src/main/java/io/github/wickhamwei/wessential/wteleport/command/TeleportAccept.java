package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAccept implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length == 0) {
                if (WTeleport.isUnderRequest(player)) {
                    WPlayer requsetPlayer = WTeleport.getMainPlayer(player);
                    requsetPlayer.teleport(player.getLocation());
                    WTeleport.stopTeleportRequest(requsetPlayer, player);
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_accept"));
                    return true;
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_accept_null"));
                    return true;
                }
            }
        }
        return false;
    }
}
