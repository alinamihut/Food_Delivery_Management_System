package bussiness;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IDeliveryServiceProcessing {
    void parseProductsCSV() throws IOException;

    List<BaseProduct> createMenu() throws IOException;

    static void addMenuItem(BaseProduct baseProduct, ArrayList<BaseProduct> menuItems) {

    }

    static void modifyMenuItem(BaseProduct oldProduct, BaseProduct newProduct, ArrayList<BaseProduct> menuItems) {

    }

    static void deleteMenuItem(BaseProduct baseProduct, ArrayList<BaseProduct> menuItems)  {

    }
    static BaseProduct searchMenuItemByName(String name){

        return null;
    }

    void makeNewOrder (Order order, ArrayList<MenuItem> orderedItems);
    ArrayList<Order> getArrayOfOrders();

    static ArrayList<CompositeProduct> getComposedMenuItem() {
        return null;
    }
    static ArrayList<BaseProduct>  getBasedMenuItem() {
        return null;
    }
    static ArrayList<MenuItem> getAllMenuItems (){
        return null;
    }
    List<MenuItem> getOrderedProducts();

    static int getIDOfUser(String name) {
        return 0;
    }
    int getOrderID();

    ArrayList<BaseProduct> searchBaseProduct (ArrayList<BaseProduct> menuItems, TextField tfName, TextField tfPrice, TextField tfRating,
                                                     TextField tfCalories, TextField tfProtein, TextField tfSodium, TextField tfFat);

    ArrayList<CompositeProduct> searchCompositeProduct (ArrayList<bussiness.CompositeProduct> menuItems, TextField tfName, TextField tfPrice);

    void generateReport1(TextField tfHourStart, TextField tfHourFinish);

    void generateReport2 (TextField tfTimes);

    void generateReport3 (TextField tfValue, TextField tfTimes);

    int getNrOfTimesProductWasOrdered (MenuItem p , Map<Order, ArrayList<MenuItem>> ordersOnSpecificDay );

    void generateReport4 (TextField tfDay , TextField tfMonth);
}
