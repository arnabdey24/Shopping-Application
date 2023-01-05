package com.xyz;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageLabel extends ImageView {

    public ImageLabel(String location, double weight, double height) {
        System.out.println(location);
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
        setImage(image);
        setFitWidth(weight);
        setFitHeight(height);

    }
}

