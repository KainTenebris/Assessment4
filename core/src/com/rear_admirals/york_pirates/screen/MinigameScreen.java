package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.*;
import com.rear_admirals.york_pirates.base.BaseScreen;

public class MinigameScreen extends BaseScreen{

    private Label playerDiceLabel;
    private Label enemyDiceLabel;
    private Label gameStateLabel;


    public MinigameScreen(final PirateGame main){
        super(main);

        final Minigame ceelo = new Minigame();

        Table uiTable = new Table();

        Label playerIntroLabel = new Label("You:", main.getSkin());
        playerIntroLabel.setAlignment(Align.left);
        playerDiceLabel = new Label("Not yet rolled", main.getSkin());

        Label enemyIntroLabel = new Label("Enemy:", main.getSkin());
        enemyIntroLabel.setAlignment(Align.left);
        enemyDiceLabel = new Label("Not yet rolled", main.getSkin());

        gameStateLabel = new Label("No game yet", main.getSkin());

        uiTable.add(playerIntroLabel);
        uiTable.add(playerDiceLabel).width(playerIntroLabel.getWidth());
        uiTable.row();
        uiTable.add(enemyIntroLabel);
        uiTable.add(enemyDiceLabel).width(enemyIntroLabel.getWidth());
        uiTable.row();
        uiTable.add(gameStateLabel);
        uiTable.row();

        uiTable.setFillParent(true);
        uiTable.align(Align.topLeft);
        uiStage.addActor(uiTable);

        final TextButton rollDice  = new TextButton("Roll dice", main.getSkin());
        rollDice.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                String gameState = ceelo.playGame();
                int[] playerdice = ceelo.getPlayerdice();
                int[] enemydice = ceelo.getEnemydice();
                playerDiceLabel.setText(Integer.toString(playerdice[0]) + Integer.toString(playerdice[1]) + Integer.toString(playerdice[2]));
                enemyDiceLabel.setText(Integer.toString(enemydice[0]) + Integer.toString(enemydice[1]) + Integer.toString(enemydice[2]));
                gameStateLabel.setText(gameState);
            }
        });

        Table gameTable = new Table();
        gameTable.setFillParent(true);
        gameTable.add(rollDice).uniform().fill().padBottom(viewheight/40).size(viewwidth/2,viewheight/10);
        mainStage.addActor(gameTable);


        Gdx.input.setInputProcessor(mainStage);

    }

    @Override
    public void update(float delta) { }
}
