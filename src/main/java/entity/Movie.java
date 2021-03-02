package entity;

public class Movie {
    private String name;
    private Integer inventory;
    private Double rentPrice;

    public Movie() {
    }

    public Movie(String name, Integer inventory, Double rentPrice) {
        this.name = name;
        this.inventory = inventory;
        this.rentPrice = rentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Double rentPrice) {
        this.rentPrice = rentPrice;
    }
}
