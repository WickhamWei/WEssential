package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length == 0) {
                if (player.isOp()) {
                    for (WPlayer wPlayer : WPlayer.playerList) {
                        if (WPlayer.isOnline(wPlayer.getName()) && !wPlayer.getName().equals(playerName)) {
                            wPlayer.teleport(player.getLocation(), true);
                        }
                    }
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_all"));
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.op_only"));
                }
                return true;
            }
        }
        return false;
    }
}
