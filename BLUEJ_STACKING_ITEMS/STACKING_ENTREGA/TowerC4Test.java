import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para el Ciclo 4.
 * Cubre los nuevos tipos de tazas y tapas: openerCup, hierarchicalCup,
 * VanishingCup, fearfulLid y crazyLid.
 *
 * @author William Santiago Ruiz/ Sergio
 * @version 4.0 (Ciclo 4)
 */
public class TowerC4Test {

    private Tower tower;

    @BeforeEach
    public void setUp() {
        tower = new Tower(20, 60);
        tower.makeInvisible();
    }

    @AfterEach
    public void tearDown() {
        tower = null;
    }

    private String typeAt(int idx) {
        return tower.stackingItems()[idx].getType();
    }

    private int blockAt(int idx) {
        return tower.stackingItems()[idx].getBlockSize();
    }

    // ================================================================== //
    //  openerCup                                                          //
    // ================================================================== //

    @Test
    public void shouldPushOpenerCup() {
        tower.pushCup("opener", 5);
        assertTrue(tower.ok(), "openerCup should enter the tower");
    }

    @Test
    public void shouldOpenerCupRemoveAllLids() {
        tower.pushCup(3);
        tower.pushLid(0);
        tower.pushCup(5);
        tower.pushLid(1);
        assertEquals(2, tower.liddedCups().length);
        tower.pushCup("opener", 7);
        assertEquals(0, tower.liddedCups().length);
    }

    @Test
    public void shouldOpenerCupHaveCorrectType() {
        tower.pushCup("opener", 5);
        assertEquals("opener", typeAt(0));
    }

    // ================================================================== //
    //  hierarchicalCup                                                    //
    // ================================================================== //

    @Test
    public void shouldPushHierarchicalCup() {
        tower.pushCup("hierarchical", 7);
        assertTrue(tower.ok());
    }

    @Test
    public void shouldHierarchicalCupDisplaceSmaller() {
        tower.pushCup(3);
        tower.pushCup(1);
        tower.pushCup("hierarchical", 5);
        assertEquals("hierarchical", typeAt(0));
        assertEquals(5, blockAt(0));
    }

    @Test
    public void shouldNotRemoveHierarchicalCupAtBottom() {
        Tower t = new Tower(10, 7);
        t.makeInvisible();
        t.pushCup("hierarchical", 7);
        t.removeCup(0);
        assertFalse(t.ok());
    }

    @Test
    public void shouldRemoveHierarchicalCupNotAtBottom() {
        tower.pushCup("hierarchical", 5);
        tower.removeCup(0);
        assertTrue(tower.ok());
    }

    // ================================================================== //
    //  VanishingCup                                                       //
    // ================================================================== //

    @Test
    public void shouldPushVanishingCup() {
        tower.pushCup("Vanishing", 5);
        assertTrue(tower.ok());
    }

    @Test
    public void shouldVanishingCupDisappearOnReverse() {
        tower.pushCup(3);
        tower.pushCup("Vanishing", 5);
        int itemsBefore = tower.stackingItems().length;
        tower.reverseTower();
        int itemsAfter = tower.stackingItems().length;
        assertTrue(itemsAfter < itemsBefore);
    }

    @Test
    public void shouldVanishingCupHaveCorrectType() {
        tower.pushCup("Vanishing", 5);
        assertEquals("Vanishing", typeAt(0));
    }

    // ================================================================== //
    //  fearfulLid                                                         //
    // ================================================================== //

    @Test
    public void shouldFearfulLidEnterWhenCupPresent() {
        tower.pushCup(5);
        tower.pushLid("fearful", 0);
        assertTrue(tower.ok());
    }

    @Test
    public void shouldFearfulLidNotPopWhileCovering() {
        tower.pushCup(5);
        tower.pushLid("fearful", 0);
        tower.popLid();
        assertFalse(tower.ok());
    }

    @Test
    public void shouldFearfulLidHaveCorrectType() {
        tower.pushCup(5);
        tower.pushLid("fearful", 0);
        assertEquals("fearful", typeAt(1));
    }

    // ================================================================== //
    //  crazyLid                                                           //
    // ================================================================== //

    @Test
    public void shouldCrazyLidGoToBase() {
        tower.pushCup(5);
        tower.pushCup(3);
        tower.pushLid("crazy", 1);
        assertEquals("crazy", typeAt(0));
    }

    @Test
    public void shouldCrazyLidHaveCorrectType() {
        tower.pushCup(5);
        tower.pushLid("crazy", 0);
        boolean found = false;
        for (StackItem item : tower.stackingItems()) {
            if (item.getType().equals("crazy")) { found = true; break; }
        }
        assertTrue(found);
    }

    // ================================================================== //
    //  Polimorfismo                                                       //
    // ================================================================== //

    @Test
    public void shouldStackingItemsReturnPolymorphicArray() {
        tower.pushCup(3);
        tower.pushLid(0);
        tower.pushCup("opener", 5);
        StackItem[] items = tower.stackingItems();
        assertTrue(items.length >= 2);
        for (StackItem item : items) {
            assertNotNull(item.getType());
            assertTrue(item.getBlockSize() > 0);
            assertTrue(item.isCupVariant() || item.getType().equals("lid")
                || item.getType().equals("fearful") || item.getType().equals("crazy"),
                "type should be valid: " + item.getType());
        }
    }

    @Test
    public void shouldFailWithUnknownCupType() {
        tower.pushCup("unknown", 5);
        assertFalse(tower.ok());
    }
}