package com.harshit.libgdx.invisibledeck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InvisibleDeck extends ApplicationAdapter implements GestureDetector.GestureListener {
	SpriteBatch batch;
//	Texture img;
	static final float WORLD_WIDTH = 480f;
	static final float WORLD_HEIGHT = 800f;
	static final int NUMBER_CARDS_IN_DECK=52;
	float scaleX=1f;
	float scaleY=1f;
	float imagePosX=0f;
	float imagePosY=0f;
	private OrthographicCamera cam;
	GestureDetector gestureDetector;
	static final float stopForce=15f;
	List<PlayingCard> deckOfCards=new ArrayList<PlayingCard>();
	int lockCardPosition=-1;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();

		scaleX = WORLD_WIDTH/Gdx.graphics.getWidth();
		scaleY = WORLD_HEIGHT/Gdx.graphics.getHeight();
//		imagePosX=(WORLD_WIDTH/2f)-(img.getWidth()/2f);
//		imagePosY=(WORLD_HEIGHT/2f)-(img.getHeight()/2f)	;
		cam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		gestureDetector = new GestureDetector(this);
		Gdx.input.setInputProcessor(gestureDetector);
		for(int i=1;i<=NUMBER_CARDS_IN_DECK;i++){
			deckOfCards.add(new PlayingCard(i));
		}
	}

	@Override
	public void render () {

//		for(PlayingCard p : deckOfCards){
//			p.update();
//		}
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		int cardInCenter=0;
		int topCenter=0;
		boolean isTopCenter=false;
		while(!isTopCenter&&topCenter<52){
			isTopCenter=deckOfCards.get(topCenter).isCardInStartPosition();
			if(!isTopCenter){
				topCenter++;
			}
		}
		if(topCenter+3>=52){
		    topCenter=51-3;
        }
		for(int i=topCenter+3;i>=0;i--){
			//deckOfCards.get(i).update();
            deckOfCards.get(i).update();
			deckOfCards.get(i).draw(batch);

		}
		//batch.draw(img,imagePosX,imagePosY );
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for(PlayingCard p : deckOfCards){
			p.dispose();
		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		System.out.println("====================");
		System.out.println(x*scaleX);

		float finalY=InvisibleDeck.WORLD_HEIGHT-(y*scaleY);
		System.out.println(finalY);
		System.out.println("====================");
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		//Vector3 touchPos = new Vector3(x,y,0);
		//cam.unproject(touchPos);
		float finalVelocityX=deltaX*scaleX;
		float finalVelocityY=deltaY*scaleY;
		float finalTouchX=x*scaleX;
		float finalTouchY=y*scaleY;

		boolean isCardMoved=false;
		int i=0;
		while(!isCardMoved &&i<deckOfCards.size() &&lockCardPosition==-1){
			isCardMoved=deckOfCards.get(i).findTopCard(finalTouchX,finalTouchY,finalVelocityX,finalVelocityY);
			if(!isCardMoved)
			{
				++i;
			}
			System.out.println("size:: "+deckOfCards.size());
			System.out.println("i:: "+i);

		}
		if(isCardMoved){
			lockCardPosition=i;
		}
		if(lockCardPosition>-1){
			deckOfCards.get(lockCardPosition).moveCard(finalTouchX,finalTouchY,finalVelocityX,finalVelocityY);
		}
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		imagePosX+=(0);
		imagePosY+=(0);
		System.out.println("PAN STOP");
		lockCardPosition=-1;
		return true;
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
}
