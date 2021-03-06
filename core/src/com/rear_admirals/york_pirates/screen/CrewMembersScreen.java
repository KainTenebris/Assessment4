package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.CrewMember;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.base.BaseScreen;

/* New class for Assessment 4 */

/**
 * Extends BaseScreen to show the buying/selling CrewMembers screen.
 */
public class CrewMembersScreen extends BaseScreen {
    private CrewMember crewMember;
    private Label pointsLabel;
    private Label goldLabel;
    private Screen previous;
    private College college;

    private TextButton buy_CrewMember;
    private TextButton sell_CrewMember0;
    private TextButton sell_CrewMember1;
    private TextButton sell_CrewMember2;
    private TextButton sell_CrewMember3;
    private TextButton sell_CrewMember4;
    private TextButton sell_CrewMember5;

    /**
     * Creates a new CrewMembersScreen object
     * @param main The game instance
     * @param crewMember The CrewMember for sale at this college
     * @param previous The screen to return to after this screen
     * @param college The college currently visiting
     */
    public CrewMembersScreen(PirateGame main, CrewMember crewMember, Screen previous, College college){
        super(main);
        this.crewMember = crewMember;
        this.previous = previous;
        this.college = college;

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

        Label title = new Label("Welcome to "+crewMember.getCollege(), main.getSkin());
        title.setPosition((viewwidth/2)-150,800);
        title.setAlignment(Align.center);

        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        Label buy_label = new Label("Gain a CrewMember (-200G)", main.getSkin());
        buy_label.setAlignment(Align.left);
        optionsTable.row().pad(50);
        optionsTable.add(buy_label);

        buy_CrewMember = new TextButton(crewMember.getCollege()+" CrewMember "+crewMember.getInfo(), main.getSkin());
        buyListener(buy_CrewMember);
        buy_CrewMember.align(Align.right);
        optionsTable.add(buy_CrewMember);

        Label sell_label = new Label("Remove a CrewMember (+100G)", main.getSkin());
        optionsTable.row().pad(50);
        optionsTable.add(sell_label);

        Table crew_table = new Table();

        sell_CrewMember0 = new TextButton(main.getPlayer().getCrewMembers()[0].toString(), main.getSkin());
        sell_CrewMember1 = new TextButton(main.getPlayer().getCrewMembers()[1].toString(), main.getSkin());
        sell_CrewMember2 = new TextButton(main.getPlayer().getCrewMembers()[2].toString(), main.getSkin());
        sell_CrewMember3 = new TextButton(main.getPlayer().getCrewMembers()[3].toString(), main.getSkin());
        sell_CrewMember4 = new TextButton(main.getPlayer().getCrewMembers()[4].toString(), main.getSkin());
        sell_CrewMember5 = new TextButton(main.getPlayer().getCrewMembers()[5].toString(), main.getSkin());
        sellListener(sell_CrewMember0,main.getPlayer().getCrewMembers()[0]);
        sellListener(sell_CrewMember1,main.getPlayer().getCrewMembers()[1]);
        sellListener(sell_CrewMember2,main.getPlayer().getCrewMembers()[2]);
        sellListener(sell_CrewMember3,main.getPlayer().getCrewMembers()[3]);
        sellListener(sell_CrewMember4,main.getPlayer().getCrewMembers()[4]);
        sellListener(sell_CrewMember5,main.getPlayer().getCrewMembers()[5]);
        crew_table.add(sell_CrewMember0);
        crew_table.row();
        crew_table.add(sell_CrewMember1);
        crew_table.row();
        crew_table.add(sell_CrewMember2);
        crew_table.row();
        crew_table.add(sell_CrewMember3);
        crew_table.row();
        crew_table.add(sell_CrewMember4);
        crew_table.row();
        crew_table.add(sell_CrewMember5);

        optionsTable.add(crew_table);

        mainStage.addActor(optionsTable);
        mainStage.addActor(title);
        Gdx.input.setInputProcessor(mainStage);
    }

    @Override
    public void update(float delta) {
        //ESC returns to the previous screen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            pirateGame.setScreen(this.previous);
            dispose();
        }

        if (college.bought()){
            buy_CrewMember.setText("This CrewMember is already on board");
        } else {
            buy_CrewMember.setText(crewMember.getCollege()+" CrewMember "+crewMember.getInfo());
        }

        goldLabel.setText(Integer.toString(pirateGame.getPlayer().getGold()));
        pointsLabel.setText(Integer.toString(pirateGame.getPlayer().getPoints()));

        sell_CrewMember0.setText(pirateGame.getPlayer().getCrewMembers()[0].toString());
        sell_CrewMember1.setText(pirateGame.getPlayer().getCrewMembers()[1].toString());
        sell_CrewMember2.setText(pirateGame.getPlayer().getCrewMembers()[2].toString());
        sell_CrewMember3.setText(pirateGame.getPlayer().getCrewMembers()[3].toString());
        sell_CrewMember4.setText(pirateGame.getPlayer().getCrewMembers()[4].toString());
        sell_CrewMember5.setText(pirateGame.getPlayer().getCrewMembers()[5].toString());
    }

    /**
     * Adds a click listener to the buy_CrewMember button which assigns the CrewMember if
     * there is space, it can be afforded, and hasn't already been purchased
     * @param button The button to add the listener to
     */
    private void buyListener(final TextButton button){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (pirateGame.getPlayer().getGold()>=200 && !college.bought() && pirateGame.getPlayer().hasSpace()){
                    college.addCrewMember(pirateGame.getPlayer());
                }
            }
        });
    }
    /**
     * Adds a click listener to the sell_CrewMember button which sells the CrewMember if it is not an empty slot
     * @param button The button to add the listener to
     */
    private void sellListener(TextButton button, final CrewMember crewMember){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (!crewMember.equals(new CrewMember())){
                    college.removeCrewMember(crewMember, pirateGame.getPlayer());
                }
            }
        });
    }
}
