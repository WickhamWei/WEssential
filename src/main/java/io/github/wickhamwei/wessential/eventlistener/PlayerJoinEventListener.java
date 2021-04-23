package io.github.wickhamwei.wessential.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wlogin.WLogin;
import io.github.wickhamwei.wessential.wminors.WMinors;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerJoinEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        if (!WEssentialMain.isWLoginEnable()) {
            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(WEssentialMain.languageConfig.getConfig().getString("message.player_join_msg"))));
            WMinors.checkMinors();
        }
    }
}
