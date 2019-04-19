package com.rear_admirals.york_pirates;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CrewMemberTest {
    /*
        The CrewMember class is fairly lightweight. Processing of stats is done in the Player class
     */
    @Test
    public void crewMemberTest() {
        CrewMember crew1 = new CrewMember();
    }

    @Test
    public void gettersTest() {
        CrewMember crew = CrewMember.Alcuin;
        assertTrue(crew.getStatsToAmounts().equals(new HashMap<String, Integer> () {{ put("Attack", 20); put("Defence", 300);}}));
        System.out.println("crew.getInfo() returns:\n" + crew.getInfo() + "\n"); //Syntax is not too important, visual test is adequate
        assertEquals(crew.getCollege(), "Alcuin");
        assertEquals(crew.getRealCollege(), College.Alcuin);
        System.out.println("crew.toString() returns:\n" + crew.toString() + "\n"); //Syntax is not too important, visual test is adequate
    }

    @Test public void equalsTest() {
        CrewMember crew1 = CrewMember.Alcuin;
        CrewMember crew2 = CrewMember.Derwent;
        assertFalse(crew1.equals(crew2));
        assertTrue(crew1.equals(crew1));
        //Equivalence with different objects requires black box testing
    }
}