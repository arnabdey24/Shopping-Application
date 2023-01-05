package com.xyz;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class UpdateProduct {
    private double height, weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;
    Element element;

    public UpdateProduct(Stage window, double height, double weight,Connection con,Session session,Element element) {
        this.height = height;
        this.weight = weight;
        this.window = window;
        this.con=con;
        this.session=session;
        this.element=element;

        Label label = new Label("Update Product");
        label.setFont(new Font(50));
        label.setAlignment(Pos.CENTER);
        StackPane top = new StackPane();
        top.getChildren().addAll(label);

        TextField pName=new TextField(element.getProductName());
        pName.setMaxWidth(200);
        pName.setPromptText("Product Name");
        pName.setFocusTraversable(false);
        GridPane.setConstraints(pName,0,0);

        TextField pdes=new TextField(element.getDescription());
        pName.setMaxWidth(200);
        pName.setPromptText("Product Name");
        pName.setFocusTraversable(false);
        GridPane.setConstraints(pdes,1,0);

        TextField price=new TextField(element.getPrice());
        price.setMaxWidth(200);
        price.setPromptText("price");
        price.setFocusTraversable(false);
        GridPane.setConstraints(price,0,1);

        TextField amount=new TextField(String.valueOf(element.getCount()));
        amount.setMaxWidth(200);
        amount.setPromptText("Amount");
        amount.setFocusTraversable(false);
        GridPane.setConstraints(amount,1,1);


        GridPane mid=new GridPane();
        mid.getChildren().addAll(pName,pdes,price,amount);
        mid.setAlignment(Pos.CENTER);
        mid.setVgap(5);
        mid.setHgap(5);



        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(imageFilter);

        ImageBtn picSelect = new ImageBtn(element.getImageUrl(),100,100);
        picSelect.setOnMouseClicked(event -> {
            //window.setScene(new EmployeeHome(window, height, weight).getScene());
            String pic;
            try {
                pic=fc.showOpenDialog(window).getAbsolutePath();
            }catch (Exception e){
                pic="resources/images/no_image.png";
            }

            System.out.println(pic);
            picSelect.changeImage(pic);
        });


        BlackBtn addBtn=new BlackBtn("Update",100,100,Color.BLACK);
        addBtn.setOnMouseClicked(event -> {
            String _pic=picSelect.location;
            String _pid= element.getPid();
            int _count=Integer.parseInt(amount.getText());
            String _price=price.getText();
            String _name=pName.getText();
            String _des=pdes.getText();
            try {
                Statement stmt;
                stmt = con.createStatement();
                String sql = "UPDATE PRODUCTS " +
                        "set pic = '"+_pic+"' ," +
                        "count = "+_count+" ," +
                        "price = '"+_price+"' ," +
                        "name = '"+_name+"' " +
                        "des = '"+_des+"' " +
                        "where pid = '"+_pid+"' ;";
                stmt.executeUpdate(sql);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            window.setScene(new ManageStore(window, height, weight,con,session).getScene());
        });


        ImageLabel backBtn=new ImageLabel("resources/images/back2.png",200,200);
        backBtn.setOnMouseClicked(event -> {
            window.setScene(new ManageStore(window, height, weight,con,session).getScene());
        });
        HBox bottom=new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().add(backBtn);


        //center
        HBox center = new HBox();
        center.setSpacing(10);
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(picSelect,mid,addBtn);

        //top
        BorderPane layout = new BorderPane();
        layout.setTop(top);
        layout.setCenter(center);
        layout.setBottom(bottom);



        scene=new Scene(layout,weight,height);
    }

    public Scene getScene() {
        return scene;
    }

    void createProduct(String _pid,String _pic,String _name, String _price,int _count) throws SQLException {
        String sql = "INSERT INTO PRODUCTS (pid,name,count,price,pic) "
                + "VALUES ('"+_pid+"', '"+ _name+"', "+_count+", '"+_price+"', '"+_pic+"');";

        /*String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (3, 'AS', 32, 'California', 20000.00 );";*/
        Statement statement=con.createStatement();
        statement.executeUpdate(sql);

    }
}
