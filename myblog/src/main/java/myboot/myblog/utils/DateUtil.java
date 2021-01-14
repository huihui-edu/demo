package myboot.myblog.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date getNowDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = format.format(new Date());
        ParsePosition position = new ParsePosition(0);
        Date date = format.parse(strDate,position);
        return date;
    }

}
