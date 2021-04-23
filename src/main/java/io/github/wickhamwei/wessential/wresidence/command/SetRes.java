package io.github.wickhamwei.wessential.wresidence.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wresidence.WResidence;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SetRes implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length == 1) {
                if (player.isOp() && player.getBukkitPlayer().getGameMode() == GameMode.CREATIVE) {
                    Location pointOne = WResidence.getPoint(player, true);
                    Location pointTwo = WResidence.getPoint(player, false);
                    if (pointOne != null && pointTwo != null && Objects.requireNonNull(pointOne.getWorld()).getName().equals(Objects.requireNonNull(pointTwo.getWorld()).getName())) {
                        WResidence.setResidence(player, strings[0], pointOne, pointTwo);
                    } else {
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_residence_set_fail"));
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
