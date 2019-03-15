package com.rear_admirals.york_pirates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* New class for Assessment 4 */
public class CrewMember {
    private HashMap statsToAmounts;
    private String college;
    private String info;

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
    public CrewMember(){
        this.college = "";
        this.statsToAmounts = new HashMap();
        this.info = "Empty Slot";
    }

    public HashMap getStatsToAmounts()              { return this.statsToAmounts;           }
    public String getInfo()                         {   return this.info;                   }
    public String getCollege()                      {    return this.college;               }
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
    public boolean equals(Object o){
        if (o instanceof CrewMember){
            CrewMember obj = (CrewMember) o;
                if (this.college.equals(obj.college)) {
                    return (this.getStatsToAmounts().equals(obj.getStatsToAmounts()));
                }
            }
        return false;
    }

    private static HashMap<String, Integer> crew1 = new HashMap<String, Integer>() {{put("Attack", 20); put("Defence", 30);}};
    private static HashMap<String, Integer> crew2 = new HashMap<String, Integer>() {{put("Accuracy", 20); put("Defence", 30);}};
    private static HashMap<String, Integer> crew3 = new HashMap<String, Integer>() {{put("Attack", 20); put("Accuracy", 30);}};
    private static HashMap<String, Integer> crew4 = new HashMap<String, Integer>() {{put("Attack", 50); put("Defence", 30);}};
    private static HashMap<String, Integer> crew5 = new HashMap<String, Integer>() {{put("Attack", 20); put("Defence", 300);}};

    static CrewMember Derwent = new CrewMember(crew1, "Derwent");
    static CrewMember Vanbrugh = new CrewMember(crew2,"Vanbrugh");
    static CrewMember James = new CrewMember(crew3, "James");
    static CrewMember Halifax = new CrewMember(crew4, "Halifax");
    static CrewMember Alcuin = new CrewMember(crew5, "Alcuin");
}
