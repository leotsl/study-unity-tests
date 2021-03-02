package service;

import entity.Movie;
import entity.User;
import exceptions.InventoryException;
import exceptions.MovieException;
import exceptions.UserException;
import exceptions.VideoStoreException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

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
        List<Movie> movies = new ArrayList<>();
        Movie movie1 = new Movie("Movie 1", 3, 10d);
        Movie movie2 = new Movie("Movie 2", 2, 8d);
        movies.add(movie1);
        movies.add(movie2);

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
        List<Movie> movies = new ArrayList<>();
        Movie movie1 = new Movie("Movie 1", 3, 10d);
        Movie movie2 = new Movie("Movie 2", 0, 8d);
        movies.add(movie1);
        movies.add(movie2);

        User user = new User("User 1");
        service.rentMovies(user, movies);
    }

    @Test
    public void rentIsNotNull() throws VideoStoreException {
        List<Movie> movies = new ArrayList<>();
        Movie movie1 = new Movie("Movie 1", 3, 10d);
        Movie movie2 = new Movie("Movie 2", 2, 8d);
        movies.add(movie1);
        movies.add(movie2);

        User user = new User("User 1");

        error.checkThat(service.rentMovies(user, movies), notNullValue());
    }
}
