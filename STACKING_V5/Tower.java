import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;

    /**
     * Clase principal del simulador de apilar tazas y tapas
     * Gestiona Tower, las tazas y sus tapas, y proporciona funcionalidades
     * para apilar, ordenar y consultar información.
     *
     * version 3.0 (Ciclo 1) 
     *
     */

public class Tower {

    private int width;
    private int height;
    private boolean visible;
    private boolean ok;
    private boolean active;
    private int margin;

    private ArrayList<Cup> cups;
    private ArrayList<StackItem> stack;
    private TowerFrame frame;

    private static final int DEFAULT_MARGIN = 50;
    private static final int PIXELS_PER_CM = 10;
    private static final int LID_HEIGHT_CM = 1;
    private static final String[] COLORS = {"red", "blue", "yellow", "green", "magenta"};

    private int colors = 0;
    private int nextId = 0; 
    
    /**
     * A continuación se crea el constructor de la clase Tower 
     * Se encarga de crear una torre con width y height determinados 
     * @param width 
     * @param maxHeight 
     */
    
    public Tower(int width, int maxHeight) {
        if (width <= 0 || maxHeight <= 0) {
            ok = false;
            showMessage("Error: dimensions <= 0.");
            return;
        }
        this.width = width;
        this.height = maxHeight;
        this.visible = false;
        this.ok = true;
        this.active = true;
        this.margin = DEFAULT_MARGIN;
        this.cups = new ArrayList<Cup>();
        this.stack = new ArrayList<StackItem>();
        this.frame = new TowerFrame(width, maxHeight, DEFAULT_MARGIN);
    }

    
    /**
     * @CICLO 2 
     * Requisito 10: Se va a crear un nuevo contructor de Tower que no incluya lids
     * Finaliza el simulador dejandolo invisible
     */
    public Tower(int cupsCount) {
        this(10, Math.max(10, cupsCount * 10));
        this.margin = 10;
        this.frame = new TowerFrame(width, height, this.margin);
        for (int k = 1; k <= cupsCount; k++) {
            pushCup(2 * k - 1);
        }
    }
    
    /**
     * @CICLO 2
     * Requisito 11
     * Intercambia posición de objetos entre la torre (swap)
     */
    private int numberOf(StackEntry e) {
        if (e.getType().equals("cup")) return e.getCup().getBlockSize();
        if (e.getType().equals("lid")) return e.getLid().getBlockSize();
        return -1;
    }

