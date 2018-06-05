package com.harshit.libgdx.invisibledeck;

import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Constants {
    public static final Rectangle value1=new Rectangle(35,494,71,134);
    public static final Rectangle value2=new Rectangle(144,540,70,70);
    public static final Rectangle value3=new Rectangle(235,462,101,119);
    public static final Rectangle value4=new Rectangle(235,385,52,50);
    public static final Rectangle value5=new Rectangle(231,237,67,87);
    public static final Rectangle value6=new Rectangle(143,188,70,84);
    public static final Rectangle value7=new Rectangle(366,165,94,152);

    public static final int SELECTION_PADDING=30;


    public static int getRandomPosition() {

//        if (min >= max) {
//            throw new IllegalArgumentException("max must be greater than min");
//        }

        Random r = new Random();
        int val=r.nextInt((17 - 10) + 1) + 10;
        System.out.println(val);
        return val;
    }

}
