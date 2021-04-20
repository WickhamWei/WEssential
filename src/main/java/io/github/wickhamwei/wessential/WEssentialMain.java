package io.github.wickhamwei.wessential;

import io.github.wickhamwei.wessential.eventlistener.*;
import io.github.wickhamwei.wessential.wteleport.command.Home;
import io.github.wickhamwei.wessential.wteleport.command.HomeList;
import io.github.wickhamwei.wessential.wteleport.command.RemoveHome;
import io.github.wickhamwei.wessential.wteleport.command.SetHome;
import io.github.wickhamwei.wessential.wteleport.eventlistener.TeleportInterruptListener;
import io.github.wickhamwei.wessential.wtools.WConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class WEssentialMain extends JavaPlugin {
    public static WEssentialMain wEssentialMain;
    public static WConfig languageConfig;
    public static WConfig homeLocationConfig;

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
        getServer().getPluginManager().registerEvents(new TeleportInterruptListener(), this);
        if (getConfig().getBoolean("game_rules.disable_creeper_explode_the_map")) {
            getLogger().info("关闭苦力怕爆炸地形破坏");
            getServer().getPluginManager().registerEvents(new CreeperListener(), this);
        }
        if (getConfig().getBoolean("game_rules.protect_farmland")) {
            getLogger().info("保护耕地不受踩踏");
            getServer().getPluginManager().registerEvents(new FarmlandProtectListener(), this);
        }
        if (getConfig().getBoolean("game_rules.keep_inventory_in_all_world")) {
            getServer().getPluginManager().registerEvents(new KeepInventoryListener(), this);
        }
    }

    private void loadConfig() {
        this.saveDefaultConfig();
        languageConfig = new WConfig(getConfig().getString("wessential_setting.language_file"));
        homeLocationConfig = new WConfig("home.yml");
    }

    private void registerCommand() {
        getLogger().info("正在注册指令");
        Objects.requireNonNull(this.getCommand("home")).setExecutor(new Home());
        Objects.requireNonNull(this.getCommand("sethome")).setExecutor(new SetHome());
        Objects.requireNonNull(this.getCommand("homelist")).setExecutor(new HomeList());
        Objects.requireNonNull(this.getCommand("removehome")).setExecutor(new RemoveHome());
    }

}
