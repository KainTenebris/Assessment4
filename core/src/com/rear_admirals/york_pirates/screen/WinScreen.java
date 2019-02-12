package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.rear_admirals.york_pirates.base.BaseActor;
import com.rear_admirals.york_pirates.*;
import com.rear_admirals.york_pirates.base.BaseScreen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class WinScreen extends BaseScreen {
    private Texture texture;
    private Image win;
    private Player player;

    //Displays Win Screen
    public WinScreen(final PirateGame main){
        super(main);
        player = main.getPlayer();

//        batch = new SpriteBatch();
        this.texture = new Texture("win.png");
        win = new Image(texture);
        win.setSize(viewwidth, viewheight);
        mainStage.addActor(win);

        Table pointsTable = new Table();
        Label pointsLabel = new Label("Your points were ",main.getSkin());
        Label numPointsLabel = new Label(Integer.toString(this.player.getPoints()), main.getSkin());
        pointsTable.add(pointsLabel);
        pointsTable.add(numPointsLabel).width(pointsLabel.getWidth());
        pointsTable.align(Align.topLeft);
        pointsTable.setFillParent(true);
        mainStage.addActor(pointsTable);

        Gdx.input.setInputProcessor(mainStage);

    }

    //Quits game is ESC is pressed
    @Override
    public void update(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

    }


}
