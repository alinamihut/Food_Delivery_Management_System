package presentation;

import bussiness.DeliveryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Reports {
    public TextField tfStartTimeR1;
    public TextField tfEndTimeR1;
    public Button btnReport1;
    public TextField tfNrTimeR2;
    public Button btnReport2;
    public Button btnReport3;
    public TextField tfValueR3;
    public TextField tfNrTimeR3;
    public TextField tfDayR4;
    public TextField tfMonthR4;
    public Button btnReport4;
    public Button btnBack;
    DeliveryService deliveryService = new DeliveryService();

    public void generateReport1 (ActionEvent actionEvent){
        deliveryService.generateReport1(tfStartTimeR1, tfEndTimeR1);
        Administrator.showConformationAlert("Report was generated!");
    }
    public void generateReport2 (ActionEvent actionEvent){
        deliveryService.generateReport2(tfNrTimeR2);
        Administrator.showConformationAlert("Report was generated!");
    }
    public void generateReport3 (ActionEvent actionEvent){
        deliveryService.generateReport3(tfValueR3, tfNrTimeR3);
        Administrator.showConformationAlert("Report was generated!");
    }
    public void generateReport4 (ActionEvent actionEvent){
        deliveryService.generateReport4(tfDayR4,tfMonthR4);
        Administrator.showConformationAlert("Report was generated!");
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/admin.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene sceneProducts = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneProducts);
        window.setTitle("Admin");
        window.show();

    }

}
