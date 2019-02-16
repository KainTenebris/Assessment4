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

    private Player player;
    private Label gameStateLabel;
    private Label currentGoldLabel;
    private DiceImage[] playerdiceimages;
    private DiceImage[] enemydiceimages;

    private int sellSoulStage;

//Initialises the minigame
    public MinigameScreen(final PirateGame main){
        super(main);
        player = main.getPlayer();
//        player.setGold(50);
        sellSoulStage = 0;

        final Minigame ceelo = new Minigame();

        playerdiceimages = new DiceImage[3];
        enemydiceimages = new DiceImage[3];
        for (int i = 0; i < 3; i++){
            playerdiceimages[i] = new DiceImage("playerdice1.png",viewwidth/20);
        }

        for (int i = 0; i < 3; i++){
            enemydiceimages[i] = new DiceImage("enemydice1.png",viewwidth/20);
        }



        final TextButton rollDice  = new TextButton("10 Gold to Roll dice", main.getSkin());
        rollDice.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (main.getPlayer().getGold() >= 10){
                    rollDice.setText("10 Gold to Roll Dice");
                    int gold = main.getPlayer().getGold();
                    String gameState = ceelo.playGame();
                    int[] playerdice = ceelo.getPlayerdice();
                    int[] enemydice = ceelo.getEnemydice();
                    gameStateLabel.setText(gameState);
                    setDiceImages(playerdice,enemydice);
                    if (gameState == "Lost"){
                        player.setGold(gold - 10);
                    } else if (gameState == "Won"){
                        player.setGold(gold + 10);
                    }
                    currentGoldLabel.setText(Integer.toString(main.getPlayer().getGold()));
                } else {

                    if (sellSoulStage == 0){
                        rollDice.setText("You don't have enough money");
                        sellSoulStage += 1;
                    } else if (sellSoulStage == 1) {
                        rollDice.setText("SELL SOUL");
                        sellSoulStage += 1;
                    } else if (sellSoulStage == 2){
//                        while (main.getPlayer().getGold() != 100){
                        sellSoul(pirateGame, ceelo, rollDice);
//                        }
                        rollDice.setText("10 Gold to Roll Dice");
                        sellSoulStage = 0;
                    }
                }

            }
        });


        //initializes data labels
        Label playerIntroLabel = new Label("You:", main.getSkin());
        Label enemyIntroLabel = new Label("Enemy:", main.getSkin());
        Label goldIntroLabel = new Label("Gold: ", main.getSkin());
        gameStateLabel = new Label("No game yet", main.getSkin());
        currentGoldLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin());

        //initializes UI tables, uses data labels and buttons
        Table gameTable = new Table();
        gameTable.setFillParent(true);
        gameTable.add(rollDice).uniform().fill().padBottom(viewheight/40).size(viewwidth/2,viewheight/10);

        mainStage.addActor(gameTable);

        Table diceTable = new Table();
        diceTable.setFillParent(true);
        diceTable.align(Align.top);
        diceTable.add(playerIntroLabel);
        diceTable.add(playerdiceimages);
        diceTable.row();

        diceTable.add(enemyIntroLabel);
        diceTable.add(enemydiceimages);
        diceTable.row();

        diceTable.add(gameStateLabel);
        diceTable.row();
        mainStage.addActor(diceTable);

        Table goldTable = new Table();
        goldTable.setFillParent(true);
        goldTable.add(goldIntroLabel);
        goldTable.add(currentGoldLabel);
        goldTable.align(Align.topLeft);
        mainStage.addActor(goldTable);


        Gdx.input.setInputProcessor(mainStage);

    }

    public void sellSoul(PirateGame main, Minigame ceelo, TextButton diceButton){
        int gold = main.getPlayer().getGold();
        String gameState = ceelo.playGame();
        int[] playerdice = ceelo.getPlayerdice();
        int[] enemydice = ceelo.getEnemydice();
        gameStateLabel.setText(gameState);
        setDiceImages(playerdice,enemydice);
        if (gameState == "Lost"){
            pirateGame.setScreen(new WinScreen(pirateGame, false));
        } else if (gameState == "Won"){
            player.setGold(100);
        }  else {
            sellSoul(pirateGame, ceelo, diceButton);
        }
        currentGoldLabel.setText(Integer.toString(main.getPlayer().getGold()));
        diceButton.setText("10 Gold to Roll dice");
        return;
    }

    public void setDiceImages(int[] playerdice, int[] enemydice){
        for (int i = 0; i < 3; i++){
            if (playerdice[i] == 1){
                playerdiceimages[i].setTexture("playerdice1.png");
            } else if (playerdice[i] == 2){
                playerdiceimages[i].setTexture("playerdice2.png");
            } else if (playerdice[i] == 3){
                playerdiceimages[i].setTexture("playerdice3.png");
            } else if (playerdice[i] == 4){
                playerdiceimages[i].setTexture("playerdice4.png");
            } else if (playerdice[i] == 5){
                playerdiceimages[i].setTexture("playerdice5.png");
            } else if (playerdice[i] == 6){
                playerdiceimages[i].setTexture("playerdice6.png");
            }
        }

        for (int i = 0; i < 3; i++){
            if (enemydice[i] == 1){
                enemydiceimages[i].setTexture("enemydice1.png");
            } else if (enemydice[i] == 2){
                enemydiceimages[i].setTexture("enemydice2.png");
            } else if (enemydice[i] == 3){
                enemydiceimages[i].setTexture("enemydice3.png");
            } else if (enemydice[i] == 4){
                enemydiceimages[i].setTexture("enemydice4.png");
            } else if (enemydice[i] == 5){
                enemydiceimages[i].setTexture("enemydice5.png");
            } else if (enemydice[i] == 6){
                enemydiceimages[i].setTexture("enemydice6.png");
            }
        }
    }

    //Goes back to sailing mode if ESC is pressed
    @Override
    public void update(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            System.out.println("ESCAPE");
            pirateGame.setScreen(pirateGame.getSailingScene());
        }

    }

}
