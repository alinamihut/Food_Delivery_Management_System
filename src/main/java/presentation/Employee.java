package presentation;

import bussiness.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Employee implements Initializable {


    public Button btnBack;
    public TableView tbOrders;
    DeliveryService deliveryService = new DeliveryService();
    public ArrayList<Order> orders = new ArrayList();
    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url= new File("src/main/java/sample.fxml").toURI().toURL();
        Parent root= FXMLLoader.load(url);
        Scene sceneStart = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneStart);
        window.setTitle("Register");
        window.show();
    }
    public static void createTableOrder(ArrayList<Order> listOfObjects, TableView<Order> table){
        table.getItems().clear();
        table.getColumns().clear();
        TableColumn column1 = new TableColumn("Order ID");
        column1.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
        TableColumn column2 = new TableColumn("Client ID");
        column2.setCellValueFactory(new PropertyValueFactory<Order, Integer>("clientID"));
        TableColumn column3 = new TableColumn("Date");
        column3.setCellValueFactory(new PropertyValueFactory<Order, String>("OrderDateString"));
        //Adding data to the table
        ObservableList<Order> list = FXCollections.observableArrayList(listOfObjects);
        table.setItems(list);
        table.getColumns().addAll(column1,column2, column3);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orders = deliveryService.getArrayOfOrders();
        createTableOrder(orders, tbOrders);
    }
}
