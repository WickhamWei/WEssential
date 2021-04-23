package io.github.wickhamwei.wessential.wtools;

import io.github.wickhamwei.wessential.WEssentialMain;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class WConfig {
    public static String[] DEFAULT_CONFIG_LIST = {"zh_cn.yml", "home.yml", "back.yml", "password.yml", "chest.yml", "minors.yml"};
    public static Set<WConfig> configList = new HashSet<>();

    public String fileName;
    public FileConfiguration configuration;
    public File configurationFile;

    public WConfig(String fileNameString) {
        fileName = fileNameString;
        createConfig(fileNameString);
    }

    private void createConfig(String fileNameString) {
        File file = new File(WEssentialMain.wEssentialMain.getDataFolder(), fileNameString);
        configurationFile = file;
        if (!file.exists()) {
            for (String s : DEFAULT_CONFIG_LIST) {
                // 是用户自己创建的还是插件自带的config文件
                if (s.equals(fileNameString)) {
                    WEssentialMain.wEssentialMain.getLogger().log(Level.WARNING, fileNameString + " 文件不存在，WEssential 将创建一个");
//                    file.getParentFile().mkdirs();
                    WEssentialMain.wEssentialMain.saveResource(fileNameString, false);
                    break;
                }
            }
        }
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            WEssentialMain.wEssentialMain.getLogger().log(Level.WARNING, fileNameString + " 文件读取失败");
        }
        configList.add(this);
        configuration = config;
    }

    public void saveConfig() {
        try {
            configuration.save(configurationFile);
        } catch (IOException e) {
            e.printStackTrace();
            WEssentialMain.wEssentialMain.getLogger().log(Level.WARNING, fileName + " 文件保存失败");
        }
    }

//    public void reloadConfig() {
//        YamlConfiguration config = new YamlConfiguration();
//        try {
//            config.load(configurationFile);
//        } catch (IOException | InvalidConfigurationException e) {
//            e.printStackTrace();
//            WEssentialMain.wEssentialMain.getLogger().log(Level.WARNING, fileName + " 文件读取失败");
//        }
//        WEssentialMain.wEssentialMain.getLogger().log(Level.WARNING, fileName + " 文件重载成功");
//        configuration = config;
//    }

    public FileConfiguration getConfig() {
        return configuration;
    }
}
