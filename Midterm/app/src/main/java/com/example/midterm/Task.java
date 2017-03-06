package com.example.midterm;

/**
 * Created by panda_000 on 3/5/2017.
 */

public class Task {
    private String s;
    private String s2;
    private int i;
    private boolean b;

    public Task(String s, String b, int a){
        this.s=s;
        s2=b;
        i=a;
        this.b=false;
    }

    public String getS1(){return s;}
    public String getS2(){return s2;}
    public int getI(){return i;}
    public boolean isB(){return b;}

    public void toggle(){b=!b;}
}
