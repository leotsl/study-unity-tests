package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import service.RentServiceTest;
import service.RentValueTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RentServiceTest.class,
        RentValueTest.class
})
public class RentSuite {
}
