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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;


public class ManageStore {
    private double height,weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;
    ObservableList<Element> entities;
    ListView<Element> centerList;

    public ManageStore(Stage window, double height, double weight,Connection con,Session session) {
        this.height = height;
        this.weight = weight;
        this.window=window;
        this.con=con;
        this.session=session;
        entities= FXCollections.observableArrayList();

        ImageLabel backBtn=new ImageLabel("resources/images/back.png",30,30);
        backBtn.setOnMouseClicked(event -> {
            if(session.isImp==1)window.setScene(new EmployeeHome(window, height, weight,con,session).getScene());
            if (session.isImp==-1)window.setScene(new ManagerHome(window, height, weight,con,session).getScene());
        });
        HBox back=new HBox();
        back.setSpacing(5);
        back.setAlignment(Pos.CENTER);
        back.getChildren().addAll(backBtn);

        TextField searchBar=new TextField();
        searchBar.setPrefWidth(1000);
        searchBar.setMaxWidth(1000);
        searchBar.setMaxHeight(30);
        searchBar.setPromptText("Search");
        searchBar.setFocusTraversable(false);
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



        ImageLabel logOutBtn=new ImageLabel("resources/images/logout.png",30,30);
        logOutBtn.setOnMouseClicked(event -> window.setScene(new AfterLogout(window, height, weight,con,session).getScene()));


        HBox bar=new HBox();
        bar.setSpacing(20);
        bar.setAlignment(Pos.CENTER_RIGHT);
        bar.getChildren().addAll(searchBar,logOutBtn);

        HBox top=new HBox();
        top.setPadding(new Insets(10));
        top.setSpacing(80);
        top.setPrefHeight(50);
        top.getChildren().addAll(back,bar);


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


                entities.add(new Element(window,session,name,des,pid,"",pic,price,"manage",count));
                centerList.getItems().add(new Element(window,session,name,des,pid,"",pic,price,"manage",count));
            }
            rs.close();

        }catch (SQLException e){
            System.out.println(e);
        }



        //bottom
        VBox center=new VBox();
        BlackBtn checkoutAll=new BlackBtn("Add products",210,25, Color.BLACK);
        checkoutAll.setOnMouseClicked(event -> window.setScene(new AddProducts(window, height, weight,con,session).getScene()));
        checkoutAll.setAlignment(Pos.CENTER);
        center.getChildren().addAll(checkoutAll);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(10));



        BorderPane layout=new BorderPane();
        layout.setTop(top);
        layout.setCenter(centerList);
        layout.setBottom(center);


        scene=new Scene(layout,weight,height);

    }

    public Scene getScene() {
        return scene;
    }
}
