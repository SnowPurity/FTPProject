package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**

     * 将long时间类型转换成String

     * @param time

     * @return

     */

    public static String longToString_Time(long time) {



        SimpleDateFormat dafo = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");



        return dafo.format(new Date(time));

    }

}
