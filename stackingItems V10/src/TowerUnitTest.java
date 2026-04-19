import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tower.StackItem;
import tower.Tower;

/**
 * The test class TowerUnitTest.
 *
 * @author  William Santiago Ruiz Medina
 * @version 6 - Corregido para usar StackItem[]
 */
public class TowerUnitTest
{
    
    private void assertEqualsInt(int expected, int actual, String msg) {
        if (expected != actual) throw new RuntimeException(msg + " expected=" + expected + " actual=" + actual);
    }
    
    // CORREGIDO: ahora recibe StackItem[] en lugar de String[][]
    private int countType(StackItem[] items, String type) {
        int c = 0;
        for (StackItem item : items) {
            if (item.getType().equals(type)) c++;
        }
        return c;
    }

    
    @Test
    public void shouldCreateTower() {
        Tower t = new Tower(10, 30);
        assertTrue(t != null, "shouldCreateTower");
    }
    
    @Test
    public void shouldAddCupAndIncreaseHeight() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        int h0 = t.height();
        t.pushCup(3);
        assertEqualsInt(h0 + 3, t.height(), "shouldAddCupAndIncreaseHeight");
    }
    
    @Test
    public void shouldRemoveCupAndDecreaseHeight() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(3);
        t.pushCup(4);
        int h0 = t.height();
        t.removeCup(0);
        assertEqualsInt(h0 - 3, t.height(), "shouldRemoveCupAndDecreaseHeight");
    }
    
    @Test
    public void shouldAddLidAndIncreaseHeightByOne() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(5);
        int h0 = t.height();
        t.pushLid(0);
        assertEqualsInt(h0 + 1, t.height(), "shouldAddLidAndIncreaseHeightByOne");
        assertTrue(countType(t.stackingItems(), "lid") == 1, "shouldAddLidAndIncreaseHeightByOne lid exists");
    }
    
    /**
     * Verificar función liddedCups()
     * Debe retornar números de las tazas tapadas (blockSize) y no su id 
     * Verifica que estén ordenados de menor a mayor 
     */
    @Test
    public void shouldReturnLiddedCupsByBlockSize() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(5);
        t.pushCup(3);
        t.pushLid(0);
        t.pushLid(1);
        int[] lidded = t.liddedCups();
        assertEqualsInt(2, lidded.length, "liddedCups length");
        assertEqualsInt(3, lidded[0], "liddedCups first");
        assertEqualsInt(5, lidded[1], "liddedCups second");
    }
    
    /**
     * Verificar que liddedCups() retorne array vacio cuando ninguna taza tiene tapa 
     */
    @Test
    public void shouldReturnEmptyWhenNoLids() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(4);
        int[] lidded = t.liddedCups();
        assertEqualsInt(0, lidded.length, "liddedCups empty");
    }
    
    /**
     * Verificar que al agregar tapa con pushLid(), debe quedar encima de su taza en el stack 
     * CORREGIDO: usa StackItem[] en lugar de String[][]
     */
    @Test
    public void shouldPlaceLidDirectlyAboveCup() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(3);
        t.pushCup(5);
        t.pushCup(7);
        t.pushLid(1); 
        StackItem[] items = t.stackingItems();
        assertEqualsInt(4, items.length, "stack size");
        assertEquals("cup", items[0].getType(), "pos0 type");
        assertEquals(3, items[0].getBlockSize(), "pos0 size");
        assertEquals("cup", items[1].getType(), "pos1 type");
        assertEquals(5, items[1].getBlockSize(), "pos1 size");
        assertEquals("lid", items[2].getType(), "pos2 type");
        assertEquals(5, items[2].getBlockSize(), "pos2 size");
        assertEquals("cup", items[3].getType(), "pos3 type");
        assertEquals(7, items[3].getBlockSize(), "pos3 size");
    }
    
    /**
     * Verificar que cover() tape todas las tazas que no tengan tapa si hay espacio en Tower
     * Crea 2 tazas sin tapa, llama cover() y ve que liddedCups() retorne una tapa por taza
     */
    @Test
    public void shouldCoverAllCupsWithSpace() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(3);
        t.pushCup(5);
        t.cover();
        int[] lidded = t.liddedCups();
        assertEqualsInt(2, lidded.length, "cover: all cups lidded");
    }
    
    /**
     * Cover() no debe poner más tapas a una taza que ya tiene una 
     */
    @Test
    public void shouldNotCoverAlreadyLiddedCups() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(3);
        t.pushLid(0);
        t.pushCup(5);
        t.cover();
        int[] lidded = t.liddedCups();
        assertEqualsInt(2, lidded.length, "cover: no duplicate lids");
    }
    
    /**
     * popCup() tests 
     */
    @Test
    public void shouldPopTopCup() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(3);
        t.pushCup(5);
        int h0 = t.height();
        t.popCup();
        assertEqualsInt(h0 - 5, t.height(), "popCup: height decreases");
    }

    @Test
    public void shouldPopCupWithLid() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(3);
        t.pushCup(5);
        t.pushLid(1);
        int h0 = t.height();
        t.popCup();
        assertEqualsInt(h0 - 6, t.height(), "popCup: removes cup and lid");
    }
    
    /**
     * popLid() Tests
     */
    @Test
    public void shouldPopTopLid() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(5);
        t.pushLid(0);
        int h0 = t.height();
        t.popLid();
        assertEqualsInt(h0 - 1, t.height(), "popLid: height decreases by 1");
    }

    @Test
    public void shouldFailPopLidWhenNoLids() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(5);
        t.popLid();
        assertTrue(!t.ok(), "popLid: ok false when no lids");
    }

    /**
     * swapToReduce TEST
     * Verificar que la función retorne null y deje ok en false ya que no hay intercambios 
     * posibles que reduzcan la altura de Tower. 
     * Crea 2 tazas sin tapa
     */
    @Test
    public void shouldReturnNullWhenNoSwapReduces() {
        Tower t = new Tower(10, 30);
        t.makeInvisible();
        t.pushCup(3);
        t.pushCup(5);
        String[][] result = t.swapToReduce();
        assertTrue(!t.ok(), "swapToReduce: null when no reduction possible");
    }
    
    @AfterEach
    public void tearDown() {
        // No necesita hacer nada por ahora
    }
}