package com.harshit.libgdx.invisibledeck;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.harshit.libgdx.invisibledeck.Constants.WORLD_HEIGHT;
import static com.harshit.libgdx.invisibledeck.Constants.scaleX;
import static com.harshit.libgdx.invisibledeck.Constants.scaleY;
import static com.harshit.libgdx.invisibledeck.Constants.soundDefault;
import static com.harshit.libgdx.invisibledeck.Constants.vibrationDefault;

public class MenuScreen  implements Screen, GestureDetector.GestureListener,Input.TextInputListener {

    int vibrationDuratoin;
    //vibration text w:450 x h:250
    //0,182
    //h:237-182,480
    Rectangle  vibrationHitbox;
    ShapeRenderer sr;
    Vector2 vibrationTextPos;
    Vector2 soundTextPos;
    boolean soundEnable;
    Rectangle  soundHitbox;
    //vibration text w:263 w:423
    // 0,358
    //409-358,480
    InvisibleDeck gameInstance;
    Texture backgroundTexture;
    BitmapFont font;
    GestureDetector gestureDetector;
    Preferences prefs;
   public MenuScreen(InvisibleDeck instanceOfGame){
       prefs = Gdx.app.getPreferences("InvisibleDeck");
       backgroundTexture=new Texture("GameMenu.png");
       this.gameInstance=instanceOfGame;
       this.font = new BitmapFont();
       vibrationTextPos=new Vector2(385,609);
       soundTextPos=new Vector2(180,434);
       gestureDetector = new GestureDetector(this);
       Gdx.input.setInputProcessor(gestureDetector);
       vibrationHitbox=new Rectangle(0,(800-282), 480,100);
       soundHitbox=new Rectangle(0,(800-458),480,100);
       sr=new ShapeRenderer();

   }
    @Override
    public void show() {

        soundEnable=prefs.getBoolean("sound",soundDefault);
        vibrationDuratoin=prefs.getInteger("vibration",vibrationDefault);
    }

    @Override
    public void render(float delta) {
        gameInstance.batch.setProjectionMatrix(gameInstance.cam.combined);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameInstance.batch.begin();
        gameInstance.batch.draw(backgroundTexture,0,0);
        font.setColor(Color.PINK);
        font.getData().setScale(3.0f);
        font.draw(gameInstance.batch, String.valueOf(vibrationDuratoin),vibrationTextPos.x,vibrationTextPos.y);
        font.draw(gameInstance.batch,(soundEnable)?"On":"Off",soundTextPos.x,soundTextPos.y);

        gameInstance.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        font.dispose();
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        float finalTouchX = x * scaleX;
        float finalTouchY = WORLD_HEIGHT - (y * scaleY);
       // gameInstance.cam.unproject(tmp);
       // Rectangle textureBounds=new Rectangle(textureX,textureY,textureWidth,textureHeight);
        // texture x is the x position of the texture
        // texture y is the y position of the texture
        // texturewidth is the width of the texture (you can get it with texture.getWidth() or textureRegion.getRegionWidth() if you have a texture region
        // textureheight is the height of the texture (you can get it with texture.getHeight() or textureRegion.getRegionhHeight() if you have a texture region
        if(soundHitbox.contains(finalTouchX,finalTouchY))
        {
           System.out.println("sound hit box");
           soundEnable=!soundEnable;
            prefs.putBoolean("sound",soundEnable);
            prefs.flush();
            // you are touching your texture
        }
        if(vibrationHitbox.contains(finalTouchX,finalTouchY)){
            System.out.println("vibration hit box");
            Gdx.input.getTextInput(this, "Enter vibration duration", String.valueOf(vibrationDuratoin), "Min: 20 , Max: 1000");
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        System.out.print("LONG PRESS IN MENU");
        gameInstance.setScreen(new InvisibleDeckScreen(gameInstance));
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public void input(String text) {
        try
        {
            // checking valid integer using parseInt() method
            int tempInt=Integer.parseInt(text);
            System.out.println(text + " is a valid integer number");
            if(tempInt>=20 && tempInt<=1000){
                vibrationDuratoin=tempInt;
                prefs.putInteger("vibration",vibrationDuratoin);
            }
            prefs.flush();
        }
        catch (NumberFormatException e)
        {
            System.out.println(text + " is not a valid integer number");
        }

    }

    @Override
    public void canceled() {

    }
}
