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

    //Displays Win Screen or Lose Screen
    public WinScreen(final PirateGame main, boolean won){
        super(main);
        player = main.getPlayer();
        if (won == true){
            win(main, player);
        } else {
            lose(main, player);
        }


    }

    //If you lose the game
    public void lose(final PirateGame main, Player player){
        this.texture = new Texture("lose.png");
        win = new Image(texture);
        win.setSize(viewwidth, viewheight);
        mainStage.addActor(win);

        Gdx.input.setInputProcessor(mainStage);
    }

    //If you win the game
    public void win(final PirateGame main, Player player){

        this.texture = new Texture("Win.png");
        win = new Image(texture);
        win.setSize(viewwidth, viewheight);
        mainStage.addActor(win);

        //Displays points obtained
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
