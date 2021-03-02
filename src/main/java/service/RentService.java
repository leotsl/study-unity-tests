package service;

import entity.Movie;
import entity.Rent;
import entity.User;
import exceptions.InventoryException;
import exceptions.MovieException;
import exceptions.UserException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static utils.DateUtils.getDateWithDifferenceOfDays;

public class RentService {
    public Rent rentMovies(User user, List<Movie> movies) throws UserException, MovieException, InventoryException {
        if (Objects.isNull(user)) {
            throw new UserException("User is null");
        }

        if (Objects.isNull(movies) || movies.isEmpty()) {
            throw new MovieException("Movies is null or empty");
        }

        for (Movie movie : movies) {
            if (Objects.isNull(movie.getInventory()) || movie.getInventory() == 0) {
                throw new InventoryException("Movie without stock");
            }
        }

        Rent rent = new Rent();
        rent.setMovies(movies);
        rent.setUser(user);
        rent.setDateRent(new Date());
        rent.setPrice(0d);
        movies.forEach(movie -> rent.setPrice(rent.getPrice() + movie.getRentPrice()));
        rent.setDateReturn(getDateWithDifferenceOfDays(1));
        return rent;
    }
}
