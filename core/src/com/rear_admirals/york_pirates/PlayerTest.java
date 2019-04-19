package com.rear_admirals.york_pirates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    //addCrewMember and removeCrewMember to be black box tested: they require a Ship instance, which requires a game instance
    @Test
    public void getCrewMembersTest() {
        Player player1 = new Player(0, null);
        CrewMember[] members = {new CrewMember(), new CrewMember(), new CrewMember(), new CrewMember(), new CrewMember(), new CrewMember()};
        assertArrayEquals(members, player1.getCrewMembers());
    }

    @Test
    public void hasSpaceTest() {
        Player player1 = new Player(0, null);
        assertTrue(player1.hasSpace());
        CrewMember[] members = player1.getCrewMembers();
        for (int i = 0; i < members.length; i++) {
            members[i] = CrewMember.Alcuin;
        }
        assertFalse(player1.hasSpace());
    }
}