    private int findIndexOf(String type, int blockSize) {
        for (int i = 0; i < stack.size(); i++) {
            StackItem e = stack.get(i);
            if (e.getType().equals(type) && e.getBlockSize() == blockSize) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * no permite hacer instanciamiento ya que le entra un array (toca desde terminal) 
     * se va a sobrecargar el método para le presentación de ciclo 
     */
    public void swap(String[] o1, String[] o2) {
        if (!active) {
            showMessage("Simulator finished");
            ok = false;
            return;
        }
        if (o1 == null || o2 == null || o1.length < 2 || o2.length < 2) {
            showMessage("invalid objects");
            ok = false;
            return;
        }
        int i1 = findIndexOf(o1[0], Integer.parseInt(o1[1]));
        int i2 = findIndexOf(o2[0], Integer.parseInt(o2[1]));
        if (i1 == -1 || i2 == -1) {
            showMessage("object not found");
            ok = false;
            return;
        }
        Collections.swap(stack, i1, i2);
        normalizeStack();
        repositionAllItems();
        ok = true;
    }
    
    /**
     * Swap sobrecargado para las pruebas 
     */
    public void swap(String type1, String num1, String type2, String num2) {
        swap(new String[]{type1, num1}, new String[]{type2, num2});
    }
    
    
    /**
     * @CICLO 2
     * Requisito 12
     */
    public void cover() {
        if (!active) {
            showMessage("Simulator finished");
            ok = false;
            return;
        }
        
        boolean anyCovered = false;
        
        for (int i = 0; i < cups.size(); i++) {
            Cup cup = cups.get(i);
            if (!cup.hasLid() && calculateCurrentHeight() + LID_HEIGHT_CM <= height) {
                pushLid(i);
                anyCovered = true;
            }
        }
        if (!anyCovered) {
            showMessage("No cups to cover");
            ok = false;
            return;
        }
        ok = true;
    }
 
    
    
    /**
     * Se debe hacer +1 taza en Tower 
     * @param blockSize 
     */
    
    public void pushCup(int blockSize) {
        if (!active) {
            showMessage("Simulator Finished");
            return;
        }
        if (blockSize > width) {
            showMessage("The cup cannot be wider than the tower");
            ok = false;
            return;
        }
        if (calculateCurrentHeight() + blockSize > height) {
            showMessage("Tower doesn't have enough space");
            ok = false;
            return;
        }
        Cup cup = new Cup(getNextColor(), blockSize, nextId++);
        cups.add(cup);
        stack.add(cup);
        repositionAllItems();
        if (visible) {
            cup.makeVisible();
        }
        ok = true;
    }
    
    /**
     * Elimina una taza usando el índice (posición) dentro de la lista cups.
     */
    public void removeCup(int index) {
        if (!active) {
            showMessage("Simulator Finished");
            return;
        }
        if (index < 0 || index >= cups.size()) {
            showMessage("invalid index");
            ok = false;
            return;
        }
        Cup cup = cups.get(index);
        if (cup.hasLid()) {
            cup.getLid().makeInvisible();
            stack.remove(cup.getLid());
            cup.setLid(null);
        }
        cup.makeInvisible();
        stack.remove(cup);
        cups.remove(index);
        repositionAllItems();
        ok = true;
    }

    private String getNextColor() {
        return COLORS[colors++ % COLORS.length];
    }
    
    /**
     * repositionAllItems
     */
    private void repositionAllItems() {
        
        if (!visible) {
            return;
        }
        int xLeft = margin;
        int yTop = margin;
        int towerWidthPx = width * PIXELS_PER_CM;
        int towerHeightPx = height * PIXELS_PER_CM;
        int currentBottom = yTop + towerHeightPx;
 
        for (StackItem item : stack) {
            if (currentBottom < yTop) {
                break;
            }
            
            switch (item.getType()) {
                case "cup":
                    currentBottom = item.reposition(xLeft, currentBottom, towerWidthPx);
                    break;
                case "lid":
                    currentBottom -= item.heightCm() * PIXELS_PER_CM;
                    break;
                default:
                    throw new TowerException(item.getType());
            }
        }
    }
    
    
    
    
    
    /**
     * Se reconstruye el stack de tal forma que cada lid queda encima de su cup 
     * usa hasDependant() y getDependant() de StackItem
     */ 
    
    private void normalizeStack() {
        
        ArrayList<StackItem> newStack = new ArrayList<StackItem>();
        for (StackItem item : stack) {
            if (item.getType().equals("cup")) {
                newStack.add(item);
                
                if (item.hasDependant()) {
                    newStack.add(item.getDependant());
                    
                }
            }
        }
        
        stack = newStack;
    }
    
    
    /**
     * es un normalizeStack pero con una copia, se usa para el swapToReduce, ya que si se hace 
     * sobre el mismo stack no se efectua correctamenbte el método
     */
    private ArrayList<StackItem> normalizeStackCopy(ArrayList<StackItem> s) {
        ArrayList<StackItem> result = new ArrayList<StackItem>();
        for (StackItem item : s) {
            if (item.getType().equals("cup")) {
                result.add(item);
                if (item.hasDependant()) {
                    result.add(item.getDependant());
                }
            }
        }
        return result;
    }
    
    /**
     * Método auxiliar para swapToReduce 
     * Calcula la altura de un stack dado sin modificar el estado de Tower.
     * evalúa los posibles swaps que reduzcan la altura 
     */
     private int heightOfStack(ArrayList<StackItem> s) {
        int total = 0;
        for (StackItem item : s) {
            total += item.heightCm();
        }
        return total;
    }
    
    
    /**
     * swapToReduce 
     * 
     *Consulta el intercambio de dos objetos en el stack que produzca
     * la mayor reducción de altura de la torre, sin ejecutar el intercambio.
     * Prueba todos los pares posibles de objetos en el stack, simula el swap
     * de cada par, calcula la altura resultante y retorna el par cuyo
     * intercambio genera la mayor reducción.
     * si ningun intercambio reduce altura retorna null
     */
    public String[][] swapToReduce() {
        if (!active) {
            showMessage("Simulator finished");
            ok = false;
            return null;
        }
        normalizeStack();
        int currentHeight = calculateCurrentHeight();
        int bestReduction = 0;
        int bestI = -1;
        int bestJ = -1;
 
        for (int i = 0; i < stack.size(); i++) {
            for (int j = i + 1; j < stack.size(); j++) {
                ArrayList<StackItem> copy = new ArrayList<StackItem>(stack);
                Collections.swap(copy, i, j);
                copy = normalizeStackCopy(copy);
                int newHeight = heightOfStack(copy);
                int reduction = currentHeight - newHeight;
                if (reduction > bestReduction) {
                    bestReduction = reduction;
                    bestI = i;
                    bestJ = j;
                }
            }
        }
        if (bestI == -1) {
            showMessage("No swap reduces height");
            ok = false;
            return null;
        }
        StackItem e1 = stack.get(bestI);
        StackItem e2 = stack.get(bestJ);
        ok = true;
        return new String[][]{
            {e1.getType(), String.valueOf(e1.getBlockSize())},
            {e2.getType(), String.valueOf(e2.getBlockSize())}
        };
    }
    
    /**
    *popCup 
    */
    public void popCup() {
        if (!active) {
            showMessage("Simulator finished");
            ok = false;
            return;
        }
        
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).getType().equals("cup")) {
                removeCup(cups.indexOf((Cup) stack.get(i)));
                ok = true;
                return;
            }
        }
        
