package com.harshit.libgdx.invisibledeck;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlayingCard {
    Texture playingCardImage;
    Texture playingCardBack;
    Vector2 cardPosition;
    String suite;
    String cardValue;
    int intValue;
    boolean isSelection;
    Vector2 velocityVector=new Vector2(0,0);
    Vector2 defaultStart;
    Texture rectangle;
    boolean isRevealed=true;
    boolean isCardMoved=false;
    boolean isAnimationFalse;
    int animationFrameCount=0;
    private final int maxAnimationFrameCount=30;
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
                    this.suite=(value%13==0)?"Spades":"Diamonds";
                    break;
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
        this.cardPosition=new Vector2((InvisibleDeck.WORLD_WIDTH/2f)-(this.playingCardImage.getWidth()/2f),(InvisibleDeck.WORLD_HEIGHT/2f)-(this.playingCardImage.getHeight()/2f));
        this.defaultStart=new Vector2((InvisibleDeck.WORLD_WIDTH/2f)-(this.playingCardImage.getWidth()/2f),(InvisibleDeck.WORLD_HEIGHT/2f)-(this.playingCardImage.getHeight()/2f));
//        this.cardPosition
//        this.cardPosition.y=;
        createTexture(playingCardImage.getWidth(),playingCardImage.getHeight(),Color.BLACK);
    }
    boolean markSelection(int selectionInt){

        if(this.intValue==selectionInt)
        {
            isSelection=true;
            playingCardBack=new Texture("Backs/back-side-1.png");
            isRevealed=false;
            return true;
        }
        else{
            return  false;
        }

    }
    void update(){
        this.cardPosition.x+=this.velocityVector.x;
        this.cardPosition.y+=this.velocityVector.y;
        if(this.velocityVector.x>0){
            this.velocityVector.x-=InvisibleDeck.stopForce;
            if(this.velocityVector.x<0){
                this.velocityVector.x=0;
            }
        }
        if(this.velocityVector.y>0){
            this.velocityVector.y-=InvisibleDeck.stopForce;
            if(this.velocityVector.y<0){
                this.velocityVector.y=0;
            }
        }
        if(this.velocityVector.x<0){
            this.velocityVector.x+=InvisibleDeck.stopForce;
            if(this.velocityVector.x>0){
                this.velocityVector.x=0;
            }
        }
        if(this.velocityVector.y<0){
            this.velocityVector.y+=InvisibleDeck.stopForce;
            if(this.velocityVector.y>0){
                this.velocityVector.y=0;
            }
        }
    }
    boolean findTopCard(float x, float y){
        if(!isCardOnScreen()
                ){
            System.out.println(this.cardValue+" of "+this.suite+" was not on screen");
            return false;
        }
        float finalY=InvisibleDeck.WORLD_HEIGHT-y;
        if(
                x>this.cardPosition.x&&x<(this.cardPosition.x+this.playingCardImage.getWidth())
                        &&finalY>this.cardPosition.y&&finalY<(this.cardPosition.y+this.playingCardImage.getHeight())
                )
        {return true;}

        return false;
    }
    boolean moveCard(float x, float y, float deltaX, float deltaY){
        if(!isCardOnScreen()
                ){
            return false;
        }
        if(isRevealed) {
            System.out.println(this.cardValue + " of " + this.suite + "   card position  (" + this.cardPosition.x + ":" + this.cardPosition.y + ","
                    + (this.cardPosition.x + this.playingCardImage.getWidth()) + ":" + (this.cardPosition.y + this.playingCardImage.getHeight()) + ")");
            System.out.println(" touch point  " + x + ":" + y);
            System.out.println(" touch delta  " + deltaX + ":" + deltaY);


            System.out.println();
            this.velocityVector.x += ((int) deltaX);
            this.velocityVector.y += (-(int) deltaY);
            isCardMoved=true;
            return true;
        }
        else{
            return true;
        }

    }
    int draw(SpriteBatch b){
//        b.begin();
        if(
                isCardOnScreen()
                ) {
            if (isSelection && !isRevealed) {
                b.draw(this.playingCardBack, this.cardPosition.x, this.cardPosition.y);
                System.out.println(this.cardValue + " of " + this.suite + " was drawn card in center count ");
            }
            else{
                b.draw(this.playingCardImage, this.cardPosition.x, this.cardPosition.y);
                System.out.println(this.cardValue + " of " + this.suite + " back was drawn ");
            }
        }

//        if(this.cardPosition.x==this.defaultStart.x && this.cardPosition.y==this.defaultStart.y){
//            System.out.println(this.cardValue+" of "+this.suite+" is in center "+cardInCenter);
//            cardInCenter=cardInCenter+1;
//        }
//        b.draw(this.rectangle,this.cardPosition.x,this.cardPosition.y);
//        b.end();
        return 0;
    }
    void dispose(){
        playingCardImage.dispose();
    }
    private void createTexture(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.drawRectangle(0, 0, width, height);
        rectangle = new Texture(pixmap);
        pixmap.dispose();
    }
    boolean isCardOnScreen(){
        return        this.cardPosition.x<InvisibleDeck.WORLD_WIDTH+1
                && this.cardPosition.x>0-this.playingCardImage.getWidth()-1
                && this.cardPosition.y<InvisibleDeck.WORLD_HEIGHT+1
                &&  this.cardPosition.y>0-this.playingCardImage.getHeight()-1;

    }
    boolean isCardInStartPosition(){
        return (this.cardPosition.x==this.defaultStart.x && this.cardPosition.y==this.defaultStart.y);
    }
    void revealCard(){
        isRevealed=true;
//        return true;
    }
    boolean isSelect(){
        return this.isSelection;
    }
    void loadAnimationSequece(){

    }
    void animateCard(){

    }

}
