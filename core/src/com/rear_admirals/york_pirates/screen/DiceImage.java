package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.rear_admirals.york_pirates.base.BaseActor;

public class DiceImage extends BaseActor {
    private float size;
    private Texture texture;

    public DiceImage(String diceFile, float size){
        this.texture = new Texture(diceFile);
        this.size = size;
        this.setBounds(getX(), getY(), size,size);
    }

        @Override
    public void draw(Batch batch, float alpha){
        batch.setColor(1,1,1, alpha);
        batch.draw(texture, getX(), getY(), size,size);
    }

    public void setTexture(String filepath){
        this.texture = new Texture(filepath);
    }

}

