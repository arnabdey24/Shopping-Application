package com.xyz;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;


public class AfterDelete {
    private double height,weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;
    public AfterDelete(Stage window, double height, double weight,Connection con,Session session,int field) {
        this.height = height;
        this.weight = weight;
        this.window=window;
        this.con=con;
        this.session=session;



        //center
        VBox center=new VBox();
        center.setSpacing(10);
        center.setAlignment(Pos.CENTER);
        Text text=new Text("Success");
        text.setFont(new Font(30));
        BlackBtn goHome=new BlackBtn("Go Back",210,25, Color.BLACK);
        goHome.setOnMouseClicked(event -> window.setScene(new ManageUser(window, height, weight,con,session,field).getScene()));
        center.getChildren().addAll(text,goHome);








        BorderPane layout=new BorderPane();
        layout.setCenter(center);


        scene=new Scene(layout,weight,height);

    }

    public Scene getScene() {
        return scene;
    }
}
