package io.github.wickhamwei.wessential;

import io.github.wickhamwei.wessential.eventlistener.*;
import io.github.wickhamwei.wessential.wlogin.command.ChangePassword;
import io.github.wickhamwei.wessential.wlogin.command.Login;
import io.github.wickhamwei.wessential.wlogin.command.Register;
import io.github.wickhamwei.wessential.wlogin.eventlistener.UnLoginListener;
import io.github.wickhamwei.wessential.wprotect.command.Add;
import io.github.wickhamwei.wessential.wprotect.eventlistener.ChestBreakListener;
import io.github.wickhamwei.wessential.wprotect.eventlistener.ChestLockListener;
import io.github.wickhamwei.wessential.wprotect.eventlistener.OpenChestListener;
import io.github.wickhamwei.wessential.wprotect.eventlistener.SignBreakListener;
import io.github.wickhamwei.wessential.wteleport.command.*;
import io.github.wickhamwei.wessential.wteleport.eventlistener.TeleportInterruptListener;
import io.github.wickhamwei.wessential.wtools.WConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class WEssentialMain extends JavaPlugin {
    public static WEssentialMain wEssentialMain;
    public static WConfig languageConfig;
    public static WConfig homeLocationConfig;
    public static WConfig backLocationConfig;
    public static WConfig passwordConfig;
    public static WConfig chestProtectConfig;

    @Override
    public void onEnable() {
        getLogger().info("欢迎使用 WEssential，作者 WickhamWei阿华");
        wEssentialMain = this;
        loadAllConfig();
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
            getLogger().info("已应用游戏规则：关闭苦力怕爆炸地形破坏");
            getServer().getPluginManager().registerEvents(new CreeperListener(), this);
        }
        if (getConfig().getBoolean("game_rules.protect_farmland")) {
            getLogger().info("已应用游戏规则：保护耕地不受踩踏");
            getServer().getPluginManager().registerEvents(new FarmlandProtectListener(), this);
        }
        if (getConfig().getBoolean("game_rules.keep_inventory_in_all_world")) {
            getLogger().info("已应用游戏规则：在所有世界启用死亡不掉落");
            getServer().getPluginManager().registerEvents(new KeepInventoryListener(), this);
        }
        if (isWLoginEnable()) {
            getLogger().info("WLogin 登录系统已启用");
            getServer().getPluginManager().registerEvents(new UnLoginListener(), this);
        }

        getServer().getPluginManager().registerEvents(new ChestLockListener(), this);
        getServer().getPluginManager().registerEvents(new OpenChestListener(), this);
        getServer().getPluginManager().registerEvents(new SignBreakListener(), this);
        getServer().getPluginManager().registerEvents(new ChestBreakListener(), this);
    }

    private void loadAllConfig() {
        this.saveDefaultConfig();

        languageConfig = new WConfig(getConfig().getString("wessential_setting.language_file"));
        homeLocationConfig = new WConfig("home.yml");
        backLocationConfig = new WConfig("back.yml");
        passwordConfig = new WConfig("password.yml");
        chestProtectConfig = new WConfig("chest.yml");
    }

//    public void reloadAllConfig() {
//        getLogger().info("将重新载入配置文件，但某些设置需要重启才会生效！");
//        this.reloadConfig();
//        for (WConfig wConfig : WConfig.configList) {
//            wConfig.reloadConfig();
//        }
//    }

    private void registerCommand() {
        getLogger().info("正在注册指令");
        Objects.requireNonNull(this.getCommand("home")).setExecutor(new Home());
        Objects.requireNonNull(this.getCommand("sethome")).setExecutor(new SetHome());
        Objects.requireNonNull(this.getCommand("homelist")).setExecutor(new HomeList());
        Objects.requireNonNull(this.getCommand("removehome")).setExecutor(new RemoveHome());
        Objects.requireNonNull(this.getCommand("tpa")).setExecutor(new TeleportAdvanced());
        Objects.requireNonNull(this.getCommand("tpaccept")).setExecutor(new TeleportAccept());
        Objects.requireNonNull(this.getCommand("back")).setExecutor(new Back());
        Objects.requireNonNull(this.getCommand("register")).setExecutor(new Register());
        Objects.requireNonNull(this.getCommand("login")).setExecutor(new Login());
        Objects.requireNonNull(this.getCommand("changepassword")).setExecutor(new ChangePassword());
        Objects.requireNonNull(this.getCommand("tpall")).setExecutor(new TeleportAll());
        Objects.requireNonNull(this.getCommand("add")).setExecutor(new Add());
    }

    public static boolean isWLoginEnable() {
        return WEssentialMain.wEssentialMain.getConfig().getBoolean("login_setting.enable_login_system");
    }
}
