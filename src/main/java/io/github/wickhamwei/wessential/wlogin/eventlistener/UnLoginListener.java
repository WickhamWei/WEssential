package io.github.wickhamwei.wessential.wlogin.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wlogin.WLogin;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import io.github.wickhamwei.wessential.wtools.WTime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.util.Date;
import java.util.Objects;

public class UnLoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (WEssentialMain.isWLoginEnable() && !WPlayer.isOnline(event.getPlayer().getName())) {
            WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
            if (WLogin.canLogin(player.getName())) {
                if (!player.isRegister()) {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_register"));
                } else {
                    long lastLoginTime = WEssentialMain.passwordConfig.getConfig().getLong(player.getName() + "." + "lastLoginTime");
                    String oldIP = WEssentialMain.passwordConfig.getConfig().getString(player.getName() + "." + "IP");
                    if (WTime.getTimeDifferenceMinutes(WTime.getTime(), lastLoginTime) <= 20 && Objects.equals(oldIP, player.getIPAddress())) {
                        player.autoLogin();
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_auto"));
                    } else {
                        player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_login"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (WPlayer.isOnline(event.getPlayer().getName())) {
            WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
            player.recordUserIP();
        }
    }

    @EventHandler
    public void stop(PlayerCommandPreprocessEvent event) {
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            String message = event.getMessage();
            String[] messageArray = message.split(" ");
            WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
            if (!(messageArray[0].equalsIgnoreCase("/login") || messageArray[0].equalsIgnoreCase("/register"))) {// 检查使用的join命令是否合法，分割字符串
                if (!player.isRegister()) {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_register"));
                } else {
                    player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_login"));
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void stop(PlayerMoveEvent event) {
        WPlayer player = WPlayer.getWPlayer(event.getPlayer().getName());
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            if (!player.isRegister()) {
                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_register"));
            } else {
                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.login_not_login"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void stop(PlayerArmorStandManipulateEvent event) {
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void stop(PlayerBedEnterEvent event) {
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void stop(PlayerBedLeaveEvent event) {
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void stop(PlayerBucketEmptyEvent event) {
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void stop(AsyncPlayerChatEvent event) {
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void stop(PlayerDropItemEvent event) {
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void stop(PlayerInteractEvent event) {
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void stop(InventoryOpenEvent event) {
        if (!WPlayer.isOnline(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }
}
