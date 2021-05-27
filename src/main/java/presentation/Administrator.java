package presentation;

import bussiness.BaseProduct;
import bussiness.DeliveryService;
import bussiness.MenuItem;
import data.Serializator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Administrator {
    public Button btnImport;
    public Button btnAddProduct;
    public Button btnModifyProduct;
    public Button btnDeleteProduct;
    public TextField tfName;
    public TextField tfRating;
    public TextField tfCalories;
    public TextField tfProteins;
    public TextField tfFats;
    public TextField tfSodium;
    public TextField tfPrice;
    public Button btnGenerateReports;
    public Button btnCreateProduct;
    public TableView tableMenu;
    public Button btnBack;
    DeliveryService deliveryService = new DeliveryService();
    ArrayList<BaseProduct> menuItems;

    public void importProductsList (ActionEvent actionEvent) throws IOException, ClassNotFoundException {

       deliveryService.parseProductsCSV();
       showConformationAlert("The products list was imported successfully!");
             menuItems = (ArrayList<BaseProduct>) Serializator.deserializeBaseProducts();
             createTable(menuItems, tableMenu);
    }

    public void addProduct (ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String name = tfName.getText();
        double rating = Double.parseDouble(tfRating.getText());
        int calories = Integer.parseInt(tfCalories.getText());
        int fats = Integer.parseInt(tfFats.getText());
        int proteins = Integer.parseInt(tfProteins.getText());
        int price = Integer.parseInt(tfPrice.getText());
        int sodium = Integer.parseInt(tfSodium.getText());
        BaseProduct newBaseProduct = new BaseProduct(rating,calories,proteins,fats,sodium);
        newBaseProduct.setName(name);
        newBaseProduct.setPrice(price);
        DeliveryService.addMenuItem(newBaseProduct,menuItems);
        showConformationAlert("New Product was added successfully!");
        menuItems= (ArrayList<BaseProduct>) Serializator.deserializeBaseProducts();

        createTable(menuItems, tableMenu);
    }

    public void modifyProduct (ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        BaseProduct oldBaseProduct;
        oldBaseProduct= (BaseProduct) tableMenu.getSelectionModel().getSelectedItem();
        String name = tfName.getText();
        double rating = Double.parseDouble(tfRating.getText());
        int calories = Integer.parseInt(tfCalories.getText());
        int fats = Integer.parseInt(tfFats.getText());
        int proteins = Integer.parseInt(tfProteins.getText());
        int price = Integer.parseInt(tfPrice.getText());
        int sodium = Integer.parseInt(tfSodium.getText());
        BaseProduct newBaseProduct = new BaseProduct(rating,calories,proteins,fats,sodium);
        newBaseProduct.setName(name);
        newBaseProduct.setPrice(price);
        System.out.println("new product");
        System.out.println( "name " + newBaseProduct.getName() + "price " + newBaseProduct.getPrice() + "rating" + newBaseProduct.getRating() + "calories" + newBaseProduct.getCalories() + "fats" + newBaseProduct.getFats() + "proteins"  + newBaseProduct.getProteins() + "sodium" +newBaseProduct.getSodium());
        DeliveryService.modifyMenuItem(oldBaseProduct,newBaseProduct,menuItems);
        showConformationAlert(" Product was modified successfully!");
        menuItems= (ArrayList<BaseProduct>) Serializator.deserializeBaseProducts();
        createTable(menuItems, tableMenu);
    }

    public void deleteProduct (ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String name = tfName.getText();
        double rating = Double.parseDouble(tfRating.getText());
        int calories = Integer.parseInt(tfCalories.getText());
        int fats = Integer.parseInt(tfFats.getText());
        int proteins = Integer.parseInt(tfProteins.getText());
        int price = Integer.parseInt(tfPrice.getText());
        int sodium = Integer.parseInt(tfSodium.getText());
        BaseProduct newBaseProduct = new BaseProduct(rating,calories,proteins,fats,sodium);
        newBaseProduct.setName(name);
        newBaseProduct.setPrice(price);
        DeliveryService.deleteMenuItem(newBaseProduct,menuItems);
        showConformationAlert(" Product was deleted successfully!");
        menuItems= (ArrayList<BaseProduct>) Serializator.deserializeBaseProducts();

        createTable(menuItems, tableMenu);

    }

    public static void createTable(ArrayList<BaseProduct> listOfObjects, TableView<BaseProduct> table){
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
@FXML
    public void handleClickTableView(javafx.scene.input.MouseEvent mouseEvent) {
            BaseProduct baseProduct;
            baseProduct = (BaseProduct) tableMenu.getSelectionModel().getSelectedItem();
            if (baseProduct!= null) {
                tfName.setText(baseProduct.getName());
                tfCalories.setText(String.valueOf(baseProduct.getCalories()));
                tfFats.setText(String.valueOf(baseProduct.getFats()));
                tfProteins.setText(String.valueOf(baseProduct.getProteins()));
                tfSodium.setText(String.valueOf(baseProduct.getSodium()));
                tfPrice.setText(String.valueOf(baseProduct.getPrice()));
                tfRating.setText(String.valueOf(baseProduct.getRating()));
            }

    }
    public static void showConformationAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(s);
        alert.show();
    }

    public void goToCompositeProductScene (ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        URL url= new File("src/main/java/compositeproduct.fxml").toURI().toURL();
        Parent root= FXMLLoader.load(url);
        Scene sceneProducts = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneProducts);
        window.setTitle("Composite Products");
        window.show();

    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url= new File("src/main/java/sample.fxml").toURI().toURL();
        Parent root= FXMLLoader.load(url);
        Scene sceneStart = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneStart);
        window.setTitle("Register");
        window.show();
    }

    public void goToReportsScene (ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        URL url= new File("src/main/java/reports.fxml").toURI().toURL();
        Parent root= FXMLLoader.load(url);
        Scene sceneProducts = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneProducts);
        window.setTitle("Reports");
        window.show();

    }
}
