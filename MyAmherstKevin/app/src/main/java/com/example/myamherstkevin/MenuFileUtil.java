package com.example.myamherstkevin;

//Received from Thyne Boonmark

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Thyne Boonmark on 2/12/17.
 */
public class MenuFileUtil {
    public String data;

    public MenuFileUtil(){
        data = "";
    }

    public void readFromFile(String date, String mealName) throws IOException {
        try {
            String meal = "";
            Document menu = Jsoup.connect("https://www.amherst.edu/campuslife/housing-dining/dining/menu").get();
            ArrayList<Element> mealType = menu.getElementsByAttributeValueContaining("class","dining-menu-menu-listing");
            for(int i = 0; i < mealType.size(); i++){
                if(mealType.get(i).id().equals("dining-menu-"+date+"-"+mealName+"-menu-listing")){
                    meal += mealType.get(i).text();
                }
            }
            this.setData(meal);
        }
        catch(java.io.IOException e){
            throw e;
        }
    }

    public void setData(String s){
        data = s;
    }
    public String getData(){
        return data;
    }

    public static void main(String[] args){

    }
}
