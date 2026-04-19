

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tower.TowerContest;

/**
 * The test class TowerContestCTest.
 *Clase de casos de prueba comunes para TowerContest.
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TowerContestCTest
{
    private TowerContest tc;
    /**
     * Default constructor for test class TowerContestCTest
     */
    public TowerContestCTest()
    {
        
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        tc = new TowerContest();
    }
    
    /**
     * Verifica que solve() use todas las tazas disponibles 
     * n=2 se crean tazas con h = 1 y h=3 
     * deben haber exactamente 2 elementos 
     */
    @Test
    public void accordingWSRMShouldUseAllCupsWithTwoCups() {
        String result = tc.solve(2, 3);
        String[] parts = result.split(" ");
        assertEquals(2, parts.length, "all 2 cups must be used");
    }
    
    /**
     * solve() debe retornar "impossible" si h supera maxHeight posible
     * n=10 cups == 1,3,5,7,9,11,13,15,17,19
     * maxCup= 19 
     * maxHeight = 28 == 19+9
     * h=30 
     * @return impossibe 
     */
    @Test 
    public void accordingWSRMShouldReturnImpossibleWithTenCupsWrongHeight() {
    assertEquals("impossible", tc.solve(10, 30), "h=30 with 10 cups = impossible");
    }
    
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        tc = null;
    }
}