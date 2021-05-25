package presentation;

import bussiness.BaseProduct;
import bussiness.CompositeProduct;
import bussiness.DeliveryService;
import bussiness.MenuItem;
import data.Serializator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CompositeProductController {
    public ListView listComposedProducts;
    public ComboBox cb1;
    public ComboBox cb2;
    public ComboBox cb3;
    public ComboBox cb4;
    public Button btnGoBack;
    public Button btnCreateProduct;
    public TextField tfName;
    public TableView tbCompositeProducts;
    ArrayList<CompositeProduct> compositeMenuItems = new ArrayList<>();
    DeliveryService deliveryService = new DeliveryService();
    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/admin.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene sceneProducts = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneProducts);
        window.setTitle("Composite Products");
        window.show();

    }

    public void setValueForComboBox1 () throws IOException{
      //  deliveryService.parseProductsCSV();
            ArrayList<BaseProduct> menuItems = (ArrayList<BaseProduct>) deliveryService.createMenu();
            cb1.getItems().clear();
            for (BaseProduct bp:menuItems){
                cb1.getItems().add(bp.getName());
            }
        }
    public void setValueForComboBox2 () throws IOException{
        ArrayList<BaseProduct> menuItems = (ArrayList<BaseProduct>) deliveryService.createMenu();
        cb2.getItems().clear();
        for (BaseProduct bp:menuItems){
            cb2.getItems().add(bp.getName());
        }
    }
    public void setValueForComboBox3 () throws IOException {
        ArrayList<BaseProduct> menuItems = (ArrayList<BaseProduct>) deliveryService.createMenu();
        cb3.getItems().clear();
        for (BaseProduct bp:menuItems){
            cb3.getItems().add(bp.getName());
        }
    }
    public void setValueForComboBox4 () throws IOException{
        ArrayList<BaseProduct> menuItems = (ArrayList<BaseProduct>) deliveryService.createMenu();
        cb4.getItems().clear();
        for (BaseProduct bp:menuItems){
            cb4.getItems().add(bp.getName());
        }
    }

    public void addComposedProduct(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        BaseProduct baseProduct1 = DeliveryService.searchMenuItemByName((String) cb1.getValue());
        BaseProduct baseProduct2 = DeliveryService.searchMenuItemByName((String) cb2.getValue());
        BaseProduct baseProduct3 = DeliveryService.searchMenuItemByName((String) cb3.getValue());
        BaseProduct baseProduct4 = DeliveryService.searchMenuItemByName((String) cb4.getValue());
        ArrayList<MenuItem> compositeProduct = new ArrayList<>();
        compositeProduct.add(baseProduct1);
        compositeProduct.add(baseProduct2);
        compositeProduct.add(baseProduct3);
        compositeProduct.add(baseProduct4);
        CompositeProduct newCompositeProduct = new CompositeProduct(compositeProduct);
        newCompositeProduct.setName(tfName.getText());
        int price = newCompositeProduct.computePrice();
        newCompositeProduct.setPrice(price);
        DeliveryService.addComposedMenuItem(newCompositeProduct,compositeMenuItems);
        compositeMenuItems = Serializator.deserializeComposedProducts();
        showConformationAlert("New product was added successfully!");
        createTable(compositeMenuItems,tbCompositeProducts);

    }

    public static void showConformationAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(s);
        alert.show();
    }

    public static void createTable(ArrayList<CompositeProduct> listOfObjects, TableView<CompositeProduct> table){
        table.getItems().clear();
        table.getColumns().clear();
        TableColumn column1 = new TableColumn("Name");
        column1.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("name"));
        TableColumn column2 = new TableColumn("Price");
        column2.setCellValueFactory(new PropertyValueFactory<BaseProduct, String>("price"));

        //Adding data to the table
        ObservableList<CompositeProduct> list = FXCollections.observableArrayList(listOfObjects);
        table.setItems(list);
        table.getColumns().addAll(column1,column2);

    }

}


