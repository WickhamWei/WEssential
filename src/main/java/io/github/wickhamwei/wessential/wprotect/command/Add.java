package io.github.wickhamwei.wessential.wprotect.command;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wprotect.WProtect;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class Add implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String playerName = commandSender.getName();
            WPlayer player = WPlayer.getWPlayer(playerName);
            if (strings.length >= 1) {
                Block lastChest = WProtect.getLastChest(player);
                // 您必须选择了一个箱子而且箱子的主人是您
                if (lastChest != null) {
                    String chestOwnerName = WProtect.getChestOwnerName(lastChest);
                    if (chestOwnerName != null && chestOwnerName.equals(playerName)) {
                        for (String string : strings) {
                            if (WProtect.setMoreUser(lastChest, string)) {
                                player.sendMessage("&e" + string + WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_user_add"));
                            }
                        }
                        Set<String> moreUsers = WProtect.getMoreUsers(lastChest);
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_all_user") + moreUsers);
                        return true;
                    }
                }
                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.w_protect_chest_unknown"));
                return true;
            }
        }
        return false;
    }
}
