package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rear_admirals.york_pirates.base.BaseActor;

//New for Assessment 4:
public class Obstacle extends BaseActor {
    private int x;
    private int y;
    private Texture texture;

    public Obstacle(int x, int y, Texture texture) {
        super();
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.setWidth(texture.getWidth());
        this.setHeight(texture.getHeight());
        this.setPosition(x, y);
        this.setOriginCentre();
        this.setEllipseBoundary();
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.setColor(1,1,1,alpha);
        batch.draw(new TextureRegion(texture),getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,getRotation());
    }
}
