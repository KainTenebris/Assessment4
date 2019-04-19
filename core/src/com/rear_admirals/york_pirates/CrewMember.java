package com.rear_admirals.york_pirates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// New class for Assessment 4

/**
 * Each Player object can hold multiple CrewMember objects for bonuses to stats
 */
public class CrewMember {
    private HashMap statsToAmounts;
    private String college;
    private String info;

    /**
     * Constructs a new CrewMember object
     * @param stats The stat string/amount pairs that are awarded to the Player
     * @param college The college they are purchasable from/originally from
     */
    private CrewMember(HashMap<String, Integer> stats, String college){
        this.college = college;
        this.statsToAmounts = stats;
        this.info = "(";
        Iterator it = statsToAmounts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            info += " +"+pair.getValue().toString()+" "+pair.getKey()+",";
        }
        info += " )";
    }

    /**
     * Creates an empty CrewMember object. Used as an empty CrewMember slot.
     */
    public CrewMember(){
        this.college = "";
        this.statsToAmounts = new HashMap();
        this.info = "Empty Slot";
    }

    public HashMap getStatsToAmounts()              { return this.statsToAmounts;           }
    public String getInfo()                         {   return this.info;                   }
    public String getCollege()                      {    return this.college;               }

    /**
     * Converts the college name to the corresponding College object
     * @return the corresponding College object
     * @throws IllegalStateException if the string doesn't match a College
     */
    public College getRealCollege() {
        if (this.college.equals("Derwent"))         {   return College.Derwent;             }
        else if (this.college.equals("Alcuin"))     {   return College.Alcuin;              }
        else if (this.college.equals("James"))      {   return College.James;               }
        else if (this.college.equals("Vanbrugh"))   {   return College.Vanbrugh;            }
        else if (this.college.equals("Halifax"))    {   return College.Halifax;             }
        else                                        {   throw new IllegalStateException();  }
    }

    public String toString(){
        String out = "";
        if (!this.equals(new CrewMember())){
            out += college + " CrewMember ";
        }
        out += info;
        return out;
    }

    /**
     * A CrewMember is equal to another if object if both:
     *  -the other object is a CrewMember
     *  -they are from the same College, since only 1 CrewMember per College is allowed
     *
     * @param o the other object
     * @return true if equal, false otherwise
     */
    public boolean equals(Object o){
        if (o instanceof CrewMember){
            CrewMember obj = (CrewMember) o;
                if (this.college.equals(obj.college)) {
                    return (this.getStatsToAmounts().equals(obj.getStatsToAmounts()));
                }
            }
        return false;
    }

    /**
     * Static stats and buff amounts to be assigned to a crew member
     */
    private static HashMap<String, Integer> crew1 = new HashMap<String, Integer>() {{put("Attack", 20); put("Defence", 30);}};
    private static HashMap<String, Integer> crew2 = new HashMap<String, Integer>() {{put("Accuracy", 20); put("Defence", 30);}};
    private static HashMap<String, Integer> crew3 = new HashMap<String, Integer>() {{put("Attack", 20); put("Accuracy", 30);}};
    private static HashMap<String, Integer> crew4 = new HashMap<String, Integer>() {{put("Attack", 50); put("Defence", 30);}};
    private static HashMap<String, Integer> crew5 = new HashMap<String, Integer>() {{put("Attack", 20); put("Defence", 300);}};

    /**
     * Static crew member objects added to a player upon purchase from their college.
     */
    static CrewMember Derwent = new CrewMember(crew1, "Derwent");
    static CrewMember Vanbrugh = new CrewMember(crew2,"Vanbrugh");
    static CrewMember James = new CrewMember(crew3, "James");
    static CrewMember Halifax = new CrewMember(crew4, "Halifax");
    static CrewMember Alcuin = new CrewMember(crew5, "Alcuin");
}
