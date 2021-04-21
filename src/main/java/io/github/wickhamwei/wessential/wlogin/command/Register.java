package io.github.wickhamwei.wessential.wlogin.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Register implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (WEssentialMain.isWLoginEnable()) {
                if (strings.length == 2) {
                    if (WPlayer.isOnline(playerName) || player.isRegister()) {
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_already_register"));
                        return true;
                    } else {
                        if (strings[0].equals(strings[1])) {
                            player.changePassword(strings[0]);
                            player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_and_register"));
                            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(WEssentialMain.languageConfig.getConfig().getString("message.player_join_msg"))));
                            return true;
                        }
                    }
                }
            } else {
                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_enable"));
                return true;
            }
        }
        return false;
    }
}
