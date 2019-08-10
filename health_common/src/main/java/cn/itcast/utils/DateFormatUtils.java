package cn.itcast.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 日期格式化为字符串"yyyy-MM-dd"
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        String formatDate = dateFormat.format(date);
        return formatDate;
    }

    /**
     * 将String转化为日期
     * @param date
     * @return
     */
    public static Date parse2Date(String date){
        Date parseDate = null;
        try {
            parseDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDate;
    }

}
