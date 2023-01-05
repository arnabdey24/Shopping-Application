package com.xyz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Element extends BorderPane {
    private int count;
    private String productName;
    private String description;
    private String imageUrl;
    private String price;
    private String category;
    private String pid;
    private Session session;


    public Element(Stage window, Session session, String productName,String description, String pid, String uid, String imageUrl, String price, String category, int amount) {
        this.productName =productName;
        this.imageUrl=imageUrl;
        this.price=price;
        this.category = category;
        this.pid=pid;
        this.session=session;
        this.description=description;
        count=1;

        String selectTxt;
        if(category.equals("pending"))selectTxt="Accept";
        else if(category.equals("cart"))selectTxt="Check Out";
        else if(category.equals("manage")){
            selectTxt="Update";
            count=amount;
        }
        else selectTxt="Add to cart";

        VBox left =new VBox();
        left.setAlignment(Pos.CENTER);
        left.setSpacing(5);
        ImageLabel imageBtn = new ImageLabel(imageUrl,100,100);
        HBox countingCmp=new HBox();
        countingCmp.setAlignment(Pos.CENTER);
        countingCmp.setSpacing(5);
        Text countNumber =new Text();
        countNumber.setText(String.valueOf(count));
        ImageBtn incrementBtn = new ImageBtn("resources/images/plus.png",20,20);
        incrementBtn.setOnMouseClicked(event ->{
            count++;
            countNumber.setText(String.valueOf(count));
        });
        ImageBtn decrementBtn = new ImageBtn("resources/images/minus.png",20,20);
        decrementBtn.setOnMouseClicked(event -> {
            count--;
            if(count==0)count++;
            countNumber.setText(String.valueOf(count));
        });
        if(category.equals("cart"))countNumber.setText(String.valueOf(amount));

        if(category.equals("manage") || category.equals("cart") || category.equals("pending"))countingCmp.getChildren().addAll(countNumber);
        else countingCmp.getChildren().addAll(incrementBtn,countNumber,decrementBtn);
        left.getChildren().addAll(imageBtn,countingCmp);

        //center
        VBox center=new VBox();
        Text nameTxt=new Text(productName);
        nameTxt.setFont(new Font(18));
        Text descriptionTxt=new Text(description);
        descriptionTxt.setFont(new Font(12));
        center.getChildren().addAll(nameTxt,descriptionTxt);
        center.setAlignment(Pos.CENTER);


        //right
        VBox right=new VBox();
        right.setSpacing(5);
        right.setAlignment(Pos.CENTER);
        Text priceTxt=new Text(String.valueOf(price)+" $");
        priceTxt.setFont(new Font(20));
        BlackBtn selectBtn=new BlackBtn(selectTxt,110,25, Color.BLUE);
        selectBtn.setOnMouseClicked(event -> {

            if(category.equals("manage")){
                window.setScene(new UpdateProduct(window, window.getHeight(), window.getWidth(),session.con,session,this).getScene());
            }

            if(category.equals("shop")){
                try {
                    addToCart(session.uid,pid,imageUrl,productName,description,price,count);
                } catch (SQLException e) {
                    System.out.println(e);
                    selectBtn.setColor(Color.RED);
                    selectBtn.setText("Already in \nthe cart");
                }
            }

            if(category.equals("cart")){
                try {
                    makeOrder(session.uid,pid,imageUrl,productName,description,price,count);
                    checkOut(pid);
                    System.out.println("ordered");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                window.setScene(new Success(window, session.height, session.weight,session.con,session).getScene());
            }

            if(category.equals("pending")){
                try {
                    //makeOrder(session.uid,pid,imageUrl,productName,price,count);
                    //
                    Statement stmt1,stmt2,stmt3;
                    int preCount=0;
                    stmt1 = session.con.createStatement();
                    ResultSet rs = stmt1.executeQuery( "SELECT * FROM PRODUCTS where pid = '"+pid+"' ;" );
                    while ( rs.next() ) {
                        preCount = rs.getInt("count");
                    }
                    System.out.println(preCount);
                    int newCount=preCount-count;
                    System.out.println(newCount);
                    stmt2=session.con.createStatement();
                    String sql = "UPDATE PRODUCTS SET " +
                            "count = "+newCount+" " +
                            "where pid = '"+pid+"' ;";
                    stmt2.executeUpdate(sql);

                    System.out.println(uid);

                    stmt3=session.con.createStatement();
                    String sql1 = "DELETE from ORDERS where uid = '"+uid+"' ;";
                    stmt3.executeUpdate(sql1);



                    System.out.println("order accepted");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                window.setScene(new Success(window, session.height, session.weight,session.con,session).getScene());
            }


        });
        BlackBtn cancelBtn=new BlackBtn("Cancel",110,25, Color.RED);
        cancelBtn.setOnMouseClicked(event -> {
            try {
                Statement stmt3;
                stmt3=session.con.createStatement();
                String sql1 = "DELETE from ORDERS where uid = '"+uid+"' ;";
                stmt3.executeUpdate(sql1);
            }catch (SQLException e){
                e.printStackTrace();
            }
            window.setScene(new Success(window, session.height, session.weight,session.con,session).getScene());
        });
        right.getChildren().addAll(priceTxt,selectBtn);

        if(category.equals("pending"))right.getChildren().add(cancelBtn);



        right.setMaxWidth(130);
        left.setMaxWidth(100);

        setRight(right);
        setCenter(center);
        setLeft(left);

        setPadding(new Insets(10));


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    void addToCart(String _email, String _pid, String _pic, String _name,String _des, String _price, int _count) throws SQLException {
        System.out.println(session.uid);
        String sql = "INSERT INTO CART (email,pid,name,des,count,price,pic) "
                + "VALUES ('"+_email+"', '"+ _pid+"' ,'"+ _name+"', '"+ _des+"', "+_count+", '"+_price+"', '"+_pic+"');";

        /*String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (3, 'AS', 32, 'California', 20000.00 );";*/
        Statement statement=session.con.createStatement();
        statement.executeUpdate(sql);

    }
    void makeOrder(String _email, String _pid, String _pic, String _name,String _des, String _price, int _count) throws SQLException {
        String uid= UUID.randomUUID().toString();
        String sql = "INSERT INTO ORDERS (email,uid,pid,name,des,count,price,pic) "
                + "VALUES ('"+_email+"', '"+uid+"','"+ _pid+"' ,'"+ _name+"','"+ _des+"', "+_count+", '"+_price+"', '"+_pic+"');";


        Statement statement=session.con.createStatement();
        statement.executeUpdate(sql);
    }


    void checkOut(String pid) throws SQLException {
        Statement stmt;
        stmt = session.con.createStatement();
        String sql = "DELETE from CART where email = '"+session.uid+"' and  pid = '"+pid+"' ;";
        stmt.executeUpdate(sql);

    }
}
