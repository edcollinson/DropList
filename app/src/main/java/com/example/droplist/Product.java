package com.example.droplist;

public class Product {

    private String EAN;
    private String name;
    private int quant;

    public Product(String mEAN, String mName, int mQuant){
        //Get info for instantiation
        EAN = mEAN;
        name = mName;
        quant = mQuant;
    }

    public int getQuant(){
        return quant;
    }

    public String getName(){
        return name;
    }

    public String getEAN(){
        return EAN;
    }
}
