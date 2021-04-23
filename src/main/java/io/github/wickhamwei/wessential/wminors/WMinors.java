package io.github.wickhamwei.wessential.wminors;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import io.github.wickhamwei.wessential.wtools.WTime;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class WMinors {
    public static void setMinors(String playerName) {
        WEssentialMain.minorsPlayerConfig.getConfig().set(playerName, WTime.getTime());
        WEssentialMain.minorsPlayerConfig.saveConfig();
    }

    public static Set<String> getMinors() {
        Set<String> playerNames = new HashSet<>();
        ConfigurationSection nameConfigSection = WEssentialMain.minorsPlayerConfig.getConfig().getConfigurationSection("");
        if (nameConfigSection != null) {
            playerNames = nameConfigSection.getKeys(false);
        }
        return playerNames;
    }

    public static boolean isMinors(String playerName) {
        return getMinors().contains(playerName);
    }

    public static void removeMinors(String playerName) {
        WEssentialMain.minorsPlayerConfig.getConfig().set(playerName, null);
        WEssentialMain.minorsPlayerConfig.saveConfig();
    }

    public static void checkMinors() {
        Set<String> minorsPlayerNames = getMinors();
        GregorianCalendar calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 22 || hour < 8) {
            for (String minorsPlayerName : minorsPlayerNames) {
                if (WPlayer.isOnline(minorsPlayerName)) {
                    WPlayer.getWPlayer(minorsPlayerName).getBukkitPlayer().kickPlayer(WEssentialMain.languageConfig.getConfig().getString("message.w_minors_kick"));
                }
            }
        }
    }
}
