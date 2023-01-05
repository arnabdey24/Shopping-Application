package com.xyz;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class BlackBtn extends Label {
    String text;
    double weight;
    double height;
    Color color;
    public BlackBtn(String text,double weight,double height, Color color) {
        super(text);
        this.text=text;
        this.color=color;
        this.height=height;
        this.weight=weight;


        setTextFill(Color.WHITE);
        setPrefSize(weight,height);
        setPadding(new Insets(20));
        CornerRadii corn = new CornerRadii(10);
        Background background = new Background(new BackgroundFill(color, corn, Insets.EMPTY));
        setBackground(background);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
