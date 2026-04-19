import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TowerCC4Test {

    private Tower tower;

    @BeforeEach
    public void setUp() {
        tower = new Tower(20, 100);
        tower.makeInvisible();
    }

    @AfterEach
    public void tearDown() {
        tower = null;
    }

    /**
     * OpenerCup debe eliminar multiples tapas de diferentes tazas antes de entrar.
     */
    @Test
    public void accordingWSRMShouldOpenerRemoveMultipleLids() {
        tower.pushCup(3);
        tower.pushLid(0);
        tower.pushCup(5);
        tower.pushLid(1);
        tower.pushCup(7);
        tower.pushLid(2);
        tower.pushCup("opener", 9);
        assertEquals(0, tower.liddedCups().length, "opener should remove all lids");
    }

    /**
     * HierarchicalCup debe desplazar solo las tazas menores, no las mayores.
     * CORREGIDO: usa StackItem[] en lugar de String[][]
     */
    @Test
    public void accordingWSRMShouldHierarchicalOnlyDisplaceSmaller() {
        tower.pushCup(7);
        tower.pushCup(3);
        tower.pushCup(1);
        tower.pushCup("hierarchical", 5);
        StackItem[] items = tower.stackingItems();
        assertEquals("hierarchical", items[0].getType(), "hierarchical should be at bottom");
        assertEquals(5, items[0].getBlockSize(), "hierarchical size should be 5");
        assertEquals(7, items[1].getBlockSize(), "cup 7 should stay above hierarchical");
    }

    /**
     * Multiples VanishingCups deben desaparecer todas al invertir.
     */
    @Test
    public void accordingWSRMShouldAllVanishingCupsDisappearOnReverse() {
        tower.pushCup("Vanishing", 3);
        tower.pushCup("Vanishing", 5);
        tower.pushCup(7);
        int before = tower.stackingItems().length;
        tower.reverseTower();
        int after = tower.stackingItems().length;
        assertEquals(before - 2, after, "both vanishing cups should disappear");
    }

    /**
     * FearfulLid debe entrar si su taza esta en la torre.
     */
    @Test
    public void accordingWSRMShouldFearfulLidEnterWhenCupPresent() {
        tower.pushCup(5);
        tower.pushLid("fearful", 0);
        assertTrue(tower.ok(), "fearfulLid should enter when cup is present");
        assertEquals(1, tower.liddedCups().length, "cup should be lidded");
    }   
    
    /**
     * CrazyLid debe ir a la base incluso si hay otras tazas y tapas.
     * CORREGIDO: usa StackItem[] en lugar de String[][]
     */
    @Test
    public void accordingWSRMShouldCrazyLidAlwaysGoToBase() {
        tower.pushCup(3);
        tower.pushLid(0);
        tower.pushCup(5);
        tower.pushCup(7);
        tower.pushLid("crazy", 2);
        StackItem[] items = tower.stackingItems();
        assertEquals("crazy", items[0].getType(), "crazyLid should always be at base");
    }

    /**
     * OpenerCup no debe afectar tazas sin tapa.
     */
    @Test
    public void accordingWSRMShouldOpenerNotAffectCupsWithoutLid() {
        tower.pushCup(3);
        tower.pushCup(5);
        tower.pushCup("opener", 7);
        assertEquals(3, tower.stackingItems().length, "opener should not remove cups");
        assertTrue(tower.ok(), "opener should succeed");
    }

    /**
     * HierarchicalCup al fondo no debe poder quitarse ni con removeCup ni con popCup.
     */
    @Test
    public void accordingWSRMShouldHierarchicalAtBottomResistBothRemoveMethods() {
        Tower t = new Tower(10, 7);
        t.makeInvisible();
        t.pushCup("hierarchical", 7);
        t.removeCup(0);
        assertFalse(t.ok(), "removeCup should fail");
        t.popCup();
        assertFalse(t.ok(), "popCup should also fail");
    }
}