package test

import com.rear_admirals.york_pirates.PirateGame
import org.junit.Test

class PirateGameTest extends GroovyTestCase {

    //tests to see whether the new philosophy department has instantialized itself correctly
    @Test
    public void testPhilosophyInstantialized(){
        PirateGame tester = new PirateGame();

        tester.create();
        AssertNotNull(tester.Philosophy);
        AssertEquals(tester.Philosophy.name,"Philosophy");
        AssertEquals(tester.Philosophy.product,Accuracy);
        AssertEquals(tester.Philosophy.pirateGame, tester);
    }
}
