package io.github.wickhamwei.wessential;

import io.github.wickhamwei.wessential.eventlistener.*;
import io.github.wickhamwei.wessential.wteleport.command.Home;
import io.github.wickhamwei.wessential.wteleport.command.SetHome;
import io.github.wickhamwei.wessential.wtools.WConfig;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WEssentialMain extends JavaPlugin {
    public static WEssentialMain wEssentialMain;
    public static WConfig languageConfig;

    @Override
    public void onEnable() {
        getLogger().info("欢迎使用 WEssential，作者 WickhamWei");
        wEssentialMain = this;
        loadConfig();
        registerEvents();
        registerCommand();
    }

    @Override
    public void onDisable() {

    }

    private void registerEvents() {
        getLogger().info("正在注册事件");
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEventListener(), this);
        if (getConfig().getBoolean("game_rules.disable_creeper_explode_the_map")) {
            getLogger().info("关闭苦力怕爆炸地形破坏");
            getServer().getPluginManager().registerEvents(new EntityExplodeEventListener(), this);
        }
        if (getConfig().getBoolean("game_rules.protect_farmland")) {
            getLogger().info("保护耕地不受踩踏");
            getServer().getPluginManager().registerEvents(new PlayerInteractEventListener(), this);
            getServer().getPluginManager().registerEvents(new EntityInteractEventListener(), this);
        }
        if (getConfig().getBoolean("game_rules.keep_inventory_in_all_world")) {
            getServer().getPluginManager().registerEvents(new WorldInitEventListener(), this);
        }
    }

    private void loadConfig() {
        this.saveDefaultConfig();
        languageConfig = new WConfig("zh_cn.yml");
//        languageConfig.getConfig().set("message.join_msg", " 加入了游戏");
//        languageConfig.saveConfig();
    }

    private void registerCommand(){
        getLogger().info("正在注册指令");
        this.getCommand("home").setExecutor(new Home());
        this.getCommand("sethome").setExecutor(new SetHome());
    }

    public FileConfiguration getMainConfig() {
        return getConfig();
    }

    public void saveMainConfig() {
        this.saveConfig();
    }

    public void sendMessage(String playerName, String message) {

    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
