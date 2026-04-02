import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
/**
 * Pruebas unitarias para el Ciclo 4.
 * Cubre los nuevos tipos de tazas y tapas: OpenerCup, HierarchicalCup,
 * VanishingCup, FearfulLid y CrazyLid.
 * Todas las pruebas corren en modo invisible.
 *
 * @author William Santiago Ruiz, Jonatan Palomares
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
 
    // ------------------------------------------------------------------ //
    //  OpenerCup                                                           //
    // ------------------------------------------------------------------ //
 
    /**
     * OpenerCup debe entrar a la torre correctamente.
     */
    @Test
    public void shouldPushOpenerCup() {
        tower.pushCup("opener", 5);
        assertTrue(tower.ok(), "OpenerCup should enter the tower");
    }
 
    /**
     * OpenerCup debe eliminar todas las tapas antes de entrar.
     */
    @Test
    public void shouldOpenerCupRemoveAllLids() {
        tower.pushCup(3);
        tower.pushLid(0);
        tower.pushCup(5);
        tower.pushLid(1);
        int[] liddedBefore = tower.liddedCups();
        assertEquals(2, liddedBefore.length, "should have 2 lids before opener");
        tower.pushCup("opener", 7);
        int[] liddedAfter = tower.liddedCups();
        assertEquals(0, liddedAfter.length, "opener should remove all lids");
    }
 
    /**
     * OpenerCup debe tener tipo "opener" en stackingItems.
     */
    @Test
    public void shouldOpenerCupHaveCorrectType() {
        tower.pushCup("opener", 5);
        String[][] items = tower.stackingItems();
        assertEquals("opener", items[0][0], "type should be opener");
    }
 
    // ------------------------------------------------------------------ //
    //  HierarchicalCup                                                     //
    // ------------------------------------------------------------------ //
 
    /**
     * HierarchicalCup debe entrar correctamente.
     */
    @Test
    public void shouldPushHierarchicalCup() {
        tower.pushCup("hierarchical", 7);
        assertTrue(tower.ok(), "HierarchicalCup should enter the tower");
    }
 
    /**
     * HierarchicalCup debe desplazar objetos de menor tamanio al entrar.
     */
    @Test
    public void shouldHierarchicalCupDisplaceSmaller() {
        tower.pushCup(3);
        tower.pushCup(1);
        tower.pushCup("hierarchical", 5);
        String[][] items = tower.stackingItems();
        assertEquals("hierarchical", items[0][0], "hierarchical should be at bottom");
        assertEquals("5", items[0][1], "hierarchical size should be 5");
    }
 
    /**
     * HierarchicalCup que llego al fondo no debe poder quitarse.
     */
    @Test
    public void shouldNotRemoveHierarchicalCupAtBottom() {
        Tower t = new Tower(10, 7);
        t.makeInvisible();
        t.pushCup("hierarchical", 7);
        t.removeCup(0);
        assertFalse(t.ok(), "HierarchicalCup at bottom should not be removable");
    }
 
    /**
     * HierarchicalCup que no llego al fondo si debe poderse quitar.
     */
    @Test
    public void shouldRemoveHierarchicalCupNotAtBottom() {
        tower.pushCup("hierarchical", 5);
        tower.removeCup(0);
        assertTrue(tower.ok(), "HierarchicalCup not at bottom should be removable");
    }
 
    // ------------------------------------------------------------------ //
    //  VanishingCup                                                        //
    // ------------------------------------------------------------------ //
 
    /**
     * VanishingCup debe entrar correctamente.
     */
    @Test
    public void shouldPushVanishingCup() {
        tower.pushCup("vanishing", 5);
        assertTrue(tower.ok(), "VanishingCup should enter the tower");
    }
 
    /**
     * VanishingCup debe desaparecer al invertir la torre.
     */
    @Test
    public void shouldVanishingCupDisappearOnReverse() {
        tower.pushCup(3);
        tower.pushCup("vanishing", 5);
        int cupsBefore = tower.stackingItems().length;
        tower.reverseTower();
        int cupsAfter = tower.stackingItems().length;
        assertTrue(cupsAfter < cupsBefore, "VanishingCup should disappear on reverse");
    }
 
    /**
     * VanishingCup debe tener tipo "vanishing" en stackingItems.
     */
    @Test
    public void shouldVanishingCupHaveCorrectType() {
        tower.pushCup("vanishing", 5);
        String[][] items = tower.stackingItems();
        assertEquals("vanishing", items[0][0], "type should be vanishing");
    }
 
    // ------------------------------------------------------------------ //
    //  FearfulLid                                                          //
    // ------------------------------------------------------------------ //
 
    /**
     * FearfulLid debe entrar si su taza esta en la torre.
     */
    @Test
    public void shouldFearfulLidEnterWhenCupPresent() {
        tower.pushCup(5);
        tower.pushLid("fearful", 0);
        assertTrue(tower.ok(), "FearfulLid should enter when cup is present");
    }
 
    /**
     * FearfulLid no debe poder salir mientras tapa a su taza.
     */
    @Test
    public void shouldFearfulLidNotPopWhileCovering() {
        tower.pushCup(5);
        tower.pushLid("fearful", 0);
        tower.popLid();
        assertFalse(tower.ok(), "FearfulLid should not pop while covering its cup");
    }
 
    /**
     * FearfulLid debe tener tipo "fearful" en stackingItems.
     */
    @Test
    public void shouldFearfulLidHaveCorrectType() {
        tower.pushCup(5);
        tower.pushLid("fearful", 0);
        String[][] items = tower.stackingItems();
        assertEquals("fearful", items[1][0], "type should be fearful");
    }
 
    // ------------------------------------------------------------------ //
    //  CrazyLid                                                            //
    // ------------------------------------------------------------------ //
 
    /**
     * CrazyLid debe ubicarse en la base de la torre.
     */
    @Test
    public void shouldCrazyLidGoToBase() {
        tower.pushCup(5);
        tower.pushCup(3);
        tower.pushLid("crazy", 1);
        String[][] items = tower.stackingItems();
        assertEquals("crazy", items[0][0], "CrazyLid should be at the base");
    }
 
    /**
     * CrazyLid debe tener tipo "crazy" en stackingItems.
     */
    @Test
    public void shouldCrazyLidHaveCorrectType() {
        tower.pushCup(5);
        tower.pushLid("crazy", 0);
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("crazy")) {
                found = true;
            }
        }
        assertTrue(found, "CrazyLid type should be crazy");
    }
 
    /**
     * pushCup con tipo desconocido debe dejar ok en false.
     */
    @Test
    public void shouldFailWithUnknownCupType() {
        tower.pushCup("unknown", 5);
        assertFalse(tower.ok(), "unknown cup type should fail");
    }
}