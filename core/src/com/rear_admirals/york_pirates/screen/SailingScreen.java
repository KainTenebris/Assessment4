package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.*;
import com.rear_admirals.york_pirates.screen.combat.CombatScreen;
import com.rear_admirals.york_pirates.base.BaseActor;
import com.rear_admirals.york_pirates.base.BaseScreen;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.rear_admirals.york_pirates.College.*;
import static com.rear_admirals.york_pirates.PirateGame.Chemistry;
import static com.rear_admirals.york_pirates.PirateGame.Physics;
import static com.rear_admirals.york_pirates.PirateGame.Philosophy;
import static com.rear_admirals.york_pirates.ShipType.*;

public class SailingScreen extends BaseScreen {

    private Ship playerShip;

    //Map Variables
    private ArrayList<BaseActor> obstacleList;
    private ArrayList<BaseActor> removeList;
    private ArrayList<BaseActor> regionList;

    private int tileSize = 64;
    private int tileCountWidth = 80;
    private int tileCountHeight = 80;

    //calculate game world dimensions
    private final int mapWidth = tileSize * tileCountWidth;
    private final int mapHeight = tileSize * tileCountHeight;
    private TiledMap tiledMap;

    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera tiledCamera;
    private int[] preWhirlpoolBackgroundLayers = {0};
    private int[] postWhirlpoolBackgroundlayers = {1,2};
    private int[] foregroundLayers = {3};

    private Label pointsLabel;
    private Label goldLabel;
    private Label healthLabel;//*Adding HP points to UI so they can see damage taken in storm
    private Label mapMessage;
    private Label hintMessage;

    private Float timer;
    private Float pointMulti; //added multiplier to increase point gain over time
    private int goldCounter; //used to add ticking gold for capturing colleges
	
	//--------------------------------------------------------------------------------------------------------------------------
    private boolean inStorm = false; //*If the ship is in a storm.
	private int weatherTimer = 0; //Timer to see how long ship was in storm for damage.
    //--------------------------------------------------------------------------------------------------------------------------

    /* New for Assessment 4 */
    private Obstacle whirlpool;
    private Texture whirlpoolTexture;
    private boolean inWhirlpool = false;
    private Timer whirlpoolTimer = new Timer();
    private float whirlpoolDelta;


    public SailingScreen(final PirateGame main){
        super(main);

        playerShip = main.getPlayer().getPlayerShip();
        System.out.println(playerShip.getName());

        mainStage.addActor(playerShip);
        System.out.println("playerShip added");

        //inits UI table
        initUItable(main);

        //inits message table
        initMessTable(main);

        //inits tileMap (including relevant objects such as college/rock hitboxes, regions and the playerShip)
        initTileMap(main);

        timer = 0f;
        pointMulti = 1f;
        goldCounter = 0;

        InputMultiplexer im = new InputMultiplexer(uiStage, mainStage);
        Gdx.input.setInputProcessor(im);

        // Debug processor
//        System.out.println("IP: im");
    }

