import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for Player class.
 *
 * @author Fareen Lavji
 * @version 11.09.2020
 *
 * @author Fareen. L
 * @version 12.07.2020
 */
public class PlayerTest {

    Player player;
    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
    }

    @After
    public void tearDown() throws Exception {
        player = null;
        assertNull(player);
    }

    @Test
    public void getArmyCount() {
        // act and assert
        assertEquals(0, player.getArmyCount());
    }

    @Test
    public void setArmyCount() {
        // setup
        player.setArmyCount(5);

        // act and assert
        assertEquals(5, player.getArmyCount());
    }

    @Test
    public void getName() {
        // act and assert
        assertEquals("Player", player.getName());
    }

    @Test
    public void getPlayerTurn() {
        // act and assert
        assertEquals(null, player.getPlayerTurn());
    }

    @Test
    public void getOwnedCountries() {
        // act and assert
        assertEquals(0, player.getOwnedCountries().size());
    }

    @Test
    public void addNewCountry() {
        // setup
        player.addCountry(new Country("SouthernEurope", "Europe"));
        player.addCountry(new Country("China", "Asia"));

        // act and assert
        assertEquals(2, player.getOwnedCountries().size());
    }
}