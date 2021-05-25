package bussiness;

import java.util.Objects;

public class BaseProduct extends MenuItem{
    private double rating;
    private int calories;
    private int proteins;
    private int fats;
    private int sodium;

    public BaseProduct() {
    }

    public BaseProduct(double rating, int calories, int proteins, int fats, int sodium) {
        this.rating = rating;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.sodium = sodium;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    @Override
    public int computePrice() {

        return getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseProduct)) {
            return false;
        }
        BaseProduct other = (BaseProduct) o;
        return calories == other.calories && fats == other.fats && proteins == other.proteins && sodium == other.sodium && rating == other.rating ;
    }


}
