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

import static matchers.MatchersOwn.*;
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
    public void rentMovie() throws MovieException, InventoryException, UserException {
        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d)));
        User user = new User("User 1");

        Rent rent = service.rentMovies(user,movies);

        error.checkThat(rent.getPrice(), is(equalTo(10d)));
        error.checkThat(rent.getDateRent(), isToday());
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
    @Ignore
    public void notReturnMoviesOnSaturday() throws MovieException, InventoryException, UserException {
        Assume.assumeTrue(DateUtils.isDayOfWeek(new Date(), Calendar.SATURDAY));

        List<Movie> movies = new ArrayList<>(Arrays
                .asList(new Movie("Movie 1", 3, 10d),
                        new Movie("Movie 2", 2, 8d)));
        User user = new User("User 1");

        Rent rent = service.rentMovies(user, movies);

        error.checkThat(rent.getDateReturn(), fallsIn(Calendar.MONDAY));
        error.checkThat(rent.getDateReturn(), fallsInMonday());

    }
}
