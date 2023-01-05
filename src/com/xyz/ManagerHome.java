package com.xyz;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;

public class ManagerHome {
    private double height,weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;
    public ManagerHome(Stage window, double height, double weight,Connection con,Session session) {
        this.height = height;
        this.weight = weight;
        this.window=window;
        this.con=con;
        this.session=session;

        Label label=new Label("Hi, Manager!");
        label.setFont(new Font(50));
        label.setAlignment(Pos.CENTER);
        StackPane top=new StackPane();
        top.getChildren().addAll(label);

        ImageLabel outBtn = new ImageLabel("resources/images/logout.png",110,110);
        outBtn.setOnMouseClicked(event -> {
            window.setScene(new AfterLogout(window, height, weight,con,session).getScene());
        });

        BlackBtn pendingBtn = new BlackBtn("Approve pending orders",200,60,Color.BLACK);
        pendingBtn.setOnMouseClicked(event -> {
            window.setScene(new Pending(window, height, weight,con,session).getScene());
        });
        BlackBtn manageStoreBtn = new BlackBtn("Manage products",200,60,Color.BLACK);
        manageStoreBtn.setOnMouseClicked(event -> {
            window.setScene(new ManageStore(window, height, weight,con,session).getScene());
        });
        BlackBtn mngEmpBtn = new BlackBtn("Manage Employee",200,60,Color.BLACK);
        mngEmpBtn.setOnMouseClicked(event -> {
            window.setScene(new ManageUser(window, height, weight,con,session,1).getScene());
        });
        BlackBtn mngCusBtn = new BlackBtn("Manage Customer",200,60,Color.BLACK);
        mngCusBtn.setOnMouseClicked(event -> {
            window.setScene(new ManageUser(window, height, weight,con,session,0).getScene());
        });








        //center
        VBox center=new VBox();
        center.setSpacing(10);
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(outBtn,pendingBtn,manageStoreBtn,mngEmpBtn,mngCusBtn);

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
