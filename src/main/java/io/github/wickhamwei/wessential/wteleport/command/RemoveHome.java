package io.github.wickhamwei.wessential.wteleport.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length == 1) {
                // removeHome <家名称>
                String playerUniqueId = player.getUniqueId();
                if (!WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId)) {
                    WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId, "");
                }
                if (WEssentialMain.homeLocationConfig.getConfig().contains(playerUniqueId + "." + strings[0])) {
                    WEssentialMain.homeLocationConfig.getConfig().set(playerUniqueId + "." + strings[0], null);
                    WEssentialMain.homeLocationConfig.saveConfig();
                    player.sendMessage(strings[0] + " " + WEssentialMain.languageConfig.getConfig().getString("message.home_remove_successful"));
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.home_null") + " " + strings[0]);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
