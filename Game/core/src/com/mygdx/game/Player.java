package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

    private GameScreen gameScreen;
    private Texture texture;

    private Vector2 position;
    private Vector2 velocity;

    private Rectangle rectangle;
    private float score;

    private final int WIDTH = 100;
    private final int HEIGHT = 100;

    private float time;

    public Player(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.texture = new Texture("player.png");
        this.position = new Vector2(0, 190);
        this.velocity = new Vector2(240.0f, 0.0f);
        this.rectangle = new Rectangle(position.x + WIDTH / 6, position.y, WIDTH / 4, HEIGHT);
        this.score = 0;
    }

    public void restart() {
        position.set(0, gameScreen.getGroundHeight());
        score = 0;
        velocity.set(240.0f, 0.0f);
        rectangle.setPosition(position);
    }

    public void render(SpriteBatch batch) {
        int frame = (int) (time / 0.1f);
        frame = frame % 6;
        batch.draw(texture, gameScreen.getPlayerAnchor(), position.y, frame * 100, 0, WIDTH, HEIGHT);
    }

    public void update(float dt) {
        if (position.y > gameScreen.getGroundHeight()) {
            velocity.y -= 720.0f * dt;
        } else {
            position.y = gameScreen.getGroundHeight();
            velocity.y = 0.0f;
            time += velocity.x * dt / 300.0f;
            if (Gdx.input.justTouched()) {
                velocity.y = 420.0f;
            }
        }
        position.mulAdd(velocity, dt);
        velocity.x += 5.0f * dt;
        score += velocity.x * dt / 5.0f;
        rectangle.setPosition(position.x + WIDTH / 6, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public float getScore() {
        return score;
    }
}
