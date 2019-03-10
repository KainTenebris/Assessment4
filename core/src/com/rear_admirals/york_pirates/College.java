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

    private Integer CrewCost = 200;

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

    public CrewMember getCrewMember(){  return this.crewMember; }

    public void addCrewMember(Player player){
        if (!this.bought){
            if (player.getGold() >= CrewCost) {
                if (player.addCrewMember(this.crewMember)) {
                    System.out.println("CREWMEMBER ADDED");
                    for (int i=0;i<6;i++){
                        System.out.println(player.getCrewMembers()[i].toString());
                    }
                    player.setGold(player.getGold() - CrewCost);
                }
            }
        }
    }

    public boolean removeCrewMember(CrewMember crewMember, Player player){
        if (player.removeCrewMember(crewMember)){
            crewMember.getRealCollege().bought = false;
            player.setGold(player.getGold() + CrewCost/2);
        }
        return false;
    }

    public boolean bought() {   return this.bought;  }

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

    public static HashMap<String, Integer> crew1 = new HashMap() {{put("Attack", 20); put("Defence", 30);}};

	public static College Derwent = new College("Derwent", new CrewMember(crew1, "Derwent"));
    public static College Vanbrugh = new College("Vanbrugh", new CrewMember(crew1, "Vanbrugh"));
    public static College James = new College("James", new CrewMember(crew1, "James"));
    public static College Halifax = new College("Halifax", new CrewMember(crew1, "Halifax")); //added to meet requirements
    public static College Alcuin = new College("Alcuin", new CrewMember(crew1, "Alcuin")); //added to meet requirements
    public static College Storm = new College("Storm"); //*Storm is labelled as a college as colleges have the functionality we need.
}