    @Override
    public void update(float delta) {
        removeList.clear();
        goldLabel.setText(Integer.toString(pirateGame.getPlayer().getGold()));
        this.playerShip.playerMove(delta);

        updateObstacle(delta);

        boolean x = false;
        for (BaseActor region : regionList) {
            String name = region.getName();
            if (playerShip.overlaps(region, false)) {
                x = true;
                mapMessage.setText(capitalizeFirstLetter(name.substring(0, name.length() - 6)) + " Territory");
                //*---------------------------------------------------------------------------------------------------------------
				//We will put bad weather down as a region as we don't want it to be something that the player can intereact with, etc
                //And it also opens up the opportunity for us to use the random enemy encounters to make a bad weather related enemy encounter.
                //Also displays on the UI what region they're in, helping them know if they're still in the storm.
                if(name.equals("stormyregion")){
                    weatherTimer++; //Time in storm increased
                    inStorm = true; //Used to affect point bonus at the bottom of code.
                    if (weatherTimer >= 30){ //After certain point
                        if(playerShip.getHealth()> (playerShip.getHealthMax()/4)){
                            playerShip.addHealth(-1); //Can't die from bad weather, only get down to 1/4 of max HP
                        }
                        weatherTimer = 0;
                    }
                } else{
                    if(inStorm){
                        //Set it all to zero/false when out of storm, if boolean value still says they're in storm.
						inStorm = false;
						weatherTimer = 0;
					}

                }
				//*End weather stuff here------------------------------------------------------------------------------------------
				
				
				Random randint = new Random();
				int enemyChance = randint.nextInt(10001);
                if (enemyChance <= 1) {
                    System.out.println("Enemy Found in " + name);
                    College college = region.getCollege();
                    if (!playerShip.getCollege().getAlly().contains(college)) {
                        System.out.println(name);
                        pirateGame.setScreen(new CombatScreen(pirateGame, new Ship(Brig, college)));
                    }
                }
            }
        }

        //Press O to get objectives
        if (Gdx.input.isKeyPressed(Input.Keys.O)){
            pirateGame.setScreen(new ObjectiveScreen(pirateGame));
        }

        if (!x) {
            mapMessage.setText("Neutral Territory");
        }


        boolean y = false;
        for (BaseActor obstacle : obstacleList) {
            String name = obstacle.getName();
            if (playerShip.overlaps(obstacle, true)) {
                y = true;
                if (!(obstacle.getDepartment() == null)) {
                    mapMessage.setText(capitalizeFirstLetter(name) + " Island");
                    hintMessage.setText("Press F to interact");
                    if (Gdx.input.isKeyPressed(Input.Keys.F)) pirateGame.setScreen(new DepartmentScreen(pirateGame, obstacle.getDepartment()));
                }
                // Obstacle must be a college if college not null
                else if (!(obstacle.getCollege() == null)) {
                    mapMessage.setText(capitalizeFirstLetter(name) + " Island");
                    hintMessage.setText("Press F to interact");
//                    System.out.println("A college");
                    College college = obstacle.getCollege();
                    if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                        System.out.println("A college");
                        if (!playerShip.getCollege().getAlly().contains(college) && !obstacle.getCollege().isBossDead()) {
                            //*-----------------------------------------------------------------------------------------------------------------------------------
                            //*Make final boss more difficult than the regular college bosses.
							if(college.getName().equals("Halifax")){ //Final boss name
                                System.out.println("Final boss");//
                                pirateGame.setScreen(new CombatScreen(pirateGame, new Ship(15, 15, 15, Fort, college, college.getName() + " Final Boss", true)));//*
                            } else {
                                //*---------------------------------------------------------------------------------------------------------------------------------
                                System.out.println("Enemy");
                                pirateGame.setScreen(new CombatScreen(pirateGame, new Ship(8, 8, 8, Fort, college, college.getName() + " Boss", true)));//*
                            }
                        } else {
                            System.out.println("Ally");
                            pirateGame.setScreen(new CollegeScreen(pirateGame, college));
                        }
                    }
                }
            }
        }

        if (!y) hintMessage.setText("");

        for (BaseActor object : removeList) {
            object.remove();
        }

        // camera adjustment
        Camera mainCamera = mainStage.getCamera();

        // center camera on player
        mainCamera.position.x = playerShip.getX() + playerShip.getOriginX();
        mainCamera.position.y = playerShip.getY() + playerShip.getOriginY();

        // bound camera to layout
        mainCamera.position.x = MathUtils.clamp(mainCamera.position.x, viewwidth / 2, mapWidth - viewwidth / 2);
        mainCamera.position.y = MathUtils.clamp(mainCamera.position.y, viewheight / 2, mapHeight - viewheight / 2);
        mainCamera.update();

        // adjust tilemap camera to stay in sync with main camera
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

        timer += delta;
        if (timer > 1) {
            //changed point gain to have a multiplier which increases on update - more time = higher multiplier
            if (Math.round(pointMulti) < 2) {
                pointMulti += pointMulti/300;
            } else if (Math.round(pointMulti) < 3) {
                pointMulti += pointMulti/500;
            }
            //at maximum multiplier (3) - stops updating
            //*-----------------------------------------------------------------------------------------------------------------------
            if(inStorm){
                pirateGame.getPlayer().addPoints(Math.round(pointMulti)); //bonus points
            }
            //*-----------------------------------------------------------------------------------------------------------------------
            pirateGame.getPlayer().addPoints(Math.round(pointMulti));

            //when you capture colleges you also gain gold. Rate of which dependent on number of colleges
            //checks to see if more than 1 colleges are allys - if that is the case ticks the gold counter up
            if (pirateGame.getPlayer().getPlayerShip().getCollege().getAlly().size() > 1){
                goldCounter += 1;
                //tick rate is dependent on number of colleges - more colleges means higher tick rate
                if (goldCounter == 6 - pirateGame.getPlayer().getPlayerShip().getCollege().getAlly().size()) {
                    //gold is added and counter is reset when counter reaches some number dependent on size of ally
                    goldCounter = 0;
                    pirateGame.getPlayer().addGold(1);
                }
            }

            timer -= 1;
        }

