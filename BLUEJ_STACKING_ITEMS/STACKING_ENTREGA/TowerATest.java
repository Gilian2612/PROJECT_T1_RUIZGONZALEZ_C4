import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.JOptionPane;
/**
 * Pruebas de aceptacion para el Ciclo 4.
 * Cada prueba muestra visualmente el comportamiento y pregunta al usuario si lo acepta.
 * William Santiago Ruiz , Sergio Gonzalez
 */


public class TowerATest {

    /**
     * Prueba de aceptacion 1: OpenerCup elimina todas las tapas al entrar.
     * Paso 1: Se crean 3 tazas.
     * Paso 2: Se tapan las 3 tazas.
     * Paso 3: Entra una OpenerCup que elimina todas las tapas.
     */
    @Test
    public void acceptanceTestOpenerCupRemovesAllLids() throws InterruptedException {
        Tower tower = new Tower(20, 80);
        tower.makeVisible();
        Thread.sleep(1000);

        JOptionPane.showMessageDialog(null,
            "Paso 1: Se van a agregar 3 tazas a la torre.",
            "Prueba de Aceptacion 1 - OpenerCup", JOptionPane.INFORMATION_MESSAGE);

        tower.pushCup(3);
        Thread.sleep(800);
        tower.pushCup(5);
        Thread.sleep(800);
        tower.pushCup(7);
        Thread.sleep(800);

        JOptionPane.showMessageDialog(null,
            "Paso 2: Se van a tapar las 3 tazas.",
            "Prueba de Aceptacion 1 - OpenerCup", JOptionPane.INFORMATION_MESSAGE);

        tower.pushLid(0);
        Thread.sleep(800);
        tower.pushLid(1);
        Thread.sleep(800);
        tower.pushLid(2);
        Thread.sleep(800);

        JOptionPane.showMessageDialog(null,
            "Paso 3: Va a entrar una OpenerCup de tamanio 9.\nDebe eliminar TODAS las tapas.",
            "Prueba de Aceptacion 1 - OpenerCup", JOptionPane.INFORMATION_MESSAGE);

        tower.pushCup("opener", 9);
        Thread.sleep(1500);

        int result = JOptionPane.showConfirmDialog(null,
            "La OpenerCup entro y elimino todas las tapas?\nAceptar = prueba exitosa",
            "Prueba de Aceptacion 1 - OpenerCup",
            JOptionPane.YES_NO_OPTION);

        tower.exit();
        assertEquals(JOptionPane.YES_OPTION, result, "User did not accept the test");
    }

    /**
     * Prueba de aceptacion 2: HierarchicalCup desplaza tazas menores y no se puede quitar.
     * Paso 1: Se crean 3 tazas de diferentes tamanios.
     * Paso 2: Entra una HierarchicalCup que desplaza las menores.
     * Paso 3: Se intenta quitar la HierarchicalCup y no puede.
     */
    @Test
    public void acceptanceTestHierarchicalCupDisplacesAndStays() throws InterruptedException {
        Tower t = new Tower(10, 20);
        t.makeVisible();
        Thread.sleep(1000);

        JOptionPane.showMessageDialog(null,
            "Paso 1: Se va a agregar una taza normal de tamaño 3 y una de tamaño 1.",
            "Prueba de Aceptacion 2 - HierarchicalCup", JOptionPane.INFORMATION_MESSAGE);

        t.pushCup(3);
        Thread.sleep(800);
        t.pushCup(1);
        Thread.sleep(800);

        JOptionPane.showMessageDialog(null,
            "Paso 2: Va a entrar una HierarchicalCup de tamaño 7.\n" +
            "Debe desplazar las tazas de 3 y 1 hacia arriba y llegar al fondo.",
            "Prueba de Aceptacion 2 - HierarchicalCup", JOptionPane.INFORMATION_MESSAGE);

        t.pushCup("hierarchical", 7);
        Thread.sleep(1500);

        JOptionPane.showMessageDialog(null,
            "Paso 3: Se va a intentar quitar la HierarchicalCup.\n" +
            "Como llego al fondo, NO debe poder quitarse.",
            "Prueba de Aceptacion 2 - HierarchicalCup", JOptionPane.INFORMATION_MESSAGE);

        t.removeCup(0);
        Thread.sleep(800);

        int result = JOptionPane.showConfirmDialog(null,
            "La HierarchicalCup llego al fondo y no se pudo quitar?\nAceptar = prueba exitosa",
            "Prueba de Aceptacion 2 - HierarchicalCup",
            JOptionPane.YES_NO_OPTION);

        t.exit();
        assertEquals(JOptionPane.YES_OPTION, result, "User did not accept the test");
    }
}
 