package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Let {

    private Texture texture;
    private Vector2 position;
    private Rectangle rectangle;

    private final int WIDTH = 80;
    private final int HEIGHT = 80;

    public Let(Texture texture, Vector2 position) {
        this.texture = texture;
        this.position = position;
        this.rectangle = new Rectangle(position.x, position.y, WIDTH, HEIGHT);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        rectangle.setPosition(position);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void render(SpriteBatch batch, float worldX) {
        batch.draw(texture, position.x - worldX, position.y);
    }
}
