package com.rear_admirals.york_pirates;

import java.util.HashMap;

public class CrewMember {
    private HashMap statsToAmounts;

    public CrewMember(HashMap<String, Integer> stats){
        this.statsToAmounts = stats;
    }

    public HashMap getStatsToAmounts(){ return this.statsToAmounts; }

    public boolean equals(Object o){
        if (o instanceof CrewMember){
            CrewMember obj = (CrewMember) o;
            if (this.getStatsToAmounts().equals(obj.getStatsToAmounts())){
                return true;
            }
        }
        return false;
    }
}
