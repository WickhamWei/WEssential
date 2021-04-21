package io.github.wickhamwei.wessential.wlogin;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import io.github.wickhamwei.wessential.wtools.WTime;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WLogin {
    public static Set<String> loginList = new HashSet<>();

    public static boolean isInLoginList(String playerName) {
        return loginList.contains(playerName);
    }

    public static void addInLoginList(String playerName) {
        loginList.add(playerName);
    }

    public static void removeFromLoginList(String playerName) {
        loginList.remove(playerName);
    }

    public static HashMap<String, Integer> loginTimesHashMap = new HashMap<>();
    public static HashMap<String, Long> lastTryTimeHashMap = new HashMap<>();

    public static void addLoginTimes(String playerName) {
        int loginTimes = 0;
        if (loginTimesHashMap.get(playerName) != null) {
            loginTimes = loginTimesHashMap.get(playerName);
        }
        loginTimes++;
        loginTimesHashMap.put(playerName, loginTimes);
        lastTryTimeHashMap.put(playerName, WTime.getTime());
        canLogin(playerName);
    }

    public static boolean canLogin(String playerName) {
        int loginTimes = 0;
        if (loginTimesHashMap.get(playerName) != null) {
            loginTimes = loginTimesHashMap.get(playerName);
        }
        if (loginTimes >= 3) {
            if (WTime.getTimeDifferenceMinutes(WTime.getTime(), lastTryTimeHashMap.get(playerName)) < 5) {
                WPlayer.getWPlayer(playerName).getBukkitPlayer().kickPlayer(WEssentialMain.languageConfig.getConfig().getString("message.login_fail"));
                return false;
            } else {
                cleanLoginTimes(playerName);
                return true;
            }
        }
        return true;
    }

    public static void cleanLoginTimes(String playerName) {
        loginTimesHashMap.remove(playerName);
        lastTryTimeHashMap.remove(playerName);
    }
}
