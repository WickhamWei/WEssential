package io.github.wickhamwei.wessential.wtools;

import java.util.Date;

public class WTime {
    public static int getTimeDifferenceMinutes(long nowTime, long oldTime) {// 获得时间戳的分钟差
        int hours = (int) ((nowTime - oldTime) / (1000 * 60 * 60));
        return (int) (((nowTime - oldTime) / 1000 - hours * (60 * 60)) / 60 + hours * 60);
    }

    public static long getTime() {
        return new Date().getTime();
    }
}
