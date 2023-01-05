package com.xyz;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Login {

    private double height,weight;
    Stage window;
    Scene scene;
    Connection con;
    Session session;
    public Login(Stage window, double height, double weight,Connection con,Session session) {
        this.height = height;
        this.weight = weight;
        this.window=window;
        this.con=con;
        this.session=session;

        Label label=new Label("Welcome");
        label.setFont(new Font(80));
        label.setAlignment(Pos.CENTER);
        StackPane top=new StackPane();
        top.getChildren().addAll(label);


        TextField textField1=new TextField();
        textField1.setMaxWidth(200);
        textField1.setPromptText("Email");
        textField1.setFocusTraversable(false);

        PasswordField textField2=new PasswordField();
        textField2.setMaxWidth(200);
        textField2.setPromptText("Password");
        textField2.setFocusTraversable(false);

        Button login=new Button("Login");
        login.setOnAction(event -> {
            String _email=textField1.getText();
            String _password=textField2.getText();

            boolean flag=false;

            if(!_email.isEmpty() && !_password.isEmpty()){
                try{
                    Statement stmt;
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE email = '"+_email+"';" );
                    while ( rs.next() ) {
                        String  email = rs.getString("email");
                        String  password = rs.getString("password");
                        int isEmp=rs.getInt("isemp");

                        if(password.equals(_password)){
                            session.uid=_email;
                            session.isImp=isEmp;
                        }

                        System.out.println( email );
                        System.out.println( password );
                        System.out.println( isEmp );
                        System.out.println();
                    }
                    rs.close();
                }catch (Exception e){

                }
            }

            if(session.uid!=null){
                if(session.isImp==-1){//manager
                    window.setScene(new ManagerHome(window,height,weight,con,session).getScene());
                }
                if(session.isImp==0){//customer
                    window.setScene(new ShoppingPage(window,height,weight,con,session).getScene());
                }
                if(session.isImp==1){//employee
                    window.setScene(new EmployeeHome(window,height,weight,con,session).getScene());
                }
            }


        });

        Text signUp=new Text("Sign Up");
        signUp.setFill(Color.BLUE);
        signUp.setOnMouseClicked(event -> {
            window.setScene(new SignUp(window,height,weight,con,session).getScene());
        });


        VBox center=new VBox();
        center.setSpacing(10);
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(textField1,textField2,login);

        if(session.isImp==2)center.getChildren().add(signUp);

        BorderPane layout=new BorderPane();
        layout.setTop(top);
        layout.setCenter(center);

        ImageLabel backBtn=new ImageLabel("resources/images/back2.png",150,150);
        backBtn.setOnMouseClicked(event -> {
            window.setScene(new Category(window, height, weight,con,session).getScene());
        });
        HBox bottom=new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().add(backBtn);

        layout.setBottom(bottom);



        scene=new Scene(layout,weight,height);

    }

    public Scene getScene() {
        return scene;
    }
}
