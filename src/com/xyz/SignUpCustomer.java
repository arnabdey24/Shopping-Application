package com.xyz;

import com.sun.xml.internal.ws.util.StringUtils;
import javafx.scene.control.PasswordField;
import org.postgresql.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.DoubleToIntFunction;

public class SignUpCustomer {
    private double height, weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;

    public SignUpCustomer(Stage window, double height, double weight,Connection con,Session session) {
        this.height = height;
        this.weight = weight;
        this.window = window;
        this.con=con;
        this.session=session;

        Label label = new Label("Sign Up");
        label.setFont(new Font(50));
        label.setAlignment(Pos.CENTER);
        StackPane top = new StackPane();
        top.getChildren().addAll(label);

        TextField firstName=new TextField();
        firstName.setMaxWidth(200);
        firstName.setPromptText("First Name");
        firstName.setFocusTraversable(false);
        GridPane.setConstraints(firstName,0,0);
        TextField lastName=new TextField();
        lastName.setMaxWidth(200);
        lastName.setPromptText("Last Name");
        lastName.setFocusTraversable(false);
        GridPane.setConstraints(lastName,1,0);
        TextField userName=new TextField();
        userName.setMaxWidth(200);
        userName.setPromptText("Username");
        userName.setFocusTraversable(false);
        GridPane.setConstraints(userName,0,1);
        PasswordField password=new PasswordField();
        password.setMaxWidth(200);
        password.setPromptText("Password");
        password.setFocusTraversable(false);
        GridPane.setConstraints(password,1,1);
        TextField email=new TextField();
        email.setMaxWidth(400);
        email.setPromptText("Email");
        email.setFocusTraversable(false);
        GridPane.setConstraints(email,0,2,2,1);


        BlackBtn signUp = new BlackBtn("Sign Up", 200, 80, Color.BLACK);
        GridPane.setConstraints(signUp,0,3,2,1, HPos.RIGHT, VPos.CENTER);
        signUp.setOnMouseClicked(event -> {

            String _first=firstName.getText();
            String _last=lastName.getText();
            String _email=email.getText();
            String _password=password.getText();
            String _uName=userName.getText();
            if(_first.isEmpty() && _last.isEmpty() &&
                    _email.isEmpty() && _password.isEmpty() &&
                    _uName.isEmpty()){

            }
            else {
                try {
                    createUser(_email,_first,_last,_uName,_password,"",0);
                    session.uid=_email;
                    session.isImp=0;
                } catch (SQLException e) {
                    Statement statement = null;
                }
                if(session.uid!=null)window.setScene(new ShoppingPage(window, height, weight,con,session).getScene());
            }
        });



        //center
        GridPane center = new GridPane();
        //center.setPadding(new Insets(20));
        center.setHgap(10);
        center.setVgap(10);
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(firstName,lastName,userName,email,password,signUp);

        //top
        BorderPane layout = new BorderPane();
        layout.setTop(top);
        layout.setCenter(center);



        scene=new Scene(layout,weight,height);
    }

    public Scene getScene() {
        return scene;
    }

    void createUser(String email,String first,String last, String uname,String password,String address,int isEmp) throws SQLException {
        String sql = "INSERT INTO USERS (email,fname,lname,password,uname,isemp,address) "
                + "VALUES ('"+email+"', '"+ first+"', '"+last+"', '"+password+"', '"+uname+"', "+isEmp+", '"+address+"' );";

        /*String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (3, 'AS', 32, 'California', 20000.00 );";*/
        Statement statement=con.createStatement();
        statement.executeUpdate(sql);

    }
}

