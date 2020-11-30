package io.github.wickhamwei.wessential.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.translateAlternateColorCodes('&', WEssentialMain.languageConfig.getConfig().getString("message.player_quit_msg")));
    }
}
