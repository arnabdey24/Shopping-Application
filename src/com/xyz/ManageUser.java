package com.xyz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManageUser {
    private double height,weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;

    TableView<Customer> customerView;
    TableView<Employee> employeeView;
    ObservableList<Customer> customers;
    ObservableList<Employee> employees;

    public ManageUser(Stage window, double height, double weight,Connection con,Session session, int field) {
        this.height = height;
        this.weight = weight;
        this.window=window;
        this.con=con;
        this.session=session;
        customers= FXCollections.observableArrayList();
        employees=FXCollections.observableArrayList();

        VBox layout =new VBox();

        if(field==0){
            fillCustomer();
            TableColumn<Customer,String> userNameColumn=new TableColumn<>("User Name");
            TableColumn<Customer,String> firstNameColumn=new TableColumn<>("First Name");
            TableColumn<Customer,String> lastNameColumn=new TableColumn<>("Last Name");
            TableColumn<Customer,String> addressColumn=new TableColumn<>("Address");
            TableColumn<Customer,String> emailColumn=new TableColumn<>("Email");
            TableColumn<Customer,String> passwordColumn=new TableColumn<>("Password");

            userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

            customerView=new TableView<>();
            customerView.getColumns().add(userNameColumn);
            customerView.getColumns().add(firstNameColumn);
            customerView.getColumns().add(lastNameColumn);
            customerView.getColumns().add(emailColumn);
            customerView.getColumns().add(addressColumn);
            customerView.getColumns().add(passwordColumn);
            customerView.setItems(customers);
            layout.getChildren().add(customerView);
        }
        if(field==1){
            fillEmployee();
            TableColumn<Employee,String> userNameColumn=new TableColumn<>("User Name");
            TableColumn<Employee,String> firstNameColumn=new TableColumn<>("First Name");
            TableColumn<Employee,String> lastNameColumn=new TableColumn<>("Last Name");
            TableColumn<Employee,String> emailColumn=new TableColumn<>("Email");
            TableColumn<Employee,String> passwordColumn=new TableColumn<>("Password");

            userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

            employeeView=new TableView<>();
            employeeView.getColumns().add(userNameColumn);
            employeeView.getColumns().add(firstNameColumn);
            employeeView.getColumns().add(lastNameColumn);
            employeeView.getColumns().add(emailColumn);
            employeeView.getColumns().add(passwordColumn);
            employeeView.setItems(employees);
            layout.getChildren().add(employeeView);
        }

        HBox down =new HBox();
        BlackBtn add=new BlackBtn("Add",100,60,Color.BLACK);
        add.setOnMouseClicked(event -> {
            window.setScene(new AddUser(window, height, weight,con,session,field).getScene());
        });
        BlackBtn update=new BlackBtn("Update",100,60,Color.BLACK);
        update.setOnMouseClicked(event -> {
            if(field==0){
                window.setScene(new UpdateUser(window, height, weight,con,session,field,customerView.getSelectionModel().getSelectedItem(),new Employee()).getScene());
            }else{
                window.setScene(new UpdateUser(window, height, weight,con,session,field,new Customer(),employeeView.getSelectionModel().getSelectedItem()).getScene());
            }
        });
        BlackBtn delete=new BlackBtn("Delete",100,60,Color.BLACK);
        delete.setOnMouseClicked(event -> {
            String key=null;
            if(field==0)key=customerView.getSelectionModel().getSelectedItem().getEmail();
            if(field==1)key=employeeView.getSelectionModel().getSelectedItem().getEmail();

            if(key!=null){
                try {
                    Statement stmt;
                    stmt = session.con.createStatement();
                    String sql = "DELETE from USERS where email = '"+key+"' ;";
                    stmt.executeUpdate(sql);
                    window.setScene(new AfterDelete(window, height, weight,con,session,field).getScene());
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
        BlackBtn goBack=new BlackBtn("Go Back",100,60,Color.BLACK);
        goBack.setOnMouseClicked(event -> {
            window.setScene(new ManagerHome(window, height, weight,con,session).getScene());
        });
        down.getChildren().addAll(goBack,add,update,delete);
        down.setSpacing(5);
        down.setPadding(new Insets(10));
        down.setAlignment(Pos.CENTER);



        layout.getChildren().add(down);


        scene=new Scene(layout,weight,height);

    }

    public Scene getScene() {
        return scene;
    }

    void fillEmployee(){
        try{
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE isemp = 1 ;" );
            while ( rs.next() ) {
                String  email = rs.getString("email");
                String  first = rs.getString("fname");
                String  last = rs.getString("lname");
                String  user = rs.getString("uname");
                String  password = rs.getString("password");

                employees.add(new Employee(email,first,last,user,password));
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void fillCustomer(){
        try{
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE isemp = 0 ;" );
            while ( rs.next() ) {
                String  email = rs.getString("email");
                String  first = rs.getString("fname");
                String  last = rs.getString("lname");
                String  user = rs.getString("uname");
                String  address = rs.getString("address");
                String  password = rs.getString("password");

                customers.add(new Customer(email,first,last,user,address,password));
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
