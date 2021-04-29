package io.github.wickhamwei.wessential;

import io.github.wickhamwei.wessential.eventlistener.*;
import io.github.wickhamwei.wessential.wlogin.command.ChangePassword;
import io.github.wickhamwei.wessential.wlogin.command.Login;
import io.github.wickhamwei.wessential.wlogin.command.Register;
import io.github.wickhamwei.wessential.wlogin.eventlistener.UnLoginListener;
import io.github.wickhamwei.wessential.wminors.command.IsMinors;
import io.github.wickhamwei.wessential.wminors.command.MinorsList;
import io.github.wickhamwei.wessential.wminors.command.RemoveMinors;
import io.github.wickhamwei.wessential.wminors.command.SetMinors;
import io.github.wickhamwei.wessential.wminors.eventlistener.MessageListener;
import io.github.wickhamwei.wessential.wprotect.command.Add;
import io.github.wickhamwei.wessential.wprotect.eventlistener.ChestBreakListener;
import io.github.wickhamwei.wessential.wprotect.eventlistener.ChestLockListener;
import io.github.wickhamwei.wessential.wprotect.eventlistener.OpenChestListener;
import io.github.wickhamwei.wessential.wprotect.eventlistener.SignBreakListener;
import io.github.wickhamwei.wessential.wresidence.command.RemoveRes;
import io.github.wickhamwei.wessential.wresidence.command.ResList;
import io.github.wickhamwei.wessential.wresidence.command.SetRes;
import io.github.wickhamwei.wessential.wresidence.eventlistener.ChoosePointListener;
import io.github.wickhamwei.wessential.wresidence.eventlistener.ResidenceProtectListener;
import io.github.wickhamwei.wessential.wteleport.command.*;
import io.github.wickhamwei.wessential.wteleport.eventlistener.TeleportInterruptListener;
import io.github.wickhamwei.wessential.wtools.WConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class WEssentialMain extends JavaPlugin {
    public static WEssentialMain wEssentialMain;
    public static WConfig languageConfig;
    public static WConfig homeLocationConfig;
    public static WConfig backLocationConfig;
    public static WConfig passwordConfig;
    public static WConfig chestProtectConfig;
    public static WConfig minorsPlayerConfig;
    public static WConfig residenceConfig;

    public static final String ConfigVersion = "1.0";
    public static final String languageVersion = "1.2";
    public static final String url = "https://api.github.com/repos/WickhamWei/WEssential/releases/latest";

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
        if (isWLoginEnable()) {
            getLogger().info("WLogin 登录系统已启用");
            getServer().getPluginManager().registerEvents(new UnLoginListener(), this);
        }

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

        getServer().getPluginManager().registerEvents(new ChestLockListener(), this);
        getServer().getPluginManager().registerEvents(new OpenChestListener(), this);
        getServer().getPluginManager().registerEvents(new SignBreakListener(), this);
        getServer().getPluginManager().registerEvents(new ChestBreakListener(), this);
//        getServer().getPluginManager().registerEvents(new CheckMinorsListener(), this);

        if (getConfig().getBoolean("minors_setting.enable_shield")) {
            getServer().getPluginManager().registerEvents(new MessageListener(), this);
        }

        getServer().getPluginManager().registerEvents(new ServerListener(), this);

        getServer().getPluginManager().registerEvents(new ChoosePointListener(), this);
        getServer().getPluginManager().registerEvents(new ResidenceProtectListener(), this);
    }

    private void loadAllConfig() {
        this.saveDefaultConfig();

        languageConfig = new WConfig(getConfig().getString("wessential_setting.language_file"));
        homeLocationConfig = new WConfig("home.yml");
        backLocationConfig = new WConfig("back.yml");
        passwordConfig = new WConfig("password.yml");
        chestProtectConfig = new WConfig("chest.yml");
        minorsPlayerConfig = new WConfig("minors.yml");

        // 有部分配置文件在ServerListener中加载
    }

//    public void reloadAllConfig() {
//        getLogger().info("将重新载入配置文件，但某些设置需要重启才会生效！");
//        this.reloadConfig();
//        for (WConfig wConfig : WConfig.configList) {
//            wConfig.reloadConfig();
//        }
//    }

    private void registerCommand() {
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
        Objects.requireNonNull(this.getCommand("setminors")).setExecutor(new SetMinors());
        Objects.requireNonNull(this.getCommand("isminors")).setExecutor(new IsMinors());
        Objects.requireNonNull(this.getCommand("minorsList")).setExecutor(new MinorsList());
        Objects.requireNonNull(this.getCommand("removeminors")).setExecutor(new RemoveMinors());
        Objects.requireNonNull(this.getCommand("setres")).setExecutor(new SetRes());
        Objects.requireNonNull(this.getCommand("reslist")).setExecutor(new ResList());
        Objects.requireNonNull(this.getCommand("removeres")).setExecutor(new RemoveRes());
    }

    public static boolean isWLoginEnable() {
        return WEssentialMain.wEssentialMain.getConfig().getBoolean("login_setting.enable_login_system");
    }

    public static void logInfo(String string) {
        Bukkit.getServer().getLogger().info("[WEssential] " + string);
    }

    public static void logWarning(String string) {
        Bukkit.getServer().getLogger().warning("[WEssential] " + string);
    }
}
