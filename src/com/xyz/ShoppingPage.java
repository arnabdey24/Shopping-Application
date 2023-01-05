package com.xyz;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;


public class ShoppingPage {
    private double height,weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;
    ObservableList<Element> entities;
    ListView<Element> centerList;

    public ShoppingPage(Stage window, double height, double weight,Connection con,Session session) {
        this.height = height;
        this.weight = weight;
        this.window=window;
        this.con=con;
        this.session=session;
        entities= FXCollections.observableArrayList();

        TextField searchBar=new TextField();
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String s=searchBar.getText().toLowerCase();
                ObservableList tmp = FXCollections.observableArrayList();
                for (Element entity : entities) {
                    String entityName=entity.getProductName().toLowerCase();
                    if(Pattern.matches(".*"+s+".*", entityName)){
                        tmp.add(entity);
                    }
                }
                centerList.setItems(tmp);
            }
        });
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
        centerList=new ListView<>();
        centerList.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println(">> Mouse Clicked");
            event.consume();
        });


        try {
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTS;" );
            while ( rs.next() ) {
                String  pid = rs.getString("pid");
                String  pic = rs.getString("pic");
                String  name = rs.getString("name");
                String  des = rs.getString("des");
                String  price = rs.getString("price");
                int  count = rs.getInt("count");

                entities.add(new Element(window,session,name,des,pid,"",pic,price,"shop",count));
                centerList.getItems().add(new Element(window,session,name,des,pid,"",pic,price,"shop",count));
            }
            rs.close();

        }catch (SQLException e){
            System.out.println(e);
        }



        //top
        BorderPane layout=new BorderPane();
        layout.setTop(top);
        layout.setCenter(centerList);

        scene=new Scene(layout,weight,height);

    }

    public Scene getScene() {
        return scene;
    }
}
