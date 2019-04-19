//add ability to see health in screen

package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.base.BaseScreen;

public class CollegeScreen extends BaseScreen {
    private Player player;
    private Label pointsLabel;
    private Label goldLabel;
    private int toHeal;

    public CollegeScreen(PirateGame main, final College college){
        super(main);
        this.player = main.getPlayer();

        Table uiTable = new Table();

        Label pointsTextLabel = new Label("Points: ", main.getSkin());
        pointsLabel = new Label(Integer.toString(main.getPlayer().getPoints()), main.getSkin());
        pointsLabel.setAlignment(Align.left);

        Label goldTextLabel = new Label("Gold:", main.getSkin());
        goldLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin());
        goldLabel.setAlignment(Align.left);

        uiTable.add(pointsTextLabel);
        uiTable.add(pointsLabel).width(pointsTextLabel.getWidth());
        uiTable.row();
        uiTable.add(goldTextLabel).fill();
        uiTable.add(goldLabel).fill();

        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);

        uiStage.addActor(uiTable);

        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        Label title = new Label("Welcome to " + college.getName(), main.getSkin(), "title");
        final Label message = new Label("", main.getSkin());
        optionsTable.add(title);


        toHeal = player.getPlayerShip().getHealthMax() - player.getPlayerShip().getHealth();
        final TextButton heal = new TextButton("Repair Ship for "+ Integer.toString(toHeal/10) +" gold", main.getSkin());

        if (toHeal == 0) { heal.setText("Your ship is already fully repaired!"); }

        heal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (toHeal==0){
                    heal.setText("Your ship is already fully repaired!");
                }
                else {
                    if (player.payGold(toHeal / 10)) {
                        System.out.println("charged");
                        player.getPlayerShip().setHealth(player.getPlayerShip().getHealthMax());
                        message.setText("Successful repair");
                    } else {
                        message.setText("You don't have the funds to repair your ship");
                    }
                }
            }
        });
        optionsTable.row();
        optionsTable.add(heal);
        optionsTable.row();
        optionsTable.add(message);

        TextButton minigame_mode = new TextButton("Go to minigame", pirateGame.getSkin()); //added
        minigame_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                pirateGame.setScreen(new MinigameScreen(pirateGame));
                dispose();
            }
        });
        optionsTable.row();
        optionsTable.add(minigame_mode);    //Added

        //New for assessment 4:
        final TextButton crew_members_screen = new TextButton ("CrewMembers", pirateGame.getSkin());
        crew_members_screen.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pirateGame.setScreen(new CrewMembersScreen(pirateGame, college.getCrewMember(), new CollegeScreen(pirateGame, college), college));
                dispose();
            }
        });
        optionsTable.row();
        optionsTable.row();
        optionsTable.row();
        optionsTable.add(crew_members_screen);


        mainStage.addActor(optionsTable);
        Gdx.input.setInputProcessor(mainStage);
    }

    @Override
    public void update(float delta){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            pirateGame.setScreen(pirateGame.getSailingScene());
        }

        toHeal = player.getPlayerShip().getHealthMax() - player.getPlayerShip().getHealth();
        goldLabel.setText(Integer.toString(pirateGame.getPlayer().getGold()));
        pointsLabel.setText(Integer.toString(pirateGame.getPlayer().getPoints()));
    }

}
