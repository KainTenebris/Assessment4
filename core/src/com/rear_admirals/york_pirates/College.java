package com.rear_admirals.york_pirates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

public class College {

	private final String name;
	private ArrayList<College> ally;
    private boolean bossDead;
    private CrewMember crewMember;
    private boolean bought;

    public Integer CrewCost = 200;

    public College(String name, CrewMember crewMember) {
        this.name = name;
        this.ally = new ArrayList<College>();
        this.ally.add(this);
        this.bossDead = false;
        this.crewMember = crewMember;
        this.bought = false;
    }

    public College(String name) {
        this.name = name;
        this.ally = new ArrayList<College>();
        this.ally.add(this);
        this.bossDead = false;
        this.crewMember = null;
        this.bought = true;
    }

//    public boolean addCrewMember(){
//        if (!this.bought){
//            if (pirateGame.getPlayer().getGold() >= CrewCost) {
//                if (this.pirateGame.getPlayer().addCrewMember(this.crewMember)) {
//                    pirateGame.getPlayer().setGold(pirateGame.getPlayer().getGold() - CrewCost);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

//    public boolean removeCrewMember(CrewMember crewMember){
//        if (pirateGame.getPlayer().removeCrewMember(crewMember)){
//            crewMember.getCollege().bought = false;
//        }
//        return false;
//    }

    public String getName() { return name; }

    public ArrayList<College> getAlly() { return ally; }
    public void addAlly(College newAlly){
        ally.add(newAlly);
    }

    public boolean isBossDead() {
        return bossDead;
    }
    public void setBossDead(boolean bossDead) {
        this.bossDead = bossDead;
    }

    public static HashMap<String, Integer> crew1 = new HashMap() {{put("String", 20);}};

	public static College Derwent = new College("Derwent", new CrewMember(crew1));
    public static College Vanbrugh = new College("Vanbrugh", new CrewMember(crew1));
    public static College James = new College("James", new CrewMember(crew1));
    public static College Halifax = new College("Halifax", new CrewMember(crew1)); //added to meet requirements
    public static College Alcuin = new College("Alcuin", new CrewMember(crew1)); //added to meet requirements
    public static College Storm = new College("Storm"); //*Storm is labelled as a college as colleges have the functionality we need.
}
