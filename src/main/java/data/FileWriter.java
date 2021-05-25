package data;

import bussiness.MenuItem;
import bussiness.Order;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileWriter {
    public void writeBillToTextFile (Order o, ArrayList<MenuItem> orderedItems) throws IOException {
        File file = new File("bill.txt");
        file.createNewFile();
        java.io.FileWriter fw = new java.io.FileWriter(file);
        fw.write("The details of your order: \n");
        fw.write("----------------------------------------\n");
        fw.write(" \n ORDER ID:" + o.getOrderID());
        fw.write("\n");
        fw.write("DATE :" + o.getOrderDate().getDay() + "." + o.getOrderDate().getMonth() + "." + o.getOrderDate().getYear() + " " + o.getOrderDate().getHour() + ":" + o.getOrderDate().getMinutes() + ":" + o.getOrderDate().getSeconds());
        fw.write("\n");
        fw.write("CLIENT:" + o.getClientID());
        fw.write("\n");
        int price = 0;
        for (MenuItem m: orderedItems){
            fw.write("PRODUCT: " + m.getName());
            fw.write("\n");
            fw.write("PRICE: " + m.getPrice());
            fw.write("\n");
            price =  price + m.getPrice();
        }
        fw.write("TOTAL PRICE:" + price);
        fw.write("\n");
        fw.write("----------------------------------------\n");
        fw.write("Thank you for your purchase \n");
        fw.close();
    }

    public void writeReport1 (TextField tf1, TextField tf2, Map<Order, ArrayList<MenuItem>> filteredOrders) throws IOException {
        File file = new File("report1.txt");
        file.createNewFile();
        java.io.FileWriter fw = new java.io.FileWriter(file);
        fw.write("REPORT 1: \n");
        fw.write("----------------------------------------\n");
        fw.write("Orders made between: \n");
        fw.write(" Start hour: " + tf1.getText() + " End hour:" + tf2.getText() + "\n");
        filteredOrders.forEach((key, value) ->
        {
            try {
                fw.write("ORDER ID: " + key.getOrderID() + "\n" +
                        "ORDER DATE: "+ key.getOrderDateString() + "\n" +
                        "ITEMS : "+ value.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.close();
    }
    public void writeReport2 (TextField tf1, List<MenuItem> filteredProducts) throws IOException {
        File file = new File("report2.txt");
        file.createNewFile();
        java.io.FileWriter fw = new java.io.FileWriter(file);
        fw.write("REPORT 2: \n");
        fw.write("----------------------------------------\n");
        fw.write("Products ordered more than: " +tf1.getText() + " times \n" );
        filteredProducts.forEach( p->
        {
            try {
                fw.write(p.toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.close();
    }

    public void writeReport3(TextField tf1, TextField tf2, List<Order> filteredOrders) throws IOException {
        File file = new File("report3.txt");
        file.createNewFile();
        java.io.FileWriter fw = new java.io.FileWriter(file);
        fw.write("REPORT 3: \n");
        fw.write("----------------------------------------\n");
        fw.write("Clients that ordered more than: " +tf1.getText() + " times \n"  + " and the value of the order was higher than " + tf2.getText() + "\n" );
        if (filteredOrders.isEmpty())
            fw.write("NONE \n");
        else {
            filteredOrders.forEach(p ->
            {
                try {
                    fw.write("client "+ p.getClientID() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        fw.close();
    }
    public void writeReport4 (TextField tf1, TextField tf2, Map <MenuItem, Integer> productsCount) throws IOException {
        File file = new File("report4.txt");
        file.createNewFile();
        java.io.FileWriter fw = new java.io.FileWriter(file);
        fw.write("REPORT 4 : \n");
        fw.write("----------------------------------------\n");
        fw.write("Products ordered on " + tf1.getText() + "/" + tf2.getText() +"/2021 \n" );
        productsCount.forEach( (key, value)->
        {
            try {
                fw.write(key.getName() + " ordered " +value.toString() + " times" + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.close();
    }

}
