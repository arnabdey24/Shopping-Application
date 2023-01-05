package com.xyz;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.sql.*;

public class Main extends Application {
    double height=500;
    double weight=800;
    Stage window;
    Connection con;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window=primaryStage;
        window.setTitle("Shopping-app");

        createDbConnection();
        check();

        Session session=new Session(null,2,con,height,weight);


        //java.net.URL url = ClassLoader.getSystemResource("resources/images/cart.png");
        FileInputStream input = new FileInputStream("resources/images/icon.png");
        window.getIcons().add(new Image(input));

        window.setScene(new Category(window,height,weight,con,session).getScene());



        window.show();
    }

    public void createDbConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/shop","postgres", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void check(){
        try {
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTS;" );
        }catch (SQLException e){
            System.out.println(-1);
            try {
                Statement stmt;
                stmt = con.createStatement();
                String sql = "CREATE TABLE PRODUCTS " +
                        "(pid TEXT PRIMARY KEY     NOT NULL," +
                        " name          TEXT    NOT NULL, " +
                        " des          TEXT    NOT NULL, " +
                        " count           INT     NOT NULL, " +
                        " price        INT  NOT NULL , " +
                        " pic         TEXT)";
                stmt.executeUpdate(sql);
                stmt.close();
            }catch (SQLException ex){
                System.out.println("Database error");
            }

        }


        try {
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS;" );
        }catch (SQLException e){
            System.out.println(-1);
            try {
                Statement stmt;
                stmt = con.createStatement();
                String sql = "CREATE TABLE USERS " +
                        "(email TEXT PRIMARY KEY     NOT NULL," +
                        " fname         TEXT    NOT NULL, " +
                        " lname         TEXT    NOT NULL, " +
                        " password           TEXT     NOT NULL, " +
                        " uname       TEXT  NOT NULL , " +
                        " isemp      INT  NOT NULL , " +
                        " address         TEXT)";
                stmt.executeUpdate(sql);
                stmt.close();
                //email,fname,lname,password,uname,isemp,address
            }catch (SQLException ex){
                System.out.println("Database error");
            }
        }


        try {
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM CART;" );
        }catch (SQLException e){
            System.out.println(-1);
            try {
                Statement stmt;
                stmt = con.createStatement();
                String sql = "CREATE TABLE CART " +
                        "(email TEXT      NOT NULL," +
                        " pid   TEXT  PRIMARY KEY  NOT NULL, " +
                        " name          TEXT    NOT NULL, " +
                        " des          TEXT    NOT NULL, " +
                        " count           INT     NOT NULL, " +
                        " price        INT  NOT NULL , " +
                        " pic         TEXT)";
                stmt.executeUpdate(sql);
                stmt.close();
            }catch (SQLException ex){
                System.out.println("Database error");
            }
        }


        try {
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ORDERS;" );
        }catch (SQLException e){
            System.out.println(-1);
            try {
                Statement stmt;
                stmt = con.createStatement();
                String sql = "CREATE TABLE ORDERS " +
                        "(email TEXT      NOT NULL," +
                        " pid          TEXT    NOT NULL, " +
                        " uid    TEXT PRIMARY KEY   NOT NULL, " +
                        " name          TEXT    NOT NULL, " +
                        " des          TEXT    NOT NULL, " +
                        " count           INT     NOT NULL, " +
                        " price        INT  NOT NULL , " +
                        " pic         TEXT)";
                stmt.executeUpdate(sql);
                stmt.close();
            }catch (SQLException ex){
                System.out.println("Database error");
            }
        }
    }
}
