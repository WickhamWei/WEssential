package io.github.wickhamwei.wessential.wlogin.command;

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

public class Login implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (WEssentialMain.isWLoginEnable()) {
                if (strings.length == 1) {
                    if (player.isRegister()) {
                        if (WPlayer.isLogin(playerName)) {
                            player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_already_login"));
                        } else {
                            if (player.login(strings[0])) {
                                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login"));
                                Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(WEssentialMain.languageConfig.getConfig().getString("message.player_join_msg"))));
                                WMinors.checkMinors();
                            } else {
                                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_password_error"));
                                WEssentialMain.wEssentialMain.getLogger().log(Level.WARNING, playerName + WEssentialMain.languageConfig.getConfig().getString("log.password_error"));
                                WLogin.addLoginTimes(playerName);
                            }
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
