package io.github.wickhamwei.wessential.wtools;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wlogin.WLogin;
import io.github.wickhamwei.wessential.wteleport.WTeleport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WPlayer {

    public static Set<WPlayer> playerList = new HashSet<>();

    public static WPlayer getWPlayer(String playerName) {
        for (WPlayer player : playerList) {
            if (player.playerName.equals(playerName)) {
                return player;
            }
        }
        return new WPlayer(playerName);
    }

    public static boolean isLogin(String playerName) {
        if (WEssentialMain.isWLoginEnable()) {
            if (Bukkit.getPlayer(playerName) != null) {
                return WLogin.isInLoginList(playerName);
            } else {
                return false;
            }
        } else {
            return Bukkit.getPlayer(playerName) != null;
        }
    }

    public static boolean isOnline(String playerName){
        return Bukkit.getPlayer(playerName) != null;
    }


    public String playerName;

    @Override
    public String toString() {
        return playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WPlayer wPlayer = (WPlayer) o;
        return playerName.equals(wPlayer.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }

    public WPlayer(String playerName) {
        this.playerName = playerName;
        if (isLogin(playerName)) {
            playerList.add(this);
        }
    }


    public String getName() {
        return playerName;
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(playerName);
    }

    public Location getLocation() {
        return getBukkitPlayer().getLocation();
    }

    public boolean isOp() {
        return getBukkitPlayer().isOp();
    }

    public void exitGame() {
        playerList.remove(this);
        if (WEssentialMain.isWLoginEnable()) {
            WLogin.removeFromLoginList(this.getName());
        }
    }

    public void sendMessage(String message) {
        getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void teleport(final Location targetLocation, boolean immediately) {
        if (isOp() || immediately) {
            WTeleport.setBackLocation(this, this.getLocation());
            getBukkitPlayer().teleport(targetLocation);
            WTeleport.stopTeleporting(this);
            WTeleport.stopTeleportCooling(this);
            WTeleport.stopTeleportRequest(this);
            return;
        }
        if (WTeleport.getCoolingTimeLeft(this) != 0) {
            sendMessage(WTeleport.getCoolingTimeLeft(this) + WEssentialMain.languageConfig.getConfig().getString("message.time_cooling_teleport"));
            return;
        }
        if (!WTeleport.isTeleporting(this)) {
            final WPlayer player = this;
            BukkitRunnable teleportBukkitRunnable = new BukkitRunnable() {
                int timeLeft = WEssentialMain.wEssentialMain.getConfig().getInt("teleport_setting.teleport_waiting_time");

                @Override
                public void run() {
                    if (isLogin(player.getName())) {
                        if (WTeleport.isTeleporting(player)) {
                            if (timeLeft > 0) {
                                player.sendMessage(timeLeft + WEssentialMain.languageConfig.getConfig().getString("message.time_left_teleport"));
                                timeLeft--;
                            } else {
                                WTeleport.setBackLocation(player, player.getLocation());
                                player.getBukkitPlayer().teleport(targetLocation);
                                player.sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.teleport_successful"));
                                WTeleport.stopTeleporting(player);
                                WTeleport.newTeleportCooling(player);
                                cancel();
                            }
                        } else {
                            cancel();
                        }
                    } else {
                        WTeleport.stopTeleporting(player);
                        cancel();
                    }
                }
            };
            teleportBukkitRunnable.runTaskTimer(WEssentialMain.wEssentialMain, 0, 20);
            WTeleport.setTeleporting(player);
            // 总是获取到taskId以后，run()才开始执行
        } else {
            sendMessage(WEssentialMain.languageConfig.getConfig().getString("message.already_waiting_teleport"));
        }
    }

    public boolean isRegister() {
        return WEssentialMain.passwordConfig.getConfig().contains(getName() + "." + "password");
    }

    public void changePassword(String newPassword) {
        WEssentialMain.passwordConfig.getConfig().set(getName() + "." + "password", WEncrypt.encrypt(newPassword));
        WEssentialMain.passwordConfig.saveConfig();
        autoLogin();
    }

    public boolean login(String password) {
        if (Objects.equals(WEssentialMain.passwordConfig.getConfig().getString(getName() + "." + "password"), WEncrypt.encrypt(password))) {
            autoLogin();
            return true;
        } else {
            return false;
        }
    }

    public void autoLogin() {
        getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
        WLogin.addInLoginList(getName());
        WLogin.cleanLoginTimes(getName());
        recordUserIP();
    }

    public void recordUserIP() {
        WEssentialMain.passwordConfig.getConfig().set(getName() + "." + "IP", getIPAddress());
        WEssentialMain.passwordConfig.getConfig().set(getName() + "." + "lastLoginTime", WTime.getTime());
        WEssentialMain.passwordConfig.saveConfig();
    }

    public String getIPAddress() {
        return Objects.requireNonNull(getBukkitPlayer().getAddress()).getAddress().getHostAddress();
    }
}
