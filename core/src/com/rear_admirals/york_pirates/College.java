package com.rear_admirals.york_pirates;

import java.util.ArrayList;

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

    public CrewMember getCrewMember()           { return this.crewMember;   }
    public boolean bought()                     { return this.bought;       }
    public String getName()                     { return name;              }
    public ArrayList<College> getAlly()         { return ally;              }
    public boolean isBossDead()                 { return bossDead;          }

    public void addAlly(College newAlly)        { ally.add(newAlly);        }
    public void setBossDead(boolean bossDead)   { this.bossDead = bossDead; }

    public void addCrewMember(Player player){
        if (!this.bought){
            if (player.getGold() >= CrewCost) {
                if (player.addCrewMember(this.crewMember)) {
                    this.bought = true;
                    player.addGold(-CrewCost);
                }
            }
        }
    }
    public boolean removeCrewMember(CrewMember crewMember, Player player){
        if (player.removeCrewMember(crewMember)){
            crewMember.getRealCollege().bought = false;
            player.addGold(CrewCost/2);
        }
        return false;
    }

	public static College Derwent = new College("Derwent", CrewMember.Derwent);
    public static College Vanbrugh = new College("Vanbrugh", CrewMember.Vanbrugh);
    public static College James = new College("James", CrewMember.James);
    public static College Halifax = new College("Halifax", CrewMember.Halifax); //added to meet requirements
    public static College Alcuin = new College("Alcuin", CrewMember.Alcuin); //added to meet requirements
    public static College Storm = new College("Storm"); //*Storm is labelled as a college as colleges have the functionality we need.
}
