package vehiclerental;

import movietheatres.Movie;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RentService {

    private List<Rentable> rentables = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Map<Rentable, User> actualRenting = new TreeMap<>();
    private static final long MAXIMUM_RENT_TIME_IN_MINUTES = 180;

    public List<Rentable> getRentables() {
        return rentables;
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<Rentable, User> getActualRenting() {
        return actualRenting;
    }

    public void registerUser(User user) {
        for (User actual : users) {
            if (user.getUserName().equals(actual.getUserName())) {
                throw new UserNameIsAlreadyTakenException("Username is taken!");
            }
        }
        users.add(user);
    }

    public void addRentable(Rentable rentable) {
        if (!rentables.contains(rentable)) {
            rentables.add(rentable);
        }
    }

    public void rent(User user, Rentable rentable, LocalTime time) {
        if (isRentingValid(user, rentable, time)) {
            rentable.rent(time);
            actualRenting.put(rentable, user);
        }

    }

    private boolean isRentingValid(User user, Rentable rentable, LocalTime time) {

        boolean result = true;
        if (rentable.getRentingTime() != null) {
            result = false;
            throw new IllegalStateException("Already renting");
        }
        if (user.getBalance() < rentable.calculateSumPrice(MAXIMUM_RENT_TIME_IN_MINUTES)) {
            result = false;
            throw new IllegalStateException("Not enough user balance");
        }
        return result;
    }

    public void closeRent(Rentable rentable, int minutes) {
        if (actualRenting.containsKey(rentable)) {
            actualRenting.get(rentable).minusBalance(rentable.calculateSumPrice(minutes));
            actualRenting.remove(rentable);
            rentable.closeRent();
        }
    }
}
