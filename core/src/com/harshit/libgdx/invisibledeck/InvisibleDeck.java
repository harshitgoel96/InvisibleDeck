package com.harshit.libgdx.invisibledeck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
	static final float stopForce=0.99f;
	List<PlayingCard> deckOfCards=new ArrayList<PlayingCard>();
	int lockCardPosition=-1;
	private ShapeRenderer shapeRenderer;
	boolean isCardSelectionMade=false;
	int swipeDirection=-1; //0-3  *13 to get suite
	int selectionNumber=-1;//1-7 + i to get value
	int swipeWhilePanning=-1;
	int cardSelectedValue=-1;
	boolean isSelectionNotified=false;
	float lockedCardVelocityX=0;
	float lockedCardVelocityY=0;
	Sound swipeSound;
	Sound turnSound;
	Texture aceBox;
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
		Collections.shuffle(deckOfCards);
		deckOfCards.add(0, new PlayingCard(0));
		deckOfCards.add(0, new PlayingCard(0));
		createTexture((int)Constants.value1.getWidth(),(int)Constants.value1.getHeight(),Color.BLACK);
		swipeSound=Gdx.audio.newSound(Gdx.files.internal("sounds/swipe.ogg"));
		turnSound=Gdx.audio.newSound(Gdx.files.internal("sounds/turn.ogg"));
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
//		batch.draw(aceBox,Constants.value1.x,Constants.value1.y);
		//batch.draw(img,imagePosX,imagePosY );
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for(PlayingCard p : deckOfCards){
			p.dispose();
		}
		swipeSound.dispose();
		turnSound.dispose();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if(count==1) {
//			System.out.println("====================");
//			System.out.println(x * scaleX);

			float finalTouchX = x * scaleX;
			float finalTouchY = InvisibleDeck.WORLD_HEIGHT - (y * scaleY);
//			System.out.println(finalTouchY);
//			System.out.println("====================");
			boolean isCardMoved = false;
			int i = 0;
			while (!isCardMoved && i < deckOfCards.size() && lockCardPosition == -1) {
				isCardMoved = deckOfCards.get(i).findTopCard(finalTouchX, finalTouchY);
				if (!isCardMoved) {
					++i;
				}else{

				}
//				System.out.println("size:: " + deckOfCards.size());
//				System.out.println("i:: " + i);

			}
			if (i < deckOfCards.size()&&deckOfCards.get(i).isSelection && !deckOfCards.get(i).isRevealed) {
				deckOfCards.get(i).revealCard();
				turnSound.play(1.0f);
			}

			return true;
		}
		return false;
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
		//static selection of 10 of C just to testing purpose, refactoring required once logic is decided
//		if(!isCardSelectionMade){
//
//
//			// fixed the selection to 10 of C for testing purpose, logic on selection yet to be decided.
//			deckOfCards.get(9).markSelection(10);
//			Collections.shuffle(deckOfCards);
//			isCardSelectionMade=true;
//		}
		float finalVelocityX=deltaX*scaleX;
		float finalVelocityY=deltaY*scaleY;
		float finalTouchX=x*scaleX;
		float finalTouchY=y*scaleY;

		boolean isCardMoved=false;
		int i=0;
		while(!isCardMoved &&i<deckOfCards.size() &&lockCardPosition==-1){
			isCardMoved=deckOfCards.get(i).findTopCard(finalTouchX,finalTouchY);
			if(!isCardMoved)
			{
				++i;
			}
//			System.out.println("size:: "+deckOfCards.size());
//			System.out.println("i:: "+i);

		}
		if(isCardMoved){
			lockCardPosition=i;
			swipeWhilePanning=getDirectionOfSwipe(finalVelocityX,finalVelocityY);
			lockedCardVelocityX=finalVelocityX;
			lockedCardVelocityY=finalVelocityY;
			if(i<2 && cardSelectedValue==-1){
				selectionNumber=getSelectionValue(finalTouchX,WORLD_HEIGHT-finalTouchY);
//				System.out.print("selection is :: "+selectionNumber);
			}

		}
		if(lockCardPosition>-1){
			deckOfCards.get(lockCardPosition).moveCard(finalTouchX,finalTouchY,finalVelocityX,finalVelocityY);
			swipeSound.play(1.0f);
		}
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		imagePosX+=(0);
		imagePosY+=(0);
		System.out.println("PAN STOP");

		swipeDirection=swipeWhilePanning;
		System.out.print("final swipe direction :: "+swipeDirection + ": "+selectionNumber);
//		System.out.print("final selection :: "+swipeDirection);

		if(selectionNumber!=-1&&cardSelectedValue==-1){
			if(lockCardPosition==2&&selectionNumber==7){
				cardSelectedValue=0;
			}
			else{
				cardSelectedValue=((13*swipeDirection)+(selectionNumber+lockCardPosition));
				System.out.println("selection is :: "+cardSelectedValue);
				isCardSelectionMade=true;
				for(int i=2;i<deckOfCards.size()&&!isSelectionNotified;i++){
					isSelectionNotified=deckOfCards.get(i).markSelection(cardSelectedValue);

					if(isSelectionNotified){
						System.out.print("swappintg between  "+i+" and ");
					Collections.swap(deckOfCards,i,Constants.getRandomPosition());}
				}

			}
		}
		if(lockCardPosition!=-1){
			deckOfCards.get(lockCardPosition).setCardVelocity(lockedCardVelocityX,lockedCardVelocityY);
		}
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

	public int getDirectionOfSwipe(float deltaX, float deltaY){
//		System.out.println("swipe  "+deltaX+":"+deltaY);
		if(Math.abs(deltaX)>Math.abs(deltaY)){
			if(deltaX>0){
//				System.out.println("Swipe right");
				return 1;
//				onRight();
			}else{
//				System.out.println("Swipe left");
				return 3;
			}
		}else{
			if(deltaY>0){
//				System.out.println("Swipe down");
				return 2;
			}else{
//				System.out.println("Swipe up");
				return 0;
			}
		}
		//return -1;
	}

	public int getSelectionValue(float x,float y){
		System.out.println("Coordinates for selection :: "+x+":"+y);
		if(
				x>Constants.value1.x&&x<(Constants.value1.x+Constants.value1.width)
				&& y>Constants.value1.y && y<(Constants.value1.y+Constants.value1.height)
				){
			System.out.println("selected 1");
			return 1;}
		if(Constants.value2.contains(x,y)){
			System.out.println("selected 2");
			return 3;}
		if(Constants.value3.contains(x,y)){
			System.out.println("selected 3");
		return 5;}
		if(Constants.value4.contains(x,y)){
			System.out.println("selected 4");
		return 7;}
		if(Constants.value5.contains(x,y)){
			System.out.println("selected 5");
		return 9;}
		if(Constants.value6.contains(x,y)){
			System.out.println("selected 6");
			return 11;}
		if(Constants.value7.contains(x,y)){
			System.out.println("selected 7");
		return 13;}

		return -1;
	}
	private void createTexture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.drawRectangle(0, 0, width, height);
		aceBox = new Texture(pixmap);
		pixmap.dispose();
	}
}
