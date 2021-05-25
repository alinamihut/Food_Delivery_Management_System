package bussiness;

import java.io.Serializable;
import java.util.Objects;

public abstract class MenuItem implements Serializable {
    private String name;
    private int price;

    public abstract int computePrice();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseProduct)) {
            return false;
        }
        BaseProduct other = (BaseProduct) o;
        return name.equals(other.getName()) && price == other.getPrice();
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return " name: " + name +
                " price: " + price +
                "\n";
    }
}
