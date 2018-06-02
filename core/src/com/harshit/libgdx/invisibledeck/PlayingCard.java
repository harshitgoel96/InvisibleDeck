package com.harshit.libgdx.invisibledeck;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlayingCard {
    Texture playingCardImage;
    Vector2 cardPosition;
    String suite;
    String cardValue;
    int intValue;
    boolean isSelection;
    int velocityX=0;
    int velocityY=0;

    PlayingCard(int value){
        this.intValue=value;
        if(value<=52&&value>0)
        {
            switch (value%13){
                case 1:
                    this.cardValue="Ace";
                    break;
                case 11:
                    this.cardValue="Jack";
                    break;
                case 12:
                    this.cardValue="Queen";
                    break;
                case 0:
                    this.cardValue="King";
                    break;
                default:
                    this.cardValue=String.valueOf(value%13);

            }
            switch (value/13){
                case 0:
                    //value 1 to 12
                    this.suite="Clubs";
                    break;

                case 1:
                    //value 13 to 25
                    this.suite=(value%13==0)?"Clubs":"Hearts";
                    break;
                case 2:
                    //value 26 to 38
                    this.suite=(value%13==0)?"Hearts":"Spades";
                    break;

                case 3:
                case 4:
                    //value 39 to 52
                    this.suite="Diamonds";
                    break;
            }
        }
        if(value==0){
            this.cardValue="Joker";
            this.suite="Jokers";

        }
        if(value==-1){
            this.suite="Backs";
            this.cardValue="back-side-1";
        }
        this.playingCardImage=new Texture(suite+"/"+cardValue+".png");
        this.cardPosition=new Vector2();
        this.cardPosition.x=(InvisibleDeck.WORLD_WIDTH/2f)-(this.playingCardImage.getWidth()/2f);
        this.cardPosition.y=(InvisibleDeck.WORLD_HEIGHT/2f)-(this.playingCardImage.getHeight()/2f);

    }
    void markSelection(){
            isSelection=true;

    }
    void update(){
        /*if(this.velocityX>0){
            this.velocityX-=InvisibleDeck.stopForce;
            if(this.velocityX<0){
                this.velocityX=0;
            }

        }
        if(this.velocityX<0){
            this.velocityX+=InvisibleDeck.stopForce;
            if(this.velocityX>0){
                this.velocityX=0;
            }
        }
        if(this.velocityY>0){
            this.velocityY-=InvisibleDeck.stopForce;
            if(this.velocityY<0){
                this.velocityY=0;
            }

        }
        if(this.velocityY<0){
            this.velocityY+=InvisibleDeck.stopForce;
            if(this.velocityY>0){
                this.velocityY=0;
            }
        }
        this.cardPosition.x+=this.velocityX;
        this.cardPosition.y=this.velocityY;*/
    }
    boolean findTopCard(float x, float y, float deltaX, float deltaY){
        if(
                x>this.cardPosition.x&&x<(this.cardPosition.x+this.playingCardImage.getHeight())
                        &&y>this.cardPosition.y&&y<(this.cardPosition.y+this.playingCardImage.getWidth())
                )
        {return true;}

        return false;
    }
    boolean moveCard(float x, float y, float deltaX, float deltaY){

        System.out.println(" card position  "+this.cardPosition);
        System.out.println(" touch point  "+x+":"+y);
        System.out.println(" touch delta  "+deltaX+":"+deltaY);


            System.out.println();
            this.cardPosition.x+=((int)deltaX);
            this.cardPosition.y+=(-(int)deltaY);
            return true;


    }
    void draw(SpriteBatch b){
        b.draw(this.playingCardImage,this.cardPosition.x,this.cardPosition.y);
    }
    void dispose(){
        playingCardImage.dispose();
    }

}