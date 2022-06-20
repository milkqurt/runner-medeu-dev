package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {

    private Runner runner;
    private SpriteBatch batch;

    private Texture textureBg;
    private Texture textureGr;
    private Texture letTexture;

    private float groundHeight = 190.0f;
    private float playerAnchor = 200.0f;

    private Player player;
    private Let[] enemies;
    private boolean gameover;

    private BitmapFont font48;
    private BitmapFont font96;

    private float time;
    private Music music;

    public GameScreen(Runner runner, SpriteBatch batch) {
        this.runner = runner;
        this.batch = batch;
    }

    @Override
    public void show() {
        textureBg = new Texture("bluemoon.png");
        textureGr = new Texture("ground1.png");
        player = new Player(this);
        letTexture = new Texture("cactus.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("random.mp3"));
        music.setLooping(true);
        music.play();
        enemies = new Let[10];
        enemies[0] = new Let(letTexture,new Vector2(1500,groundHeight));
        for (int i = 1; i < 10; i++) {
            enemies[i] = new Let(letTexture,new Vector2(enemies[i-1].getPosition().x + MathUtils.random(400,900),groundHeight));
        }
        gameover = false;
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
        HighScore.createTable();
        HighScore.loadTable();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(textureBg,0,0);
        for (int i = 0; i < 8; i++) {
            batch.draw(textureGr,i * 200 - player.getPosition().x % 200,0);
        }
        player.render(batch);
        for (int i = 0; i < enemies.length; i++) {
            enemies[i].render(batch,player.getPosition().x - playerAnchor);
        }
        font48.draw(batch,"TOP PLAYER: " + HighScore.topPlayerName + " - " + HighScore.topPlayerScore,22,718);
        font48.draw(batch,"SCORE: " + (int) player.getScore(),22,672);
        if (gameover){
            font96.draw(batch,"GAME OVER",380,402);
            font48.setColor(1,1,1,0.5f + 0.5f * (float)Math.sin(time * 5.0f));
            font48.draw(batch,"Tap to RESTART",470,302);
            font48.setColor(1,1,1,1);
        }
        batch.end();
    }

    public void restart(){
        gameover = false;
        enemies[0].setPosition(1500,groundHeight);
        for (int i = 1; i < 10; i++) {
            enemies[i].setPosition(enemies[i-1].getPosition().x + MathUtils.random(400,900),groundHeight);
        }
        player.restart();
    }

    public float getRightestEnemy(){
        float maxValue = 0.0f;
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].getPosition().x > maxValue){
                maxValue = enemies[i].getPosition().x;
            }
        }
        return maxValue;
    }

    public void update(float dt){
        time += dt;
        if (!gameover) {
            player.update(dt);
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].getPosition().x < player.getPosition().x - playerAnchor - 80) {
                    enemies[i].setPosition(getRightestEnemy() + MathUtils.random(400, 900), groundHeight);
                }
            }
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].getRectangle().overlaps(player.getRectangle())) {
                    gameover = true;
                    HighScore.updateTable("Player",(int)player.getScore());
                    break;
                }
            }
        }else {
            if (Gdx.input.justTouched()){
                restart();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        runner.getViewport().update(width,height,true);
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
        textureBg.dispose();
        textureGr.dispose();
        letTexture.dispose();
        font96.dispose();
        font48.dispose();
        music.dispose();
    }

    public float getPlayerAnchor() {
        return playerAnchor;
    }

    public float getGroundHeight() {
        return groundHeight;
    }
}
