package matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import utils.DateUtils;

import java.util.Date;

public class TodayMatcher extends TypeSafeMatcher<Date> {
    @Override
    protected boolean matchesSafely(Date date) {
        return DateUtils.isSameDate(new Date(), date);
    }

    @Override
    public void describeTo(Description description) {

    }
}
