package presentation;

import bussiness.*;
import data.Serializator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Client implements Initializable {
    public TableView tBaseProducts;
    public TableView tbCompositeProducts;
    public TextField tfName;
    public TextField tfPrice;
    public TextField tfRating;
    public TextField tfCalories;
    public TextField tfProtein;
    public TextField tfFat;
    public TextField tfSodium;
    public TableView tbOrders;
    public Button btnOrder;
    public ArrayList<CompositeProduct> compositeMenuItems = new ArrayList<>();
    public ArrayList<BaseProduct> baseMenuItems = new ArrayList<>();
    public ArrayList<MenuItem> itemsFromOrder = new ArrayList<>();
    public Button btnBack;
    private User user;
    DeliveryService deliveryService = new DeliveryService();
    public void createTables() throws IOException, ClassNotFoundException {
        createMenuItems();
        createTableBaseProducts(baseMenuItems,tBaseProducts);
        createTableComposite(compositeMenuItems,tbCompositeProducts);

    }

    public void createMenuItems() throws IOException, ClassNotFoundException {
        baseMenuItems = Serializator.deserializeBaseProducts();
        compositeMenuItems = Serializator.deserializeComposedProducts();
    }
    public static void createTableBaseProducts(ArrayList<BaseProduct> listOfObjects, TableView<BaseProduct> table){
        table.getItems().clear();
        table.getColumns().clear();
        TableColumn column1 = new TableColumn("Name");
        column1.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("name"));
        TableColumn column2 = new TableColumn("Rating");
        column2.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("rating"));
        TableColumn column3 = new TableColumn("Calories");
        column3.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("calories"));
        TableColumn column4 = new TableColumn("Protein");
        column4.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("proteins"));
        TableColumn column5 = new TableColumn("Fat");
        column5.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("fats"));
        TableColumn column6 = new TableColumn("Sodium");
        column6.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("sodium"));
        TableColumn column7 = new TableColumn("Price");
        column7.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("price"));
        //Adding data to the table
        ObservableList<BaseProduct> list = FXCollections.observableArrayList(listOfObjects);
        table.setItems(list);
        table.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7);

    }
    public static void createTableComposite(ArrayList<CompositeProduct> listOfObjects, TableView<CompositeProduct> table){
        table.getItems().clear();
        table.getColumns().clear();
        TableColumn column1 = new TableColumn("Name");
        column1.setCellValueFactory(new PropertyValueFactory<CompositeProduct, String>("name"));
        TableColumn column2 = new TableColumn("Price");
        column2.setCellValueFactory(new PropertyValueFactory<CompositeProduct, String>("price"));
        TableColumn column3 = new TableColumn("Details");
        column3.setCellValueFactory(new PropertyValueFactory<CompositeProduct, String>("price"));
        //Adding data to the table
        ObservableList<CompositeProduct> list = FXCollections.observableArrayList(listOfObjects);
        table.setItems(list);
        table.getColumns().addAll(column1,column2);

    }
    public static void createTableOrder(ArrayList<MenuItem> listOfObjects, TableView<MenuItem> table){
        table.getItems().clear();
        table.getColumns().clear();
        TableColumn column1 = new TableColumn("Name");
        column1.setCellValueFactory(new PropertyValueFactory<CompositeProduct, String>("name"));
        TableColumn column2 = new TableColumn("Price");
        column2.setCellValueFactory(new PropertyValueFactory<CompositeProduct, String>("price"));
        //Adding data to the table
        ObservableList<MenuItem> list = FXCollections.observableArrayList(listOfObjects);
        table.setItems(list);
        table.getColumns().addAll(column1,column2);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            createTables();
            user = Controller.getUser();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleClickTableView(javafx.scene.input.MouseEvent mouseEvent) {
        MenuItem menuItem;
        menuItem= (MenuItem) tBaseProducts.getSelectionModel().getSelectedItem();
        if (menuItem!=null) {
            itemsFromOrder.add(menuItem);
            createTableOrder(itemsFromOrder, tbOrders);
        }
    }

    @FXML
    public void handleClickTableViewComposite(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            MenuItem menuItem;
            menuItem = (MenuItem) tbCompositeProducts.getSelectionModel().getSelectedItem();
            if (menuItem!=null) {
                itemsFromOrder.add(menuItem);
                createTableOrder(itemsFromOrder, tbOrders);
            }
        }
        catch (NullPointerException e){
            tbOrders.getItems().clear();
        }
    }

    
    public void makeOrder (ActionEvent actionEvent){
            int clientID = DeliveryService.getIDOfUser(user.getUsername());
            int orderID = deliveryService.getOrderID();
            int hour = LocalTime.now().getHour();
            int minutes = LocalTime.now().getMinute();
            int seconds = LocalTime.now().getSecond();
            int day = LocalDate.now().getDayOfMonth();
            int month = LocalDate.now().getMonth().getValue();
            int year = LocalDate.now().getYear();
            bussiness.Date date = new Date( day, month, year, hour, minutes, seconds);
            Order newOrder = new Order(orderID, 2, date);
            deliveryService.makeNewOrder(newOrder, itemsFromOrder);
            Administrator.showConformationAlert("New order is now in process by the employees!");

    }

    public void search(ActionEvent actionEvent){
        if (!tfName.getText().isEmpty() || !tfPrice.getText().isEmpty()){
            ArrayList<BaseProduct> filteredBaseProducts = deliveryService.searchBaseProduct(baseMenuItems, tfName, tfPrice, tfRating, tfCalories, tfProtein, tfSodium, tfFat);
            createTableBaseProducts(filteredBaseProducts, tBaseProducts);
            ArrayList<CompositeProduct> filteredCompositeProducts = deliveryService.searchCompositeProduct(compositeMenuItems, tfName, tfPrice);
            createTableComposite(filteredCompositeProducts,tbCompositeProducts);
        }
        else{
            ArrayList<BaseProduct> filteredBaseProducts = deliveryService.searchBaseProduct(baseMenuItems, tfName, tfPrice, tfRating, tfCalories, tfProtein, tfSodium, tfFat);
            createTableBaseProducts(filteredBaseProducts, tBaseProducts);
        }
        tfName.clear();
        tfPrice.clear();
        tfRating.clear();
        tfCalories.clear();
        tfProtein.clear();
        tfSodium.clear();
        tfFat.clear();
    }

    public void goBack (ActionEvent actionEvent) throws IOException {
            URL url= new File("src/main/java/sample.fxml").toURI().toURL();
            Parent root= FXMLLoader.load(url);
            Scene sceneStart = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(sceneStart);
            window.setTitle("Register");
            window.show();

        }

}
