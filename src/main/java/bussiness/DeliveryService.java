package bussiness;

import data.FileWriter;
import data.Serializator;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {
    private List<BaseProduct> menuItems;
    private List <BaseProduct> inputList;
    private List<CompositeProduct> compositeMenuItems;
    public Map<Order, ArrayList<MenuItem>> orders = new HashMap<Order, ArrayList<MenuItem>>();
    public static ArrayList<User> listOfUsers = new ArrayList<>();
    private FileWriter fileWriter = new FileWriter();
    public void parseProductsCSV() throws IOException {
       inputList = new ArrayList<>();

        try {

            File inputF = new File("src/main/java/products.csv");
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));


            inputList = br.lines().skip(1).map(mapToItem).distinct().collect(Collectors.toList());
            Serializator.serializeBaseProducts((ArrayList<BaseProduct>) inputList);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Function<String, BaseProduct> mapToItem = (line) -> {

        String[] p = line.split(",");

        BaseProduct product = new BaseProduct(
                Double.parseDouble(p[1]),
                Integer.parseInt(p[2]),
                Integer.parseInt(p[3]),
                Integer.parseInt(p[4]),
                Integer.parseInt(p[5])
        );

        product.setName(p[0]);
        product.setPrice(Integer.parseInt(p[6]));
        return product;
    };

    public List<BaseProduct> createMenu () throws IOException {
        parseProductsCSV();
        menuItems = new ArrayList<>();
        menuItems.addAll(inputList);
        return menuItems;
    }

    public static void addMenuItem(BaseProduct baseProduct, ArrayList<BaseProduct> menuItems) throws IOException, ClassNotFoundException {
        menuItems= (ArrayList<BaseProduct>) Serializator.deserializeBaseProducts();
        menuItems.add(baseProduct);
        Serializator.serializeBaseProducts(menuItems);
    }

    public static void modifyMenuItem(BaseProduct oldProduct, BaseProduct newProduct, ArrayList<BaseProduct> menuItems) throws IOException, ClassNotFoundException {
        menuItems= (ArrayList<BaseProduct>) Serializator.deserializeBaseProducts();
        for (BaseProduct bp: menuItems){
            if (bp.equals(oldProduct)){
                System.out.println("old product");
                System.out.println( "name " + bp.getName() + "price " + bp.getPrice() + "rating" + bp.getRating() + "calories" + bp.getCalories() + "fats" + bp.getFats() + "proteins"  + bp.getProteins() + "sodium" +bp.getSodium());
                bp.setName(newProduct.getName());
                bp.setPrice(newProduct.getPrice());
                bp.setRating(newProduct.getRating());
                bp.setCalories(newProduct.getCalories());
                bp.setProteins(newProduct.getProteins());
                bp.setFats(newProduct.getFats());
                bp.setSodium(newProduct.getSodium());
                System.out.println("new product");
                System.out.println( "name " + bp.getName() + "price " + bp.getPrice() + "rating" + bp.getRating() + "calories" + bp.getCalories() + "fats" + bp.getFats() + "proteins"  + bp.getProteins() + "sodium" +bp.getSodium());
                break;
            }
        }
        Serializator.serializeBaseProducts(menuItems);
    }
    public static void deleteMenuItem(BaseProduct baseProduct, ArrayList<BaseProduct> menuItems) throws IOException, ClassNotFoundException {
               menuItems.removeIf(b -> b.equals(baseProduct));
        Serializator.serializeBaseProducts(menuItems);
    }

    public static void addComposedMenuItem (CompositeProduct compositeProduct, ArrayList<CompositeProduct> compositeMenuItems) throws IOException, ClassNotFoundException {
        compositeMenuItems = Serializator.deserializeComposedProducts();
        compositeMenuItems.add(compositeProduct);
        Serializator.serializeComposedProducts(compositeMenuItems);

    }

    public static BaseProduct searchMenuItemByName(String name) throws IOException, ClassNotFoundException {
        ArrayList<BaseProduct> menuItems = (ArrayList<BaseProduct>) Serializator.deserializeBaseProducts();
        for (BaseProduct bp : menuItems) {
            if (bp.getName().equals(name)) {
                return bp;
            }
        }
        return null;
    }

    public void makeNewOrder (Order order, ArrayList<MenuItem> orderedItems){
       orders = Serializator.deserializeOrders();
       order.setOrderID(orders.size()+1);
        orders.put(order, orderedItems);
        Serializator.serializeOrders((HashMap<Order, ArrayList<MenuItem>>) orders);
        try {
            fileWriter.writeBillToTextFile(order, orderedItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Order> getArrayOfOrders(){
       orders = Serializator.deserializeOrders();
       Set<Order> ordersSet = orders.keySet();
       List<Order> ordersList = new ArrayList<>(ordersSet);
        return (ArrayList<Order>) ordersList;
    }

    public static ArrayList<CompositeProduct>  getComposedMenuItem() throws IOException, ClassNotFoundException {
        ArrayList<CompositeProduct> compositeMenuItems = Serializator.deserializeComposedProducts();
        return compositeMenuItems;

    }
    public static ArrayList<BaseProduct>  getBasedMenuItem() throws IOException, ClassNotFoundException {
        ArrayList<BaseProduct> baseMenuItems = Serializator.deserializeBaseProducts();
        return baseMenuItems;

    }
    public static ArrayList<MenuItem> getAllMenuItems (){
        ArrayList<MenuItem> allMenuItems = new ArrayList<>();
        try {
            allMenuItems.addAll(getBasedMenuItem());
            allMenuItems.addAll(getComposedMenuItem());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allMenuItems;
    }

    public List<MenuItem> getOrderedProducts(){
        orders= Serializator.deserializeOrders();
        ArrayList<MenuItem> allMenuItems = getAllMenuItems();
        List<MenuItem> orderedProducts = new ArrayList<>();
        orders.forEach( (key, value) -> value.forEach( menuItem1 -> {
            allMenuItems.forEach( menuItem2 -> {
                if (menuItem1.equals(menuItem2))
                    orderedProducts.add(menuItem2);});}
            ));
        return orderedProducts;

    }

    public static int getIDOfUser(String name) {
       for (User u:listOfUsers){
           if (u.getUsername().equals(name)){
               return u.getUserID();
           }
        }
       return -1;
    }

    public int getOrderID(){
        if (orders.size() == 0){
            return 1;
        }
        orders = Serializator.deserializeOrders();
        return orders.size()+1;
    }

    public ArrayList<BaseProduct> searchBaseProduct (ArrayList<BaseProduct> menuItems, TextField tfName, TextField tfPrice, TextField tfRating,
                                       TextField tfCalories, TextField tfProtein, TextField tfSodium, TextField tfFat){

        List<BaseProduct> listOfFilteredItems;
        listOfFilteredItems = menuItems.stream().filter(!tfName.getText().isEmpty() ? p -> p.getName().contains(tfName.getText()): p->true).
                filter(!tfPrice.getText().isEmpty() ? p -> p.getPrice() ==  Integer.parseInt(tfPrice.getText()): p->true).
                filter(!tfRating.getText().isEmpty() ? p -> p.getRating() ==  Double.parseDouble(tfRating.getText()): p->true).
                filter(!tfCalories.getText().isEmpty() ? p -> p.getCalories() == Integer.parseInt(tfCalories.getText()) : p -> true).
                filter(!tfProtein.getText().isEmpty() ? p -> p.getProteins() == Integer.parseInt(tfProtein.getText()) : p -> true).
                filter(!tfSodium.getText().isEmpty() ? p -> p.getSodium() == Integer.parseInt(tfSodium.getText()) : p -> true).
                filter(!tfFat.getText().isEmpty() ? p -> p.getFats() == Integer.parseInt(tfFat.getText()) : p -> true).collect(Collectors.toList());
        return (ArrayList<BaseProduct>) listOfFilteredItems;
    }

    public ArrayList<CompositeProduct> searchCompositeProduct (ArrayList<CompositeProduct> menuItems, TextField tfName, TextField tfPrice){

        List<CompositeProduct> listOfFilteredItems;
        listOfFilteredItems = menuItems.stream().filter(!tfName.getText().isEmpty() ? p -> p.getName().contains(tfName.getText()): p->true).
                filter(!tfPrice.getText().isEmpty() ? p -> p.getPrice() ==  Integer.parseInt(tfPrice.getText()): p->true).collect(Collectors.toList());
        return (ArrayList<CompositeProduct>) listOfFilteredItems;
    }

    public void generateReport1 (TextField tfHourStart, TextField tfHourFinish){
        orders = Serializator.deserializeOrders();
        Map<Order, ArrayList<MenuItem>> filteredOrders = orders.entrySet().stream().filter( map -> map.getKey().getOrderDate().getHour() <= Integer.parseInt(tfHourFinish.getText())).filter(
                map -> map.getKey().getOrderDate().getHour() >= Integer.parseInt(tfHourStart.getText())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        try {
            fileWriter.writeReport1(tfHourStart, tfHourFinish, filteredOrders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateReport2 (TextField tfTimes){
        orders = Serializator.deserializeOrders();

        List<MenuItem> orderedProducts = getOrderedProducts();
        Map <MenuItem, AtomicInteger> productsCount = new HashMap<>();
        List<MenuItem> filteredProducts = new ArrayList<>();
        AtomicInteger count= new AtomicInteger();
        orders.forEach((key, value) -> value.forEach(

                products1 ->{

                orderedProducts.forEach( p -> {if (products1.equals(p))
                count.getAndIncrement(); });
                        productsCount.put(products1,count);
                    }
                    )
                );
        for (Map.Entry <MenuItem, AtomicInteger> entry: productsCount.entrySet()) {
        if (entry.getValue().intValue() > Integer.parseInt(tfTimes.getText())) {
            filteredProducts.add(entry.getKey());
        }
    }
        try {
            fileWriter.writeReport2(tfTimes, filteredProducts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateReport3 (TextField tfValue, TextField tfTimes){
        orders = Serializator.deserializeOrders();

        List<Order> filteredOrders = new ArrayList<>();
        final int[] orderValue = {0};
        orders.forEach((key, value) -> {
                     orderValue[0] = 0;
                     value.forEach(p-> {
                         orderValue[0] = orderValue[0] + p.getPrice();
                     });
                     if (orderValue[0] > Integer.parseInt(tfValue.getText())){
                         filteredOrders.add(key);
                     };

                     });
        try {
            fileWriter.writeReport3(tfValue, tfTimes,filteredOrders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getNrOfTimesProductWasOrdered (MenuItem p , Map<Order, ArrayList<MenuItem>> ordersOnSpecificDay ){
        final int[] nrTimes =new int[1];
        ordersOnSpecificDay.forEach((key, value) -> value.forEach(
                products1 ->{ if(products1.getName().equals(p.getName())){
                    nrTimes[0]++;
                };
                }
                )
        );
        return nrTimes[0];
    }
    public void generateReport4 (TextField tfDay , TextField tfMonth){
        orders = Serializator.deserializeOrders();

        List<MenuItem> orderedProducts = getOrderedProducts();
        Map <MenuItem, Integer> productsCount = new HashMap<>();
        List<MenuItem> filteredProducts = new ArrayList<>();
        AtomicInteger count= new AtomicInteger();
        Map<Order, ArrayList<MenuItem>> ordersOnSpecificDay = orders.entrySet().stream().filter( p -> p.getKey().getOrderDate().getDay() == Integer.parseInt(tfDay.getText())).
                filter( p-> p.getKey().getOrderDate().getMonth() == Integer.parseInt(tfMonth.getText())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        ordersOnSpecificDay.forEach((key, value) -> value.forEach(

                products1 ->{
                    productsCount.put(products1,getNrOfTimesProductWasOrdered(products1,ordersOnSpecificDay));
                }
                )
        );
        try {
            fileWriter.writeReport4(tfDay,tfMonth,productsCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
