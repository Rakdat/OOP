package ru.nsu.tokarev4;

public class PizUtil {
    private final static int ONE_MINUTE_IN_MILLIS = 1000/60;
    /**
     * Рассчитывает игровое время на основе прошедших миллисекунд от начала смены.
     *
     * @param deliveryTime время доставки (если 0, выводит текущее игровое время).
     * @return отформатированная строка со временем.
     */

    public static String getGameTime(int deliveryTime) {
        return (deliveryTime / ONE_MINUTE_IN_MILLIS + " мин");
    }

    public static String getGameTime(long startTime, long timeNow) {
        int hours = 8 + (int) (timeNow - startTime) / 1000;
        int minutes = (int) (timeNow - startTime) % 1000 / ONE_MINUTE_IN_MILLIS;
        if (minutes < 10) {
            return (hours % 24 + ":0" + minutes);
        }
        if(minutes == 60){
            hours++;
            return (hours % 24 + ":00");
        }
        return (hours % 24 + ":" + minutes);
    }

    private PizUtil() {
        throw new UnsupportedOperationException();
    }
}
