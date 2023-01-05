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

public class AddProducts {
    private double height, weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;

    public AddProducts(Stage window, double height, double weight,Connection con,Session session) {
        this.height = height;
        this.weight = weight;
        this.window = window;
        this.con=con;
        this.session=session;

        Label label = new Label("Add Products");
        label.setFont(new Font(50));
        label.setAlignment(Pos.CENTER);
        StackPane top = new StackPane();
        top.getChildren().addAll(label);

        TextField pName=new TextField();
        pName.setMaxWidth(200);
        pName.setPromptText("Product Name");
        pName.setFocusTraversable(false);
        GridPane.setConstraints(pName,0,0);

        TextField pdes=new TextField();
        pdes.setMaxWidth(200);
        pdes.setPromptText("Product Description");
        pdes.setFocusTraversable(false);
        GridPane.setConstraints(pdes,1,0);

        TextField price=new TextField();
        price.setMaxWidth(200);
        price.setPromptText("price");
        price.setFocusTraversable(false);
        GridPane.setConstraints(price,0,1);

        TextField amount=new TextField();
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

        ImageBtn picSelect = new ImageBtn("",100,100);
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


        BlackBtn addBtn=new BlackBtn("Add",100,100,Color.BLACK);
        addBtn.setOnMouseClicked(event -> {
                String _pic=picSelect.location;
                String _pid= UUID.randomUUID().toString();
                int _count=Integer.parseInt(amount.getText());
                String _price=price.getText();
                String _name=pName.getText();
                String _des=pdes.getText();
            try {
                createProduct(_pid,_pic,_name,_des,_price,_count);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            window.setScene(new ManageStore(window, height, weight,con,session).getScene());
        });

        ImageLabel backBtn=new ImageLabel("resources/images/back2.png",200,200);
        backBtn.setOnMouseClicked(event -> {
            window.setScene(new ManageStore(window, height, weight,con,session).getScene());
        });



        //center
        HBox center = new HBox();
        center.setSpacing(10);
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(picSelect,mid,addBtn);

        HBox bottom=new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().add(backBtn);
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

    void createProduct(String _pid,String _pic,String _name,String _des, String _price,int _count) throws SQLException {
        String sql = "INSERT INTO PRODUCTS (pid,name,des,count,price,pic) "
                + "VALUES ('"+_pid+"', '"+ _name+"', '"+ _des+"', "+_count+", '"+_price+"', '"+_pic+"');";

        /*String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (3, 'AS', 32, 'California', 20000.00 );";*/
        Statement statement=con.createStatement();
        statement.executeUpdate(sql);

    }
}
