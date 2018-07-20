package com.harshit.libgdx.invisibledeck.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.harshit.libgdx.invisibledeck.InvisibleDeck;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=480;
		config.height=800;
		config.useGL30=true;
		config.title="InvisibleDeck";
		new LwjglApplication(new InvisibleDeck(), config);
	}
}
