package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.*;
import com.rear_admirals.york_pirates.base.BaseScreen;

public class ObjectiveScreen extends BaseScreen {

    private Player player;
    private Label[] objectives;
    private Texture texture;
    private Image map;
    //0 James, 1 Vanbrugh, 2 Alcuin, 3 Halifax
    //Halifax is final boss
    //Derwent starts allied?

    public ObjectiveScreen(final PirateGame main){
        super(main);
        player = main.getPlayer();

        //Displays map
        this.texture = new Texture("game_map.png");
        map = new Image(texture);
        map.setSize(viewwidth, viewheight);
        mainStage.addActor(map);

        objectives = new Label[4];

        for (int i = 0; i < objectives.length; i++){
            objectives[i] = new Label("Not yet Completed", main.getSkin());
            objectives[i].setColor(0,0,0,255);
        }

        //Whether the objectives have been completed or not
        if (this.player.getPlayerShip().getCollege().getAlly().contains(College.James)){
            objectives[0].setText("Completed");
        }
        if (this.player.getPlayerShip().getCollege().getAlly().contains(College.Vanbrugh)){
            objectives[1].setText("Completed");
        }
        if (this.player.getPlayerShip().getCollege().getAlly().contains(College.Alcuin)){
            objectives[2].setText("Completed");
        }
        if (this.player.getPlayerShip().getCollege().getAlly().contains(College.Halifax)){
            objectives[3].setText("Completed");
        }

        //Initialising labels;
        Label obj_James = new Label("Beat James College: ", main.getSkin());
        obj_James.setColor(0,0,0,255);
        Label obj_Vanbrugh = new Label("Beat Vanbrugh College: ", main.getSkin());
        obj_Vanbrugh.setColor(0,0,0,255);
        Label obj_Alcuin = new Label("Beat Alcuin College: ", main.getSkin());
        obj_Alcuin.setColor(0,0,0,255);
        Label obj_Halifax = new Label("Beat Final Boss (Halifax): ", main.getSkin());
        obj_Halifax.setColor(0,0,0,255);

        Table uiTable = new Table();
        uiTable.add(obj_James);
        uiTable.add(objectives[0]).width(obj_James.getWidth()+ 200);
        uiTable.row();
        uiTable.add(obj_Vanbrugh);
        uiTable.add(objectives[1]).width(obj_Vanbrugh.getWidth()+ 200);
        uiTable.row();
        uiTable.add(obj_Alcuin);
        uiTable.add(objectives[2]).width(obj_Vanbrugh.getWidth()+ 200);
        uiTable.row();
        uiTable.add(obj_Halifax);
        uiTable.add(objectives[3]).width(obj_Halifax.getWidth()+ 200);

        uiTable.align(Align.topLeft);
        uiTable.setFillParent(true);

        mainStage.addActor(uiTable);
        Gdx.input.setInputProcessor(mainStage);
    }

    @Override
    public void update(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            System.out.println("ESCAPE");
            pirateGame.setScreen(pirateGame.getSailingScene());
        }

    }
}
