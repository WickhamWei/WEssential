package io.github.wickhamwei.wessential.wminors.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wminors.WMinors;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveMinors implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);

            if (strings.length >= 1) {
                if (player.isOp()) {
                    for (String string : strings) {
                        WMinors.removeMinors(string);
                        player.sendMessage("&e" + string + WEssentialMain.languageConfig.getConfig().getString("message.w_minors_unset"));
                    }
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.op_only"));
                }
                return true;
            }
        }
        return false;
    }
}
