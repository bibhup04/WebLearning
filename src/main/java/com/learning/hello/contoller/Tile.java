package com.learning.hello.contoller;

import java.awt.Color;

public class Tile {
    private int value;
    private String tileColor;

    public Tile() {
        value = 0; //default 
    }


    public Tile(int number) {
        value = number;
    }

    public int getValue() {
        return value;
    }


    public void setValue(int value) {
        this.value = value;
    }


    public String toString() {
        return "" + value;
    }


    public void setColor() {
        if (this.getValue() == 1) {
            tileColor = "#EEE4DA";
        } else if (this.getValue() == 2) {
            tileColor = "#EDE0C8";
        } else if (this.getValue() == 3) {
            tileColor = "#F2B179";
        } else if (this.getValue() == 5) {
            tileColor = "#F59563";
        } else if (this.getValue() == 8) {
            tileColor = "#F67C5F";
        } else if (this.getValue() == 13) {
            tileColor = "#F65E3B";
        } else if (this.getValue() == 21) {
            tileColor = "#EDCF72";
        } else if (this.getValue() == 34) {
            tileColor = "#EDCC61";
        } else if (this.getValue() == 55) {
            tileColor = "#EDC850";
        } else if (this.getValue() == 89) {
            tileColor = "#EDC53F";
        } else {
            tileColor = "#EDC22E";
        }
    }
    public String getColor() {
        this.setColor();
        return tileColor;
    }
}
