package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MenuScreen implements Screen {

    private Runner runner;
    private SpriteBatch batch;
    private Texture texture;

    private BitmapFont font96;
    private BitmapFont font48;

    private float time;

    public MenuScreen(Runner runner, SpriteBatch batch) {
        this.runner = runner;
        this.batch = batch;
    }

    @Override
    public void show() {
        texture = new Texture("bg.jpg");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        font48 = generator.generateFont(parameter);
        parameter.size = 96;
        font96 = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void render(float delta) {
        update(delta);
        batch.begin();
        batch.draw(texture, 0, 0);
        font96.draw(batch, "PUNK - RUNNER", 0, 600, 1334, 1, false);
        font48.setColor(1, 1, 1, 0.5f + 0.5f * (float) Math.sin(time * 5.0f));
        font48.draw(batch, "Tap to START", 0, 400, 1334, 1, false);
        font48.setColor(1, 1, 1, 1);
        if (Gdx.input.justTouched()) {
            runner.switchScreen(Runner.Screens.GAME);
        }
        batch.end();
    }

    public void update(float dt) {
        time += dt;
    }

    @Override
    public void resize(int width, int height) {
        runner.getViewport().update(width, height, true);
        runner.getViewport().apply();
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
        texture.dispose();
        font96.dispose();
        font48.dispose();
    }
}
