package io.github.wickhamwei.wessential.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String message = WEssentialMain.languageConfig.getConfig().getString("message.player_quit_msg");
        if (message != null) {
            event.setQuitMessage(ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.translateAlternateColorCodes('&', message));
        }
        WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
        player.exitGame();
    }
}
