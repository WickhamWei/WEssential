package io.github.wickhamwei.wessential.wresidence.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wprotect.WProtect;
import io.github.wickhamwei.wessential.wresidence.WResidence;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

public class ResList implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length == 0) {
                Set<String> allResidence = WResidence.getResidence(player);
                if (allResidence.size() != 0) {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_residence_all") + allResidence);
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_residence_no_more"));
                }
                return true;
            }
        }
        return false;
    }
}
