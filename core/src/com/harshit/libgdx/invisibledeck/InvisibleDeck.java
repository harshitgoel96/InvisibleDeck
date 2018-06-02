package com.harshit.libgdx.invisibledeck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InvisibleDeck extends ApplicationAdapter implements GestureDetector.GestureListener {
	SpriteBatch batch;
	Texture img;
	static final float WORLD_WIDTH = 480f;
	static final float WORLD_HEIGHT = 800f;
	float scaleX=1f;
	float scaleY=1f;
	float imagePosX=0f;
	float imagePosY=0f;
	private OrthographicCamera cam;
	GestureDetector gestureDetector;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("Clubs/Queen.png");
		scaleX = WORLD_WIDTH/Gdx.graphics.getWidth();
		scaleY = WORLD_HEIGHT/Gdx.graphics.getHeight();
		imagePosX=(WORLD_WIDTH/2f)-(img.getWidth()/2f);
		imagePosY=(WORLD_HEIGHT/2f)-(img.getHeight()/2f)	;
		cam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		gestureDetector = new GestureDetector(this);
		Gdx.input.setInputProcessor(gestureDetector);
	}

	@Override
	public void render () {

		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img,imagePosX,imagePosY );
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		System.out.println("====================");
		System.out.println(x*scaleX);
		System.out.println(y*scaleY);
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
		System.out.println("====================");
		System.out.println("deltaX  :: "+(deltaX*scaleX));
		System.out.println("deltaY  :: "+(-deltaY*scaleY));
		System.out.println("====================");
		imagePosX+=(deltaX*scaleX);
		imagePosY+=(-deltaY*scaleX);

		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		imagePosX+=(0);
		imagePosY+=(0);
		System.out.println("PAN STOP");
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
