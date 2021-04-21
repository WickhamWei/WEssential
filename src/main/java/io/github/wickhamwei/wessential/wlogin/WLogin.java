package io.github.wickhamwei.wessential.wlogin;

import io.github.wickhamwei.wessential.wtools.WPlayer;

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
}
