package com.example.prac_03;

import java.util.ArrayList;

public class Model {
    private String name;

    public Model(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    private static int lastModelId = 0;
    public static ArrayList<Model> createModelArrayList (int numName){
        ArrayList<Model> models = new ArrayList<Model>();
        for(int i=1; i<=numName; i++){
            models.add(new Model("Name" + ++lastModelId));
        }
        return models;
    }
}
