package data;

import bussiness.BaseProduct;
import bussiness.CompositeProduct;
import bussiness.MenuItem;
import bussiness.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Serializator {

public static void serializeBaseProducts(ArrayList<BaseProduct> baseProducts) throws IOException {
    FileOutputStream file = new FileOutputStream ("src\\main\\resources\\baseproducts.ser");
    ObjectOutputStream out = new ObjectOutputStream (file);
    out.writeObject(baseProducts);
    out.close();
    file.close();
}


public static ArrayList deserializeBaseProducts() throws IOException, ClassNotFoundException {

    ArrayList<CompositeProduct> baseProducts = new ArrayList<>();
    FileInputStream file = new FileInputStream ("src\\main\\resources\\baseproducts.ser");
    ObjectInputStream in = new ObjectInputStream (file);
    baseProducts = (ArrayList)in.readObject();
    in.close();
    file.close();
   // System.out.println( "name " + baseProduct.getName() + "price " + baseProduct.getPrice() + "rating" + baseProduct.getRating() + "calories" + baseProduct.getCalories() + "fats" + baseProduct.getFats() + "proteins"  + baseProduct.getProteins() + "sodium" +baseProduct.getSodium());
    return baseProducts;
}
    public static void serializeComposedProducts(ArrayList<CompositeProduct> compositeProducts) throws IOException {
        FileOutputStream file = new FileOutputStream ("src\\main\\resources\\compositeproducts.ser");
        ObjectOutputStream out = new ObjectOutputStream (file);
        out.writeObject(compositeProducts);
        out.close();
        file.close();
    }


    public static ArrayList<CompositeProduct> deserializeComposedProducts () throws IOException, ClassNotFoundException {

        ArrayList<CompositeProduct> composedProducts = new ArrayList<>();
        FileInputStream file = new FileInputStream ("src\\main\\resources\\compositeproducts.ser");
        ObjectInputStream in = new ObjectInputStream (file);
        composedProducts= (ArrayList)in.readObject();
        in.close();
        file.close();
        // System.out.println( "name " + baseProduct.getName() + "price " + baseProduct.getPrice() + "rating" + baseProduct.getRating() + "calories" + baseProduct.getCalories() + "fats" + baseProduct.getFats() + "proteins"  + baseProduct.getProteins() + "sodium" +baseProduct.getSodium());
        return composedProducts;
    }
    public static void serializeOrders(HashMap<Order, ArrayList<MenuItem>> orders) {
    try {
        FileOutputStream file = new FileOutputStream("src\\main\\resources\\orders.ser");
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(orders);
        out.close();
        file.close();
    }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public static HashMap<Order, ArrayList<MenuItem>> deserializeOrders()  {

        HashMap<Order, ArrayList<MenuItem>> orders = new HashMap<>();
        FileInputStream file = null;
            try {
            file = new FileInputStream("src\\main\\resources\\orders.ser");

             ObjectInputStream in = null;
            in = new ObjectInputStream(file);
            orders= (HashMap<Order, ArrayList<MenuItem>>) in.readObject();

            in.close();
            file.close();
        }catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
        // System.out.println( "name " + baseProduct.getName() + "price " + baseProduct.getPrice() + "rating" + baseProduct.getRating() + "calories" + baseProduct.getCalories() + "fats" + baseProduct.getFats() + "proteins"  + baseProduct.getProteins() + "sodium" +baseProduct.getSodium());
        return orders;
    }
}