        showMessage("No cups in tower");
        ok = false;
    }

 
    /**
     * popLid 
     * Elimina la tapa que está en la parte de arriba del stack 
     */
    public void popLid() {
        if (!active) {
            showMessage("Simulator finished");
            ok = false;
            return;
        }
        
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).getType().equals("lid")) {
                Cup cup = findCupById(stack.get(i).getCupId());
                if (cup != null) {
                    removeLid(cups.indexOf(cup));
                }
                ok = true;
                return;
            }
        }
        
        showMessage("No lids in tower");
        ok = false;
    }




    /**
     * Para eliminar una taza
     * @param index hace referencia al índice de la taza
     */

    public void removeLid(int cupIndex) {
        if (!active) {
            showMessage("Simulator finished");
            return;
        }
        if (cupIndex < 0 || cupIndex >= cups.size()) {
            showMessage("invalid index");
            ok = false;
            return;
        }
        Cup cup = cups.get(cupIndex);
        if (!cup.hasLid()) {
            showMessage("cup has no lid");
            ok = false;
            return;
        }
        Lid lid = cup.getLid();
        lid.makeInvisible();
        stack.remove(lid);
        cup.setLid(null);
        repositionAllItems();
        ok = true;
    }
    
    /**
     * Para poner tapas en una taza 
     * @param cupIndex 
     */

    public void pushLid(int cupIndex) {
        if (!active) {
            showMessage("Simulator Finished");
            return;
        }
        if (cupIndex < 0 || cupIndex >= cups.size()) {
            showMessage("invalid cup index");
            ok = false;
            return;
        }
        Cup cup = cups.get(cupIndex);
        if (cup.hasLid()) {
            showMessage("cup already has a lid");
            ok = false;
            return;
        }
        if (calculateCurrentHeight() + LID_HEIGHT_CM > height) {
            showMessage("Not enough space for lid");
            ok = false;
            return;
        }
        Lid lid = new Lid(cup.getColor(), cup.getBlockSize(), cup.getId());
        cup.setLid(lid);
        int insertPos = stack.indexOf(cup) + 1;
        stack.add(insertPos, lid);
        if (visible) {
            lid.makeVisible();
        }
        repositionAllItems();
        ok = true;
    }

    
    /**
     * Invertir el orden de la torre
     * Se nornmaliza antes y después para que la tapa quede pegada a la taza 
     */
    public void reverseTower() {
        if (!active) {
            showMessage("Simulator finished");
            ok = false;
            return;
        }
        
        normalizeStack();
        Collections.reverse(stack);
        normalizeStack();
        repositionAllItems();
        ok = true;
    }
    
    private Cup findCupById(int id) {
        
        for (Cup cup : cups) {
            
            if (cup.getId() == id) {
                
                return cup;
            }
            
        }
        
        return null;
    }
    
    private int calculateCurrentHeight() {
        int total = 0;
        for (StackItem e : stack) {
            total += e.heightCm();
        }
        return total;
    }
    
    /**
     * Ordena la torre de mayor a menor con las fichas que caben 
     */
    public void orderTower() {
        if (!active) {
            showMessage("Simulator finished");
            ok = false;
            return;
        }
        
        normalizeStack();
        stack.sort((a, b) -> Integer.compare(b.getBlockSize(), a.getBlockSize()));
        normalizeStack();
        repositionAllItems();
        ok = true;
    }
    
    /**
     * h total 
     * 
     */
    public int height() {
        return calculateCurrentHeight();
    }
    
    /**
     * Método para hallar las tazas tapadas (se identifican con el id)
     */
    public int[] liddedCups() {
        ArrayList<Integer> sizes = new ArrayList<Integer>();
        
        for (Cup cup : cups) {
            if (cup.hasLid()) {
                sizes.add(cup.getBlockSize());
            }
        }
        
        Collections.sort(sizes);
        int[] result = new int[sizes.size()];
        for (int i = 0; i < sizes.size(); i++) {
            result[i] = sizes.get(i);
        }
        
        return result;
    }
    
    /**
     * De abajo hacia arriba retorna type y numero
     */
    
    public String[][] stackingItems() {
        normalizeStack();
        ArrayList<String[]> out = new ArrayList<String[]>();
        for (StackItem e : stack) {
            out.add(new String[]{e.getType(), String.valueOf(e.getBlockSize())});
        }
        String[][] result = new String[out.size()][2];
        for (int i = 0; i < out.size(); i++) {
            result[i] = out.get(i);
        }
        return result;
    }
    
    public void makeVisible() {
        visible = true;
        
        frame.makeVisible();
        for (Cup cup : cups) {
            cup.makeVisible();
            
            if (cup.hasLid()) {
                cup.getLid().makeVisible();
            }
        }
        
        repositionAllItems();
        ok = true;
    }

    public void makeInvisible() {
        visible = false;
        for (Cup cup : cups) {
            cup.makeInvisible();
            if (cup.hasLid()) {
                cup.getLid().makeInvisible();
            }
        }
        
        frame.makeInvisible();
        
        ok = true;
    }
    
    /**
     * Booleano que dice si la ultima operación fue exitosa o no 
     */
    public boolean ok() {
        return ok; 
    }
    
    

        /**
     * Muestra un mensaje al usuario si el simulador es visible.
     * @param message el mensaje a mostrar
     */
    private void showMessage(String message) {
        if (visible) {
            JOptionPane.showMessageDialog(null, message, 
                "StackingItems Simulator", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Termina el simulador 
     */
    public void exit() {
        active = false;
        makeInvisible();
        ok = true;
    }
    
   
}

    
