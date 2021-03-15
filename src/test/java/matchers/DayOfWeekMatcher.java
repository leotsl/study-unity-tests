package matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayOfWeekMatcher extends TypeSafeMatcher<Date> {

    private Integer day;

    public DayOfWeekMatcher(Integer day) {
        this.day = day;
    }

    @Override
    public void describeTo(Description description) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, this.day);
        String dateDescription = calendar.getDisplayName(
                Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
        description.appendText(dateDescription);
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return DateUtils.isDayOfWeek(date, this.day);
    }
}
