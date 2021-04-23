package io.github.wickhamwei.wessential.wlogin.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePassword implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (WEssentialMain.isWLoginEnable()) {
                if (strings.length == 2) {
                    if (player.isRegister()) {
                        if (WPlayer.isLogin(playerName)) {
                            if (strings[0].equals(strings[1])) {
                                player.changePassword(strings[0]);
                                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_change_password_successful"));
                            } else {
                                return false;
                            }
                        } else {
                            player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_login"));
                        }
                    } else {
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_register"));
                    }
                    return true;
                }
            } else {
                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_enable"));
                return true;
            }
        }
        return false;
    }
}
