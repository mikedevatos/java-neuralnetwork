package de.abramov.train.data;

public class RealEstate {

    private double price;
    private double rent;

    public RealEstate(double price, double rent) {
        this.price = price;
        this.rent = rent;
    }

    public boolean isWorthwhile() {
        return price < ((rent * 12) * 16);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }


    @Override
    public String toString() {
        return "RealEstate{" +
                "price=" + price +
                ", rent=" + rent +
                ", isWorthwhile=" + isWorthwhile() +
                '}';
    }
}