        pointsLabel.setText(Integer.toString(pirateGame.getPlayer().getPoints()));
        healthLabel.setText(Integer.toString(playerShip.getHealth()));//*
    }

    @Override
    public void render(float delta) {
        uiStage.act(delta);

        mainStage.act(delta);
        update(delta);

        // render
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render(preWhirlpoolBackgroundLayers);
        if(whirlpool != null) {
            mainStage.getBatch().begin();
            whirlpool.draw(mainStage.getBatch(),1);
            mainStage.getBatch().end();
        }
        tiledMapRenderer.render(postWhirlpoolBackgroundlayers);

        mainStage.draw();

        tiledMapRenderer.render(foregroundLayers);

        uiStage.draw();

        if (!playerShip.isAnchor()){
            playerShip.addAccelerationAS(playerShip.getRotation(), 10000);
        } else{
            playerShip.setAccelerationXY(0,0);
            playerShip.setDeceleration(100);
        }
    }

    @Override
    public void dispose () {
        mainStage.dispose();
        uiStage.dispose();
        playerShip.getSailingTexture().dispose();
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    //initializes UI table
    public void initUItable(PirateGame main){
        Table uiTable = new Table();

        Label pointsTextLabel = new Label("Points: ", main.getSkin(),"default_black");
        pointsLabel = new Label(Integer.toString(main.getPlayer().getPoints()), main.getSkin(), "default_black");
        pointsLabel.setAlignment(Align.left);

        Label goldTextLabel = new Label("Gold:", main.getSkin(),"default_black");
        goldLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin(), "default_black");
        goldLabel.setAlignment(Align.left);

        Label healthTextLabel = new Label("Health:", main.getSkin(),"default_black");//*
        healthLabel = new Label(Integer.toString(playerShip.getHealth()), main.getSkin(), "default_black");//*
        healthLabel.setAlignment(Align.left);//*

        uiTable.add(pointsTextLabel);
        uiTable.add(pointsLabel).width(pointsTextLabel.getWidth());
        uiTable.row();
        uiTable.add(goldTextLabel).fill();
        uiTable.add(goldLabel).fill();
        //---------------------------------------------------------------------------------------------------------------------
        uiTable.add(healthTextLabel).fill();//*Just adding HP to UI
        uiTable.add(healthLabel).fill();//*
        //---------------------------------------------------------------------------------------------------------------------
        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);

        uiStage.addActor(uiTable);
    }

    public void initMessTable(PirateGame main){

        mapMessage = new Label("", main.getSkin(), "default_black");
        hintMessage = new Label("", main.getSkin(),"default_black");

        Table messageTable = new Table();
        messageTable.add(mapMessage);
        messageTable.row();
        messageTable.add(hintMessage);

        messageTable.setFillParent(true);
        messageTable.top();

        uiStage.addActor(messageTable);
    }

    public void initTileMap(PirateGame main){
        obstacleList = new ArrayList<BaseActor>();
        removeList = new ArrayList<BaseActor>();
        regionList = new ArrayList<BaseActor>();

        // set up tile map, renderer and camera
        tiledMap = new TmxMapLoader().load("game_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, viewwidth, viewheight);
        tiledCamera.update();

        MapObjects objects = tiledMap.getLayers().get("ObjectData").getObjects();
        //test player - identifies where the player hitbox is and sets the playerShip position to it
        findPlayer(objects);

        //maps hitboxes to the appropriate college and sets the hitboxes as a solid
        objects = tiledMap.getLayers().get("PhysicsData").getObjects();
        setCollegeHitboxes(objects);

        //sets the regions for relevant colleges/bad weather
        objects = tiledMap.getLayers().get("RegionData").getObjects();
        setRegions(objects);

    }

    //Find player and sets playerShip position
    public void findPlayer(MapObjects objects){
        //makes every object a RectangleMapObject and sets the starting position of the player ship
        for (MapObject object : objects) {
            String name = object.getName();

            // all object data assumed to be stored as rectangles - used to set position of playerShip
            RectangleMapObject rectangleObject = (RectangleMapObject)object;
            Rectangle r = rectangleObject.getRectangle();

            //if it is the player put the playerShip in the relevant starting position
            if (name.equals("player")){
                playerShip.setPosition(r.x, r.y);
            } else{
                System.err.println("Unknown tilemap object: " + name);
            }
        }
    }

    //looks at all PhysicsObjects and maps it to a relevant college - or ignores in the case of rocks
    public void setCollegeHitboxes(MapObjects objects){
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                BaseActor solid = new BaseActor();
                solid.setPosition(r.x, r.y);
                solid.setSize(r.width, r.height);
                solid.setName(object.getName());
                solid.setRectangleBoundary();
                String objectName = object.getName();

                if (objectName.equals("derwent")) solid.setCollege(Derwent);
                else if (objectName.equals("james")) solid.setCollege(James);
                else if (objectName.equals("vanbrugh")) solid.setCollege(Vanbrugh);
                else if (objectName.equals("halifax")) solid.setCollege(Halifax); //added for req
                else if (objectName.equals("alcuin")) solid.setCollege(Alcuin); //added for req
                else if (objectName.equals("chemistry"))solid.setDepartment(Chemistry);
                else if (objectName.equals("physics")) solid.setDepartment(Physics);
                else if (objectName.equals("philosophy")) solid.setDepartment(Philosophy); //added for req
                else if (!objectName.equals("rocks")) { //ignores rocks
                    System.out.println("Not college/department: " + solid.getName());
                }
                obstacleList.add(solid);
            } else {
                System.err.println("Unknown PhysicsData object.");
            }
        }
    }

    //sets the regions the player can sail in - no region = neutral region. Includes bad weather
    public void setRegions(MapObjects objects){
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                BaseActor region = new BaseActor();
                region.setPosition(r.x, r.y);
                region.setSize(r.width, r.height);
                region.setRectangleBoundary();
                region.setName(object.getName());

                if (object.getName().equals("derwentregion")) region.setCollege(Derwent);
                else if (object.getName().equals("jamesregion")) region.setCollege(James);
                else if (object.getName().equals("vanbrughregion")) region.setCollege(Vanbrugh);
                else if (object.getName().equals("halifaxregion")) region.setCollege(Halifax);
                else if (object.getName().equals("alcuinregion")) region.setCollege(Alcuin);
                else if (object.getName().equals("stormyregion")) region.setCollege(Storm);//*Adding storm region to the map. Set as colleges as they have the functionality we need.
                regionList.add(region);
            } else {
                System.err.println("Unknown RegionData object.");
            }
        }
    }

    //New for assessment 4
    /**
        Spawns a whirlpool at a random location.
     */
    public void spawnObstacle() {
        System.out.println("Spawning obstacle");
        this.whirlpoolDelta = 0;
        this.whirlpoolTexture = new Texture("whirlpool.png");
        Random ran = new Random();
        int x = ran.nextInt(this.mapWidth - this.whirlpoolTexture.getWidth());
        int y = ran.nextInt(this.mapHeight - this.whirlpoolTexture.getHeight());
        this.whirlpool = new Obstacle(x, y, whirlpoolTexture);

        //Despawns after 30s
        whirlpoolTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                despawnObstacle();
            }
        }, 30*1000);
    }

    //New for Assessment 4:
    /**
        Deletes the whirlpool object.
     */
    private void despawnObstacle() {
        System.out.println("Despawning obstacle");
        this.whirlpool = null;
    }

    //New for Assessment 4:
    /**
        Checks if the player is in a whirlpool and handles all the events if they are.
            -Reduced speed
            -Damage taken
        Also randomly spawns a new obstacle if one doesn't exist

        @param delta time since last call, used for damage over time calculations
     */
    private void updateObstacle(float delta) {
        if (this.whirlpool != null) {
            if (this.whirlpool.overlaps(playerShip, false) != inWhirlpool) {
                inWhirlpool = !inWhirlpool;
            }
            if (inWhirlpool) {
                this.whirlpoolDelta += delta;
                playerShip.setMaxSpeed(100);
                if (this.whirlpoolDelta >= 0.5f) {
                    playerShip.addHealth(-10);
                    if (playerShip.getHealth() <= 0) {
                        playerShip.setHealth((int) (playerShip.getHealthMax() * .25));
                        Player player = pirateGame.getPlayer();
                        player.addGold((int) (-0.5 * player.getGold()));
                    }
                    this.whirlpoolDelta -= 1;
                }
            } else {
                this.whirlpoolDelta = 0;
                playerShip.setMaxSpeed(400);
            }
        } else {
            Random rand = new Random();
            if (rand.nextInt(500) == 0) {
                spawnObstacle();
                return;
            }
        }
    }
}