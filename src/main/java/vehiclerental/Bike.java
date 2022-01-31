package vehiclerental;

import java.time.LocalTime;

public class Bike implements Rentable {

    private String id;
    private static final int PRICE_PER_MIN = 15;
    private LocalTime rentingTime;

    public Bike(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) minutes * PRICE_PER_MIN;
    }

    @Override
    public LocalTime getRentingTime() {
        return rentingTime;
    }

    @Override
    public void rent(LocalTime time) {
        rentingTime = time;
    }

    @Override
    public void closeRent() {
        rentingTime = null;
    }
}
