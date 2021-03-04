package service;

import entity.Movie;
import entity.Rent;
import entity.User;
import exceptions.InventoryException;
import exceptions.MovieException;
import exceptions.UserException;
import exceptions.VideoStoreException;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import utils.DateUtils;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;

public class RentServiceTest {

    private RentService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        service = new RentService();
    }

    @Test(expected = UserException.class)
    public void rentUserNull() throws VideoStoreException {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d)));
        service.rentMovies(null, movies);
    }

    @Test(expected = MovieException.class)
    public void rentMoviesIsEmpty() throws VideoStoreException {
        List<Movie> movies = new ArrayList<>();
        User user = new User("User 1");
        service.rentMovies(user, movies);
    }

    @Test(expected = InventoryException.class)
    public void rentInventoryIsEmpty() throws VideoStoreException {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 0, 8d)));
        User user = new User("User 1");
        service.rentMovies(user, movies);
    }

    @Test
    public void rentIsNotNull() throws VideoStoreException {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d)));
        User user = new User("User 1");

        error.checkThat(service.rentMovies(user, movies), notNullValue());
    }


    @Test
    public void rentApplyDiscount() throws VideoStoreException {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d),
                        new Movie("Movie 3", 5, 7d),
                        new Movie("Movie 4", 2, 8d),
                        new Movie("Movie 5", 5, 5d)));
        User user = new User("User 1");

        Rent rent = service.rentMovies(user, movies);

        error.checkThat(rent.getPrice(), is(66.5));
    }

    @Test
    public void getProgressiveDiscount25() {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d),
                        new Movie("Movie 3", 5, 7d)));

        double finalPrice = service.applyDiscount(movies);

        error.checkThat(finalPrice, equalTo(0.25));
    }

    @Test
    public void getProgressiveDiscount50() {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d),
                        new Movie("Movie 3", 5, 7d),
                        new Movie("Movie 4", 2, 8d)));

        double finalPrice = service.applyDiscount(movies);

        error.checkThat(finalPrice, equalTo(0.50));
    }

    @Test
    public void getProgressiveDiscount75() {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d),
                        new Movie("Movie 3", 5, 7d),
                        new Movie("Movie 4", 2, 8d),
                        new Movie("Movie 5", 1, 15d)));

        double finalPrice = service.applyDiscount(movies);

        error.checkThat(finalPrice, equalTo(0.75));
    }

    @Test
    public void getProgressiveDiscount100() {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d),
                        new Movie("Movie 3", 5, 7d),
                        new Movie("Movie 4", 2, 8d),
                        new Movie("Movie 5", 1, 15d),
                        new Movie("Movie 6", 5, 5d)));

        double finalPrice = service.applyDiscount(movies);

        error.checkThat(finalPrice, equalTo(1.0));
    }

    @Test
    public void getProgressiveDiscountMoreMovies() {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d),
                        new Movie("Movie 3", 5, 7d),
                        new Movie("Movie 4", 2, 8d),
                        new Movie("Movie 5", 1, 15d),
                        new Movie("Movie 6", 5, 5d),
                        new Movie("Movie 7", 5, 5d)));

        double finalPrice = service.applyDiscount(movies);

        error.checkThat(finalPrice, equalTo(1.0));
    }

    @Test
    @Ignore
    public void notReturnMoviesOnSaturday() throws MovieException, InventoryException, UserException {
        Assume.assumeTrue(DateUtils.isDayOfWeek(new Date(), Calendar.SATURDAY));

        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d)));
        User user = new User("User 1");

        Rent rent = service.rentMovies(user, movies);
        boolean isSunday = DateUtils.isDayOfWeek(rent.getDateReturn(), Calendar.SUNDAY);

        Assert.assertTrue(isSunday);
    }
}
