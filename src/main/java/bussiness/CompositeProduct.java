package bussiness;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {

    private ArrayList<MenuItem> compositeProduct;

    public CompositeProduct(ArrayList<MenuItem> compositeProduct) {
        this.compositeProduct = compositeProduct;
    }

    public ArrayList<MenuItem> getCompositeProduct() {
        return compositeProduct;
    }

    @Override
    public int computePrice() {
        int price = 0;
        for (MenuItem bp: compositeProduct){
            price = price + bp.getPrice();
        }
        return price;
    }
}
