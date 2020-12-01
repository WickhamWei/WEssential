package io.github.wickhamwei.wessential.wteleport;

public class WTeleportPlayer {
    public String playerName;
    public int teleportTaskID;
    public int countDownTaskID;

    public WTeleportPlayer(String playerName, int teleportTaskID, int countDownTaskID) {
        this.playerName = playerName;
        this.teleportTaskID = teleportTaskID;
        this.countDownTaskID = countDownTaskID;
    }
}
