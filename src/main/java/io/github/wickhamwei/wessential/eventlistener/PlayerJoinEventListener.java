package io.github.wickhamwei.wessential.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.translateAlternateColorCodes('&', WEssentialMain.languageConfig.getConfig().getString("message.player_join_msg")));
        WEssentialMain.wEssentialMain.getServer().getLogger().info(event.getPlayer().getName() + WEssentialMain.languageConfig.getConfig().getString("message.player_join_msg"));
    }
}
