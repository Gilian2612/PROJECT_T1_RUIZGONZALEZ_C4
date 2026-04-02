

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TowerContestTest.
 *
 * @author  William Ruiz 
 * @version 3
 */
public class TowerContestTest
{
    private TowerContest tc;

    /**
     * Default constructor for test class TowerContestTest
     */
    public TowerContestTest()
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
    @Test
    public void shouldReturnImpossibleWhenHeightTooLarge() {
        assertEquals("impossible", tc.solve(4, 100), "h too large should return impossible");
    }

    @Test
    public void shouldReturnImpossibleWhenHeightTooSmall() {
        assertEquals("impossible", tc.solve(3, 2), "h < minimum should return impossible");
    }

    @Test
    public void shouldSolveMinimumHeight() {
        String result = tc.solve(5, 9);
        assertNotNull(result, "minHeight should not be null");
        assertNotEquals("impossible", result, "minimum height should have a solution");
    }

    @Test
    public void shouldSolveMaximumHeight() {
        String result = tc.solve(3, 7);
        assertNotNull(result, "maximum h should not be null");
        assertNotEquals("impossible", result, "maximum h should have a solution");
    }

    @Test
    public void shouldSolveSampleCase() {
        String result = tc.solve(4, 9);
        assertNotEquals("impossible", result, "sample case should have a solution");
    }

    @Test
    public void shouldReturnCorrectHeightForSampleCase() {
        String result = tc.solve(4, 9);
        assertEquals(9, calculateHeight(result), " h== 9");
    }

    @Test
    public void shouldSolveWithOneCup() {
        String result = tc.solve(1, 1);
        assertEquals("1", result, "one cup = return h==1");
    }

    @Test
    public void shouldReturnImpossibleWithOneCupWrongHeight() {
        assertEquals("impossible", tc.solve(1, 5), "1 cup with wrong h should return impossible");
    }

    @Test
    public void shouldUseAllCups() {
        String result = tc.solve(4, 9);
        String[] parts = result.split(" ");
        assertEquals(4, parts.length, "must use all cups");
    }
    
    private int calculateHeight(String order) {
        String[] parts = order.split(" ");
        int height = Integer.parseInt(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            int current = Integer.parseInt(parts[i]);
            int prev = Integer.parseInt(parts[i - 1]);
            if (current > prev) {
                height += 1;
            }
        }
        return height;
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

