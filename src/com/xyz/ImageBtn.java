package com.xyz;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageBtn extends Button {

    String location;
    double weight,height;

    public void setLocation(String location) {
        this.location = location;
    }

    ImageView imageView=new ImageView();
    public ImageBtn(String location, double weight, double height) {
        this.location=location;
        this.height=height;
        this.weight=weight;
        Image image;
        FileInputStream input=null;
        try {
            input = new FileInputStream(location);
            image = new Image(input);
        }catch (Exception e){
            System.out.println(e);
            try {
                input = new FileInputStream("resources/images/no_image.png");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            image = new Image(input);
        }
        imageView.setImage(image);
        imageView.setFitWidth(weight);
        imageView.setFitHeight(height);
        setGraphic(imageView);
    }

    public void changeImage(String loc){
        setLocation(loc);
        Image image;
        FileInputStream input=null;
        try {
            input = new FileInputStream(location);
            image = new Image(input);
        }catch (Exception e){
            System.out.println(e);
            try {
                input = new FileInputStream("resources/images/no_image.png");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            image = new Image(input);
        }
        imageView.setImage(image);
        imageView.setFitWidth(weight);
        imageView.setFitHeight(height);
        setGraphic(imageView);
    }
}
