package matchers;

import java.util.Calendar;

public class MatchersOwn {

    public static DayOfWeekMatcher fallsIn(Integer day){
        return new DayOfWeekMatcher(day);
    }

    public static DayOfWeekMatcher fallsInMonday(){
        return new DayOfWeekMatcher(Calendar.MONDAY);
    }

    public static TodayMatcher isToday(){
        return new TodayMatcher();
    }
}
