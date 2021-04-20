package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAdvanced implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length == 0) {
                return false;
            } else if (strings.length == 1) {
                String targetPlayerName = strings[0];
                if (WPlayer.isOnline(targetPlayerName)) {
                    WPlayer targetPlayer = WPlayer.getWPlayer(targetPlayerName);
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_sent_to") + targetPlayerName);
                    targetPlayer.sendMessage("&e" + playerName + WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_sb_to_you"));

                    //to do
                } else {
                    player.sendMessage("&e" + targetPlayerName + " " + WEssentialMain.languageConfig.getConfig().getString("message.teleport_target_player_null"));
                }
                return true;
            }
        }
        return false;
    }
}
