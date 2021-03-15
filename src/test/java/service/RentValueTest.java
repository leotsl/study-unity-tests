package service;

import entity.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class RentValueTest {

    private RentService service;

    @Before
    public void before() {
        service = new RentService();
    }

    private static Movie movie1 = new Movie("Movie 1", 3, 10d);
    private static Movie movie2 = new Movie("Movie 2", 2, 8d);
    private static Movie movie3 = new Movie("Movie 3", 5, 7d);
    private static Movie movie4 = new Movie("Movie 4", 2, 8d);
    private static Movie movie5 = new Movie("Movie 5", 1, 15d);
    private static Movie movie6 = new Movie("Movie 6", 5, 5d);
    private static Movie movie7 = new Movie("Movie 7", 5, 5d);

    @Parameterized.Parameter
    public List<Movie> movies;
    @Parameterized.Parameter(value = 1)
    public Double valueRent;
    @Parameterized.Parameter(value = 2)
    public String description;

    @Parameterized.Parameters(name = "Test = {2}")
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(movie1, movie2, movie3), 0.25, "3 Movies - 25%"},
                {Arrays.asList(movie1, movie2, movie3, movie4), 0.50, "4 Movies - 50%"},
                {Arrays.asList(movie1, movie2, movie3, movie4, movie5), 0.75, "5 Movies - 75%"},
                {Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6), 1.00, "6 Movies - 100%"},
                {Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7), 1.00, "6+ Movies - 100%"}
        });
    }

    @Test
    public void getProgressive() {
        double finalPrice = service.applyDiscount(movies);
        Assert.assertEquals(finalPrice, 0.25, 1.00);
    }

}
