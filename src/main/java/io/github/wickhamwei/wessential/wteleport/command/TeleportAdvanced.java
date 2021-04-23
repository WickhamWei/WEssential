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
            if (strings.length == 1) {
                String targetPlayerName = strings[0];
                if (targetPlayerName.equals(playerName)) {
                    return false;
                }
                if (WPlayer.isLogin(targetPlayerName)) {
                    WPlayer targetPlayer = WPlayer.getWPlayer(targetPlayerName);

                    if (WTeleport.getCoolingTimeLeft(player) != 0) {
                        player.sendMessage(WTeleport.getCoolingTimeLeft(player) + WEssentialMain.languageConfig.getConfig().getString("message.time_cooling_teleport"));
                        return true;
                    }

                    if (WTeleport.isTeleporting(player)) {
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.already_waiting_teleport"));
                        return true;
                    }

                    // 自己有无正在等待其他人回复
                    if (WTeleport.isInRequest(player)) {
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_main_busy"));
                        return true;
                    }

                    // 自己有无未接受的其他人请求
                    if (WTeleport.isUnderRequest(player)) {
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_under_request"));
                        return true;
                    }

                    // 对方有无正在等待其他人传送 或 对方正在等待传送到其他人
                    if (WTeleport.isUnderRequest(targetPlayer) || WTeleport.isInRequest(targetPlayer)) {
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_target_busy"));
                        return true;
                    }

                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_sent_to") + targetPlayerName);
                    targetPlayer.sendMessage("&e" + playerName + WEssentialMain.languageConfig.getConfig().getString("message.teleport_request_sb_to_you"));

                    WTeleport.newTeleportRequest(player, targetPlayer);
                } else {
                    player.sendMessage("&e" + targetPlayerName + " " + WEssentialMain.languageConfig.getConfig().getString("message.teleport_target_player_null"));
                }
                return true;
            }
        }
        return false;
    }
}
