package service;

import entity.Movie;
import entity.Rent;
import entity.User;
import exceptions.InventoryException;
import exceptions.MovieException;
import exceptions.UserException;
import utils.DateUtils;

import java.util.Calendar;
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

        double percentageDiscount = applyDiscount(movies);

        Rent rent = new Rent();
        rent.setMovies(movies);
        rent.setUser(user);
        rent.setDateRent(new Date());
        rent.setPrice(0d);
        movies.forEach(movie -> rent.setPrice(rent.getPrice() + movie.getRentPrice()));
        rent.setPrice(rent.getPrice() + (rent.getPrice() * percentageDiscount));
        rent.setDateReturn(getDateWithDifferenceOfDays(1));

        if (DateUtils.isDayOfWeek(rent.getDateReturn(), Calendar.MONDAY)) {
            rent.setDateReturn(getDateWithDifferenceOfDays(2));
        }

        return rent;
    }

    public double applyDiscount(List<Movie> movies) {
        double percentage = 0;
        if (movies.size() >= 3) {
            int countMovies = movies.size() - 2;
            while (countMovies > 0 && percentage < 1.0) {
                percentage += 0.25;
                countMovies--;
            }
        }
        return percentage;
    }
}
