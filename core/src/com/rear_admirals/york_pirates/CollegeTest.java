package com.rear_admirals.york_pirates;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CollegeTest {
    /*
    addCrewMember and removeCrewMember require black box testing
     */
    @Test
    public void getCrewMemberTest() {
        College testColl = College.Derwent;
        assertEquals(testColl.getCrewMember(), CrewMember.Derwent);
    }

    @Test
    public void boughtTest() {
        College testColl = College.Derwent;
        assertFalse(testColl.bought());
    }

    @Test
    public void addCrewMemberTest() {
        Player player1 = new Player(0, null);
        College coll = College.Derwent;
        coll.addCrewMember(player1);//Should fail since not enough gold
        for(CrewMember crew : player1.getCrewMembers()) {
            assertEquals(crew, new CrewMember());
        }
    }
}
