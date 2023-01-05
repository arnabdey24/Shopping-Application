package com.xyz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;


public class Success {
    private double height,weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;

    public Success(Stage window, double height, double weight,Connection con,Session session) {
        this.height = height;
        this.weight = weight;
        this.window=window;
        this.con=con;
        this.session=session;

        TextField searchBar=new TextField();
        searchBar.setPrefWidth(1000);
        searchBar.setMaxWidth(1000);
        searchBar.setMaxHeight(30);
        searchBar.setPromptText("Search");
        searchBar.setFocusTraversable(false);

        ImageLabel cartBtn=new ImageLabel("resources/images/cart.png",30,30);
        cartBtn.setOnMouseClicked(event -> window.setScene(new Cart(window, height, weight,con,session).getScene()));
        ImageLabel logOutBtn=new ImageLabel("resources/images/logout.png",30,30);
        logOutBtn.setOnMouseClicked(event -> window.setScene(new AfterLogout(window, height, weight,con,session).getScene()));

        HBox top=new HBox();
        top.setPadding(new Insets(10));
        top.setSpacing(20);
        top.setAlignment(Pos.CENTER);
        top.setPrefHeight(50);
        top.getChildren().addAll(searchBar,cartBtn,logOutBtn);






        //center
        VBox center=new VBox();
        center.setSpacing(10);
        center.setAlignment(Pos.CENTER);
        Text text=new Text("Success");
        text.setFont(new Font(30));
        String tmp;
        if(session.isImp==0)tmp="Home";
        else tmp="Back";
        BlackBtn goHome=new BlackBtn(tmp,210,25, Color.BLACK);
        goHome.setOnMouseClicked(event -> {
            if(session.isImp==0)window.setScene(new ShoppingPage(window, height, weight,con,session).getScene());
            else window.setScene(new Pending(window, height, weight,con,session).getScene());
        });
        center.getChildren().addAll(text,goHome);








        BorderPane layout=new BorderPane();
        layout.setTop(top);
        layout.setCenter(center);


        scene=new Scene(layout,weight,height);

    }

    public Scene getScene() {
        return scene;
    }
}
