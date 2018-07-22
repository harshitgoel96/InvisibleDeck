package com.harshit.libgdx.invisibledeck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
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

import static com.harshit.libgdx.invisibledeck.Constants.WORLD_HEIGHT;
import static com.harshit.libgdx.invisibledeck.Constants.WORLD_WIDTH;
import static com.harshit.libgdx.invisibledeck.Constants.scaleX;
import static com.harshit.libgdx.invisibledeck.Constants.scaleY;

public class InvisibleDeck extends Game {
	SpriteBatch batch;

	OrthographicCamera cam;

	@Override
	public void create () {
		batch = new SpriteBatch();

		scaleX = WORLD_WIDTH/Gdx.graphics.getWidth();
		scaleY = WORLD_HEIGHT/Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
        this.setScreen(new InvisibleDeckScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
        batch.dispose();

	}



}
