package gym.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    public static Date date(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,0,0,0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
