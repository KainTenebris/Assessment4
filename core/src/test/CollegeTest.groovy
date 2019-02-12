package test

import com.badlogic.gdx.Gdx
import com.rear_admirals.york_pirates.College
import org.junit.Test

class CollegeTest extends GroovyTestCase {

    //testing whether the new colleges are instantialized within College
    @Test
    public void testAlcuinHalifaxPresent(){
        College tester = new College();
        Gdx.graphics mockGraphics = Mockito.mock(Gdx.graphics.class);

        assertNotNull(tester.Halifax)
        assertNotNull(tester.Alcuin)
    }

}
