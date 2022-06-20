package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Runner extends Game {

	public enum Screens{
		MENU,GAME
	}
	private SpriteBatch batch;
	private GameScreen gameScreen;
	private MenuScreen menuScreen;
	private Viewport viewport;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this,batch);
		menuScreen = new MenuScreen(this,batch);
		viewport = new FitViewport(1334,750);
		switchScreen(Screens.MENU);
	}

	public void switchScreen(Screens type){
		Screen currentScreen = getScreen();
		if (currentScreen != null){
			currentScreen.dispose();
		}switch (type){
			case MENU:
				setScreen(menuScreen);
				break;
			case GAME:
				setScreen(gameScreen);
				break;
		}
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		getScreen().render(dt);
	}

	@Override
	public void dispose () {
		batch.dispose();
		getScreen().dispose();
	}

	public Viewport getViewport() {
		return viewport;
	}
}
