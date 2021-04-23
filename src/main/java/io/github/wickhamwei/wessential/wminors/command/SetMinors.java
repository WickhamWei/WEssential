package io.github.wickhamwei.wessential.wminors.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wlogin.WLogin;
import io.github.wickhamwei.wessential.wminors.WMinors;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.logging.Level;

public class SetMinors implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);

            if (strings.length >= 1) {
                if (player.isOp()) {
                    for (String string : strings) {
                        WMinors.setMinors(string);
                        player.sendMessage("&e" + string + WEssentialMain.languageConfig.getConfig().getString("message.w_minors_set"));
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
