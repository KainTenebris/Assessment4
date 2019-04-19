package com.rear_admirals.york_pirates;

import com.rear_admirals.york_pirates.screen.combat.attacks.*;
import com.rear_admirals.york_pirates.screen.combat.attacks.GrapeShot;

import java.util.*;

import static com.rear_admirals.york_pirates.College.*;
import static com.rear_admirals.york_pirates.ShipType.*;

public class Player {
    private Ship playerShip;
    private int gold;
    private int points;
    public static List<Attack> attacks = new ArrayList<Attack>();
    //New for assessment 4
    private CrewMember[] crewMembers;

    public Player(int gold) {
	    this.playerShip = new Ship(Brig, "Your Ship", Derwent);
        this.gold = gold;
        this.points = 0;
        //New for assessment 4
        this.crewMembers = new CrewMember[6];
        for (int i=0;i<6;i++){
            crewMembers[i] = new CrewMember();
        }

        attacks.add(Ram.attackRam);
        attacks.add(GrapeShot.attackSwivel);
        attacks.add(Attack.attackBoard);
    }

    //Added for assessment 4 testing. Creating a ship must be done in an instance of a game, so we can pass null for testing
    public Player(int gold, Ship ship) {
        this.playerShip = ship;
        this.gold = gold;
        this.points = 0;
        //New for assessment 4
        this.crewMembers = new CrewMember[6];
        for (int i=0;i<6;i++){
            crewMembers[i] = new CrewMember();
        }

        attacks.add(Ram.attackRam);
        attacks.add(GrapeShot.attackSwivel);
        attacks.add(Attack.attackBoard);
    }

    /*
    public Player(Ship ship) {
        this.playerShip = ship;
        this.gold = 0;
        this.points = 0;

        attacks.add(Ram.attackRam);
        attacks.add(Attack.attackSwivel);
        attacks.add(Attack.attackBoard);
    }
    */

    //New for assessment 4
    /**
     * Assigns a CrewMember to the player if there is a slot available
     * @param crewMember The CrewMember object to assign to the player
     * @return True on success, false if there was no space available
     */
    public boolean addCrewMember(CrewMember crewMember){
        /*
            iterates through the array of crewMembers
            if there is an empty spot, sets it to contain the added crew member and returns true
            else returns false
        */
        for (int i=0; i<6; i++){
            if (crewMembers[i].equals(new CrewMember())){
                crewMembers[i] = crewMember;
                Iterator it = crewMember.getStatsToAmounts().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if (pair.getKey()=="Attack"){   this.playerShip.setAttack(this.playerShip.getAttack()+(Integer)pair.getValue());    }
                    else if (pair.getKey()=="Defence"){ this.playerShip.setDefence(this.playerShip.getDefence()+(Integer)pair.getValue()); }
                    else if (pair.getKey()=="Accuracy"){ this.playerShip.setAccuracy(this.playerShip.getAccuracy()+(Integer)pair.getValue()); }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a CrewMember from the Player by setting it to a blank new CrewMember()
     * @param crewMember The CrewMember to remove
     * @return True if the CrewMember was removed, False if the CrewMember wasn't found
     */
    public boolean removeCrewMember(CrewMember crewMember) {
        /*
            iterates through the array of crewMembers
            if it finds the specified crewMember, sets it to null and returns true
            else returns false
        */
        for (int i=0;i<6;i++){
            if (crewMembers[i].equals(crewMember)){
                Iterator it = crewMember.getStatsToAmounts().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if (pair.getKey()=="Attack"){   this.playerShip.setAttack(this.playerShip.getAttack()-(Integer)pair.getValue());    }
                    else if (pair.getKey()=="Defence"){ this.playerShip.setDefence(this.playerShip.getDefence()-(Integer)pair.getValue()); }
                    else if (pair.getKey()=="Accuracy"){ this.playerShip.setAccuracy(this.playerShip.getAccuracy()-(Integer)pair.getValue()); }
                }
                crewMembers[i] = new CrewMember();
                return true;
            }
        }
        return false;
    }

    //New for assessment 4
    /**
     * Checks if the Player has space for another CrewMember
     * @return True if there is a blank CrewMember, False otherwise
     */
    public boolean hasSpace(){
        for (int i=0;i<6;i++){
            if (crewMembers[i].equals(new CrewMember())){   return true;    }
        }
        return false;
    }
    //New for assessment 4
    public CrewMember[] getCrewMembers(){ return this.crewMembers;  }

    public Ship getPlayerShip()         { return this.playerShip;   }
    public int getPoints()              { return points;            }
	public int getGold()                { return gold;              }

    public void addPoints(int amount)   { this.points += amount;    }
    public void addGold(int amount)     { this.gold += amount;      }

	public boolean payGold(int amount){
        if (amount > gold){
            return false;
        }
        else{
            addGold(-amount);
            return true;
        }
    }
}