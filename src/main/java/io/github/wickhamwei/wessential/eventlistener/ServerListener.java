package io.github.wickhamwei.wessential.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.WUpdateChecker.WUpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerListener implements Listener {
    @EventHandler
    public void serverLoadDone(ServerLoadEvent event) {
        String nowPluginVersion = "v" + WEssentialMain.wEssentialMain.getDescription().getVersion();
        WEssentialMain.logInfo("WEssential " + nowPluginVersion + " 加载完成");
        String nowPluginConfigVersion = WEssentialMain.wEssentialMain.getConfig().getString("config_version");
        String nowLanguageVersion = WEssentialMain.languageConfig.getConfig().getString("language_version");
        assert nowPluginConfigVersion != null;
        if (!nowPluginConfigVersion.equals(WEssentialMain.ConfigVersion)) {
            WEssentialMain.logWarning("配置文件版本 v" + nowPluginConfigVersion + " 不是最新的，请备份后删除，并重启服务器重新生成");
        }
        assert nowLanguageVersion != null;
        if (!nowLanguageVersion.equals(WEssentialMain.languageVersion)) {
            WEssentialMain.logWarning("语言文件版本 v" + nowLanguageVersion + " 不是最新的，请备份后删除，并重启服务器重新生成");
        }

        WUpdateChecker updateChecker = new WUpdateChecker(WEssentialMain.url);
        updateChecker.getUpdate();
        if (updateChecker.getNewestVersionString() != null && updateChecker.isNetworkNormal()) {
            if (updateChecker.getNewestVersionString().equals(nowPluginVersion)) {
                WEssentialMain.logInfo("WEssential 已是最新版本");
            } else {
                WEssentialMain.logInfo("WEssential 当前版本为 " + nowPluginVersion);
                WEssentialMain.logInfo("WEssential 最新版本为 " + updateChecker.getNewestVersionString() + " 发布于 "
                        + updateChecker.getNewestVersionPTimeString());
                WEssentialMain.logInfo("请及时更新");
            }
        } else {
            WEssentialMain.logWarning("WEssential 无法连接到 Github");
            WEssentialMain.logWarning("WEssential 无法检查更新");
        }
    }
}