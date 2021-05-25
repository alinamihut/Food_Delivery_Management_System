package presentation;

import bussiness.BaseProduct;
import bussiness.DeliveryService;
import bussiness.User;
import data.Serializator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Controller {
    public Button btnSignUp;
    public TextField tfUsername;
    public ComboBox cbType;
    public Button btnLogIn;
    public Boolean isAdmin;
    public Boolean isClient;
    public Boolean isEmployee;
    public PasswordField pfPassword;
    public static User user = new User();
    public Boolean checkEmailExistence(){
        try {
            FileReader fileUsers = new FileReader("src/main/java/users.txt");
            BufferedReader br = new BufferedReader(fileUsers);
            String data = br.readLine();
            int line = 1;
            while (data != null) {
                if(line % 3 == 1){
                    if(data.equals(tfUsername.getText())){
                        return true;
                    }
                }
                line++;
                data = br.readLine();
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public  boolean validateLogIn() {
        try {
            isAdmin = false;
            isClient = false;
            isEmployee = false;
            FileReader file = new FileReader("src\\main\\resources\\users.txt");
            BufferedReader br = new BufferedReader(file);

            int line = 1;
            boolean foundUsername = false, correctPassword = false;
            String data = br.readLine();
            while (data != null) {
                if (line % 3 == 1) {
                    if (tfUsername.getText().equals(data)) {
                        foundUsername = true;
                        user.setUsername(tfUsername.getText());
                    }
                } else {
                    if (line %3 == 2) {
                        if (foundUsername) {
                            if (pfPassword.getText().equals(data)) {
                                correctPassword = true;
                                user.setPassword(pfPassword.getText());
                            }
                        }
                    }
                }
                if (foundUsername && correctPassword) {
                    if (line % 3 == 0) {
                        if (data.equals("administrator")){
                            isAdmin = true;
                        }
                        else if (data.equals("client")){
                            isClient = true;
                        }
                        else if (data.equals("employee")){
                            isEmployee = true;
                        }
                        user.setType(data);
                        //Client client = new Client(user);
                        return true;
                    }
                }
                data = br.readLine();
                line++;
            }
            br.close();
            return false;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void logIn (ActionEvent actionEvent) throws IOException, ClassNotFoundException {
            if (validateLogIn()){
                if (isAdmin){
                    URL url= new File("src/main/java/admin.fxml").toURI().toURL();
                    Parent root= FXMLLoader.load(url);
                    Scene sceneProducts = new Scene(root);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(sceneProducts);
                    window.setTitle("Administrator");
                    window.show();
                    //ArrayList <BaseProduct> menuItems = Serializator.deserializeBaseProducts();
                }
                else if (isEmployee){
                    URL url= new File("src/main/java/employee.fxml").toURI().toURL();
                    Parent root= FXMLLoader.load(url);
                    Scene sceneOrders = new Scene(root);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(sceneOrders);
                    window.setTitle("Employee");
                    window.show();


                }
                else if (isClient){

                   // client.createTables();
                    URL url= new File("src/main/java/client.fxml").toURI().toURL();
                    Parent root= FXMLLoader.load(url);
                    Scene sceneOrders = new Scene(root);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(sceneOrders);
                    window.setTitle("Client");
                    window.show();
                }
                DeliveryService deliveryService = new DeliveryService();

            }
            else {
                showAlert("Username or password not correct!");
            }


    }

    public static User getUser(){
        return user;
    }
    public static void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Message");
        alert.setHeaderText(s);
        alert.show();
    }


    public void registerNewUser (ActionEvent actionEvent) throws IOException{
        if (checkEmailExistence()){
            showAlert("This username already exists!");
        }
        else{
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/users.txt", true))) {
            bw.write(tfUsername.getText());
            bw.newLine();
            bw.write(pfPassword.getText());
            bw.newLine();
            bw.write(cbType.getValue().toString());
            bw.newLine();
            User user = new User (tfUsername.getText(), pfPassword.getText(), cbType.getValue().toString());
            DeliveryService.listOfUsers.add(user);
            user.setUserID(DeliveryService.listOfUsers.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
            showAlert("User registered succesfully!");
        tfUsername.clear();
        pfPassword.clear();
    }
    }
}
