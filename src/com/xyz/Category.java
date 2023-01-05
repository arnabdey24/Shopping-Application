package com.xyz;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;

public class Category {
    private double height,weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;
    public Category(Stage window, double height, double weight,Connection con,Session session) {
        this.height = height;
        this.weight = weight;
        this.window=window;
        this.con=con;
        this.session=session;

        Label label=new Label("Who are You?");
        label.setFont(new Font(50));
        label.setAlignment(Pos.CENTER);
        StackPane top=new StackPane();
        top.getChildren().addAll(label);

        BlackBtn managerBtn = new BlackBtn("Manager",200,80,Color.BLACK);
        managerBtn.setOnMouseClicked(event -> {
            session.isImp=3;
            window.setScene(new Login(window, height, weight,con,session).getScene());
        });

        BlackBtn customerBtn = new BlackBtn("Customer",200,80,Color.BLACK);
        customerBtn.setOnMouseClicked(event -> {
            session.isImp=2;
            window.setScene(new Login(window, height, weight,con,session).getScene());
        });
        BlackBtn employeeBtn = new BlackBtn("Employee",200,80,Color.BLACK);
        employeeBtn.setOnMouseClicked(event -> {
            session.isImp=3;
            window.setScene(new Login(window, height, weight,con,session).getScene());
        });








        //center
        VBox center=new VBox();
        center.setSpacing(10);
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(managerBtn,customerBtn,employeeBtn);

        //top
        BorderPane layout=new BorderPane();
        layout.setTop(top);
        layout.setCenter(center);

        scene=new Scene(layout,weight,height);

    }

    public Scene getScene() {
        return scene;
    }
}
