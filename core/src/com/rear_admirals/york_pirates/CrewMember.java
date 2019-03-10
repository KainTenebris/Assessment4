package com.rear_admirals.york_pirates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CrewMember {
    private HashMap statsToAmounts;
    private String college;
    private String info;

    public CrewMember(){
        this.college = "";
        this.statsToAmounts = new HashMap();
        this.info = "Empty Slot";
    }

    public CrewMember(HashMap<String, Integer> stats, String college){
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

    public String toString(){
        String out = "";
        if (!this.equals(new CrewMember())){
            out += college + " CrewMember ";
        }
        out += info;
        return out;
    }

    public String getInfo() {   return this.info;   }

    public College getRealCollege() {
        if (this.college == "Derwent"){
            return College.Derwent;
        } else if (this.college == "Alcuin"){
            return College.Alcuin;
        } else if (this.college == "Halifax"){
            return College.Halifax;
        } else if (this.college == "Vanbrugh"){
            return College.Vanbrugh;
        }   else if (this.college == "James"){
            return College.James;
        }   else {
            throw new IllegalArgumentException();
        }
    }

    public String getCollege() {    return this.college;    }

    public HashMap getStatsToAmounts(){ return this.statsToAmounts; }

    public boolean equals(Object o){
        if (o instanceof CrewMember){
            CrewMember obj = (CrewMember) o;
            if (this.statsToAmounts.size()==obj.statsToAmounts.size()) {
                if (this.college == obj.college) {
                    Iterator it1 = this.statsToAmounts.entrySet().iterator();
                    Iterator it2 = obj.statsToAmounts.entrySet().iterator();
                    while (it1.hasNext()) {
                        Map.Entry pair1 = (Map.Entry) it1.next();
                        Map.Entry pair2 = (Map.Entry) it2.next();
                        if (pair1.getKey() != pair2.getKey() || pair1.getValue() != pair2.getValue()) {
                            return false;
                        }
                        it1.remove();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private static HashMap<String, Integer> crew1 = new HashMap() {{put("Attack", 20); put("Defence", 30);}};
    private static HashMap<String, Integer> crew2 = new HashMap() {{put("Accuracy", 20); put("Defence", 30);}};
    private static HashMap<String, Integer> crew3 = new HashMap() {{put("Attack", 20); put("Accuracy", 30);}};
    private static HashMap<String, Integer> crew4 = new HashMap() {{put("Attack", 50); put("Defence", 30);}};
    private static HashMap<String, Integer> crew5 = new HashMap() {{put("Attack", 20); put("Defence", 300);}};

    static CrewMember Derwent = new CrewMember(crew1, "Derwent");
    static CrewMember Vanbrugh = new CrewMember(crew2,"Vanbrugh");
    static CrewMember James = new CrewMember(crew3, "James");
    static CrewMember Halifax = new CrewMember(crew4, "Halifax");
    static CrewMember Alcuin = new CrewMember(crew5, "Alcuin");
}
