package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
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

                    // 对方有无正在等待其他人传送
                    if(WTeleport.isUnderRequest(targetPlayer)){
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_target_busy"));
                        return true;
                    }

                    // 自己有无正在等待其他人回复
                    if(WTeleport.isInRequest(player)){
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_main_busy"));
                        return true;
                    }

                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_sent_to") + targetPlayerName);
                    targetPlayer.sendMessage("&e" + playerName + WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_sb_to_you"));

                    WTeleport.newTeleportRequest(player,targetPlayer);
                } else {
                    player.sendMessage("&e" + targetPlayerName + " " + WEssentialMain.languageConfig.getConfig().getString("message.teleport_target_player_null"));
                }
                return true;
            }
        }
        return false;
    }
}
