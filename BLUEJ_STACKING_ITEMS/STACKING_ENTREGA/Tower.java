import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
 
/**
 * Clase principal del simulador de apilar tazas y tapas.
 * REFACTORIZADO: Uso uniforme de blockSize para identificar copas
 *
 * @author William Santiago Ruiz  / Sergio G
 * @version 4.1 (Ciclo 4 - Refactor Uniforme)
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
 
    private static final int DEFAULT_MARGIN  = 50;
    private static final int PIXELS_PER_CM   = 10;
    private static final int LID_HEIGHT_CM   = 1;
    private static final String[] COLORS     = {"red","blue","yellow","green","magenta"};
 
    private int colors  = 0;
    private int nextId  = 0;
 
    public Tower(int width, int maxHeight) {
        if (width <= 0 || maxHeight <= 0) {
            ok = false;
            showMessage("Error: dimensions <= 0.");
            return;
        }
        this.width   = width;
        this.height  = maxHeight;
        this.visible = false;
        this.ok      = true;
        this.active  = true;
        this.margin  = DEFAULT_MARGIN;
        this.cups    = new ArrayList<Cup>();
        this.stack   = new ArrayList<StackItem>();
        this.frame   = new TowerFrame(width, maxHeight, DEFAULT_MARGIN);
    }
 
    public Tower(int cupsCount) {
        this(10, Math.max(10, cupsCount * 10));
        this.margin = 10;
        this.frame  = new TowerFrame(width, height, this.margin);
        for (int k = 1; k <= cupsCount; k++) {
            pushCup(2 * k - 1);
        }
    }
 
    // -----------------------------------------------------------------------
    // Gestion de tazas
    // -----------------------------------------------------------------------
 
    /**
     * Añade una copa normal con el blockSize dado.
     * @param blockSize Tamaño en cm de la copa
     */
    public void pushCup(int blockSize) {
        pushCup("normal", blockSize);
    }
 
    /**
     * Añade una copa del tipo indicado con el blockSize dado.
     * @param type Tipo de copa: "normal", "opener", "hierarchical", "Vanishing"
     * @param blockSize Tamaño en cm de la copa
     */
    public void pushCup(String type, int blockSize) {
        if (!active) { showMessage("Simulator Finished"); return; }
        if (blockSize > width) {
            showMessage("The cup cannot be wider than the tower");
            ok = false; return;
        }
        if (findCupByBlockSize(blockSize) != null) {
            showMessage("A cup with that size already exists");
            ok = false; return;
        }
 
        switch (type) {
            case "opener":
                removeBlockingLids();
                break;
            case "hierarchical":
                displaceSmaller(blockSize);
                break;
            case "normal":
            case "Vanishing":
                break;
            default:
                showMessage("Unknown cup type: " + type);
                ok = false; return;
        }
 
        if (calculateCurrentHeight() + blockSize > height) {
            showMessage("Tower doesn't have enough space");
            ok = false; return;
        }
 
        String color = getNextColor();
        int id       = nextId++;
        Cup cup;
        switch (type) {
            case "opener":
                cup = new openerCup(color, blockSize, id);
                break;
            case "hierarchical":
                hierarchicalCup hcup = new hierarchicalCup(color, blockSize, id);
                if (calculateCurrentHeight() + blockSize == height) {
                    hcup.setReachedBottom(true);
                }
                cup = hcup;
                break;
            case "Vanishing":
                cup = new VanishingCup(color, blockSize, id);
                break;
            default:
                cup = new Cup(color, blockSize, id);
                break;
        }
 
        cups.add(cup);
        StackItem item = new StackItem(cup);
 
        if (type.equals("hierarchical")) {
            stack.add(0, item);
        } else {
            stack.add(item);
        }
 
        repositionAllItems();
        if (visible) cup.makeVisible();
        ok = true;
    }
 
    /**
     * Remueve la última copa de la torre (la más alta en el stack).
     */
    public void popCup() {
        if (!active) { showMessage("Simulator finished"); ok = false; return; }
        for (int i = stack.size() - 1; i >= 0; i--) {
            StackItem item = stack.get(i);
            if (item.isCupVariant()) {
                Cup cup = item.getCup();
                if (cup.getType().equals("hierarchical") && ((hierarchicalCup)cup).hasReachedBottom()) {
                    showMessage("hierarchicalCup reached the bottom and cannot be removed");
                    ok = false; return;
                }
                removeCup(cup.getBlockSize());
                ok = true;
                return;
            }
        }
        showMessage("No cups in tower");
        ok = false;
    }
 
    /**
     * Remueve una copa identificada por su blockSize.
     * @param blockSize Tamaño en cm de la copa a remover
     */
    public void removeCup(int blockSize) {
        if (!active) { showMessage("Simulator Finished"); return; }
        Cup cup = findCupByBlockSize(blockSize);
        if (cup == null) {
            showMessage("Cup not found"); ok = false; return;
        }
        if (cup.getType().equals("hierarchical") && ((hierarchicalCup)cup).hasReachedBottom()) {
            showMessage("hierarchicalCup reached the bottom and cannot be removed");
            ok = false; return;
        }
        if (cup.hasLid()) {
            cup.getLid().makeInvisible();
            for (int i = 0; i < stack.size(); i++) {
                StackItem si = stack.get(i);
                if (si.getLid() == cup.getLid()) {
                    stack.remove(i);
                    break;
                }
            }
            cup.setLid(null);
        }
        cup.makeInvisible();
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getCup() == cup) {
                stack.remove(i);
                break;
            }
        }
        cups.remove(cup);
        repositionAllItems();
        ok = true;
    }
 
    // -----------------------------------------------------------------------
    // Gestion de tapas
    // -----------------------------------------------------------------------
 
    /**
     * Añade una tapa normal a la copa con el blockSize dado.
     * @param blockSize Tamaño en cm de la copa a tapar
     */
    public void pushLid(int blockSize) {
        pushLid("normal", blockSize);
    }
 
    /**
     * Añade una tapa del tipo indicado a la copa con el blockSize dado.
     * @param type Tipo de tapa: "normal", "fearful", "crazy"
     * @param blockSize Tamaño en cm de la copa a tapar
     */
    public void pushLid(String type, int blockSize) {
        if (!active) { showMessage("Simulator Finished"); return; }
 
        Cup cup = findCupByBlockSize(blockSize);
        if (cup == null) {
            showMessage("Cup not found"); ok = false; return;
        }
 
        if (cup.hasLid()) {
            showMessage("cup already has a lid"); ok = false; return;
        }
        if (calculateCurrentHeight() + LID_HEIGHT_CM > height) {
            showMessage("Not enough space for lid"); ok = false; return;
        }
 
        if (type.equals("fearful") && !isCupInTower(cup.getBlockSize())) {
            showMessage("fearfulLid: su copa no esta en la torre");
            ok = false; return;
        }
 
        Lid lid;
        switch (type) {
            case "fearful":
                lid = new fearfulLid(cup.getColor(), cup.getBlockSize(), cup.getId());
                cup.setLid(lid);
                int cupPos = findCupStackPosition(cup);
                stack.add(cupPos + 1, new StackItem(lid, cup.getId()));
                break;
            case "crazy":
                lid = new crazyLid(cup.getColor(), cup.getBlockSize(), cup.getId());
                stack.add(0, new StackItem(lid, cup.getId()));
                break;
            default:
                lid = new Lid(cup.getColor(), cup.getBlockSize(), cup.getId());
                cup.setLid(lid);
                cupPos = findCupStackPosition(cup);
                stack.add(cupPos + 1, new StackItem(lid, cup.getId()));
                break;
        }
 
        if (visible) lid.makeVisible();
        repositionAllItems();
        ok = true;
    }
 
    /**
     * Remueve la última tapa de la torre (la más alta en el stack).
     */
    public void popLid() {
        if (!active) { showMessage("Simulator finished"); ok = false; return; }
        for (int i = stack.size() - 1; i >= 0; i--) {
            StackItem item = stack.get(i);
            String t = item.getType();
            if (t.equals("lid") || t.equals("fearful") || t.equals("crazy")) {
                if (t.equals("fearful")) {
                    Cup cup = findCupById(item.getCupId());
                    if (cup != null && cup.hasLid()) {
                        showMessage("fearfulLid: no puede salir mientras tapa a su taza");
                        ok = false; return;
                    }
                }
                Cup cup = findCupById(item.getCupId());
                if (cup != null) removeLid(cup.getBlockSize());
                ok = true;
                return;
            }
        }
        showMessage("No lids in tower");
        ok = false;
    }
 
    /**
     * Remueve la tapa de la copa identificada por su blockSize.
     * @param blockSize Tamaño en cm de la copa cuya tapa se remueve
     */
    public void removeLid(int blockSize) {
        if (!active) { showMessage("Simulator finished"); return; }
        Cup cup = findCupByBlockSize(blockSize);
        if (cup == null) {
            showMessage("Cup not found"); ok = false; return;
        }
        if (!cup.hasLid()) {
            showMessage("cup has no lid"); ok = false; return;
        }
        Lid lid = cup.getLid();
        lid.makeInvisible();
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getLid() == lid) {
                stack.remove(i);
                break;
            }
        }
        cup.setLid(null);
        repositionAllItems();
        ok = true;
    }
 
    // -----------------------------------------------------------------------
    // Reorganizacion de la torre
    // -----------------------------------------------------------------------
 
    /**
     * Ordena la torre de mayor a menor blockSize.
     */
    public void orderTower() {
        if (!active) { showMessage("Simulator finished"); ok = false; return; }
        normalizeStack();
        stack.sort((a, b) -> Integer.compare(b.getBlockSize(), a.getBlockSize()));
        normalizeStack();
        repositionAllItems();
        ok = true;
    }
 
    /**
     * Invierte el orden de los elementos de la torre.
     */
    public void reverseTower() {
        if (!active) { showMessage("Simulator finished"); ok = false; return; }
        vanishVanishingCups();
        normalizeStack();
        Collections.reverse(stack);
        normalizeStack();
        repositionAllItems();
        ok = true;
    }
 
    /**
     * Intercambia dos objetos identificados por tipo y blockSize.
     * @param o1 Arreglo {tipo, blockSize} del primer objeto
     * @param o2 Arreglo {tipo, blockSize} del segundo objeto
     */
    public void swap(String[] o1, String[] o2) {
        if (!active) { showMessage("Simulator finished"); ok = false; return; }
        if (o1 == null || o2 == null || o1.length < 2 || o2.length < 2) {
            showMessage("invalid objects"); ok = false; return;
        }
        int i1 = findIndexOf(o1[0], Integer.parseInt(o1[1]));
        int i2 = findIndexOf(o2[0], Integer.parseInt(o2[1]));
        if (i1 == -1 || i2 == -1) {
            showMessage("object not found"); ok = false; return;
        }
        Collections.swap(stack, i1, i2);
        normalizeStack();
        repositionAllItems();
        ok = true;
    }
 
    /**
     * Intercambia dos objetos (sobrecarga con parámetros individuales).
     */
    public void swap(String type1, String num1, String type2, String num2) {
        swap(new String[]{type1, num1}, new String[]{type2, num2});
    }
 
    /**
     * Tapa todas las copas que aún no tienen tapa.
     */
    public void cover() {
        if (!active) { showMessage("Simulator finished"); ok = false; return; }
        boolean anyCovered = false;
        for (int i = 0; i < cups.size(); i++) {
            Cup cup = cups.get(i);
            if (!cup.hasLid() && calculateCurrentHeight() + LID_HEIGHT_CM <= height) {
                pushLid(cup.getBlockSize());
                anyCovered = true;
            }
        }
        if (!anyCovered) { showMessage("No cups to cover"); ok = false; return; }
        ok = true;
    }
 
    // -----------------------------------------------------------------------
    // Consultas
    // -----------------------------------------------------------------------
 
    /**
     * Retorna la altura actual de los elementos apilados.
     * @return Altura en cm
     */
    public int height() {
        return calculateCurrentHeight();
    }
 
    /**
     * Retorna los blockSizes de las copas tapadas, ordenados.
     * @return Arreglo de blockSizes
     */
    public int[] liddedCups() {
        ArrayList<Integer> sizes = new ArrayList<Integer>();
        for (Cup cup : cups) {
            if (cup.hasLid()) sizes.add(cup.getBlockSize());
        }
        Collections.sort(sizes);
        int[] result = new int[sizes.size()];
        for (int i = 0; i < sizes.size(); i++) result[i] = sizes.get(i);
        return result;
    }
 
    /**
     * Retorna los elementos apilados como arreglo de StackItem.
     * @return Arreglo de StackItem
     */
    public StackItem[] stackingItems() {
        normalizeStack();
        return stack.toArray(new StackItem[0]);
    }
 
    /**
     * Retorna los elementos apilados como arreglo de Strings.
     * @return Arreglo bidimensional {tipo, blockSize}
     */
    public String[][] stackingItemsAsStrings() {
        normalizeStack();
        String[][] result = new String[stack.size()][2];
        for (int i = 0; i < stack.size(); i++) {
            result[i][0] = stack.get(i).getType();
            result[i][1] = String.valueOf(stack.get(i).getBlockSize());
        }
        return result;
    }
 
    /**
     * Consulta un intercambio que reduzca la altura de la torre.
     * @return Arreglo con los dos objetos a intercambiar, o null si no hay mejora
     */
    public String[][] swapToReduce() {
        if (!active) { showMessage("Simulator finished"); ok = false; return null; }
        normalizeStack();
        int currentHeight = calculateCurrentHeight();
        int bestReduction = 0;
        int bestI = -1, bestJ = -1;
 
        for (int i = 0; i < stack.size(); i++) {
            for (int j = i + 1; j < stack.size(); j++) {
                ArrayList<StackItem> copy = new ArrayList<StackItem>(stack);
                Collections.swap(copy, i, j);
                copy = normalizeStackCopy(copy);
                int newHeight = heightOfStack(copy);
                int reduction = currentHeight - newHeight;
                if (reduction > bestReduction) {
                    bestReduction = reduction;
                    bestI = i; bestJ = j;
                }
            }
        }
        if (bestI == -1) { showMessage("No swap reduces height"); ok = false; return null; }
        StackItem e1 = stack.get(bestI);
        StackItem e2 = stack.get(bestJ);
        ok = true;
        return new String[][]{
            {e1.getType(), String.valueOf(e1.getBlockSize())},
            {e2.getType(), String.valueOf(e2.getBlockSize())}
        };
    }
 
    // -----------------------------------------------------------------------
    // Visibilidad
    // -----------------------------------------------------------------------
 
    /**
     * Hace visible el simulador.
     */
    public void makeVisible() {
        visible = true;
        frame.makeVisible();
        for (Cup cup : cups) {
            cup.makeVisible();
            if (cup.hasLid()) cup.getLid().makeVisible();
        }
        repositionAllItems();
        ok = true;
    }
 
    /**
     * Hace invisible el simulador.
     */
    public void makeInvisible() {
        visible = false;
        for (Cup cup : cups) {
            cup.makeInvisible();
            if (cup.hasLid()) cup.getLid().makeInvisible();
        }
        frame.makeInvisible();
        ok = true;
    }
 
    /**
     * Indica si la última operación fue exitosa.
     * @return true si la última operación fue exitosa
     */
    public boolean ok() { return ok; }
 
    /**
     * Termina el simulador.
     */
    public void exit() {
        active = false;
        makeInvisible();
        ok = true;
    }
 
    // -----------------------------------------------------------------------
    // Métodos privados auxiliares
    // -----------------------------------------------------------------------
 
    private void repositionAllItems() {
        if (!visible) return;
        int xLeft        = margin;
        int towerWidthPx = width * PIXELS_PER_CM;
        int currentBottom = margin + height * PIXELS_PER_CM;
 
        for (StackItem item : stack) {
            if (currentBottom < margin) break;
            currentBottom = item.reposition(xLeft, currentBottom, towerWidthPx);
        }
    }
 
    private void normalizeStack() {
        ArrayList<StackItem> newStack = new ArrayList<StackItem>();
        for (StackItem item : stack) {
            if (item.isCupVariant()) {
                newStack.add(item);
                if (item.hasDependant()) newStack.add(item.getDependant());
            } else if (item.getType().equals("crazy")) {
                newStack.add(item);
            }
        }
        stack = newStack;
    }
 
    private ArrayList<StackItem> normalizeStackCopy(ArrayList<StackItem> s) {
        ArrayList<StackItem> result = new ArrayList<StackItem>();
        for (StackItem item : s) {
            if (item.isCupVariant()) {
                result.add(item);
                if (item.hasDependant()) result.add(item.getDependant());
            }
        }
        return result;
    }
 
    private int heightOfStack(ArrayList<StackItem> s) {
        int total = 0;
        for (StackItem item : s) total += item.heightCm();
        return total;
    }
 
    private int calculateCurrentHeight() {
        return heightOfStack(stack);
    }
 
    private void removeBlockingLids() {
        for (int i = cups.size() - 1; i >= 0; i--) {
            if (cups.get(i).hasLid()) removeLid(cups.get(i).getBlockSize());
        }
    }
 
    private void displaceSmaller(int blockSize) {
        normalizeStack();
        ArrayList<StackItem> smaller = new ArrayList<StackItem>();
        ArrayList<StackItem> rest    = new ArrayList<StackItem>();
        for (StackItem item : stack) {
            if (item.getBlockSize() < blockSize) smaller.add(item);
            else rest.add(item);
        }
        stack = rest;
        stack.addAll(smaller);
    }
 
    private void vanishVanishingCups() {
        for (int i = cups.size() - 1; i >= 0; i--) {
            Cup cup = cups.get(i);
            if (cup.getType().equals("Vanishing")) {
                cup.vanish();
                if (cup.hasLid()) {
                    for (int j = 0; j < stack.size(); j++) {
                        if (stack.get(j).getLid() == cup.getLid()) {
                            stack.remove(j);
                            break;
                        }
                    }
                    cup.setLid(null);
                }
                for (int j = 0; j < stack.size(); j++) {
                    if (stack.get(j).getCup() == cup) {
                        stack.remove(j);
                        break;
                    }
                }
                cups.remove(i);
            }
        }
    }
 
    /**
     * Verifica si una copa con el blockSize dado está en la torre.
     * @param blockSize Tamaño de la copa a buscar
     * @return true si la copa está en el stack
     */
    private boolean isCupInTower(int blockSize) {
        for (StackItem item : stack) {
            if (item.isCupVariant() && item.getBlockSize() == blockSize) return true;
        }
        return false;
    }
 
    /**
     * Busca una copa por su blockSize en la lista de copas.
     * @param blockSize Tamaño de la copa a buscar
     * @return La copa encontrada o null
     */
    private Cup findCupByBlockSize(int blockSize) {
        for (Cup cup : cups) {
            if (cup.getBlockSize() == blockSize) return cup;
        }
        return null;
    }
 
    /**
     * Busca una copa por su ID interno (usado internamente por popLid).
     * @param id ID interno de la copa
     * @return La copa encontrada o null
     */
    private Cup findCupById(int id) {
        for (Cup cup : cups) { if (cup.getId() == id) return cup; }
        return null;
    }
 
    private int findCupStackPosition(Cup target) {
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getCup() == target) return i;
        }
        return -1;
    }
 
    private int findIndexOf(String type, int blockSize) {
        for (int i = 0; i < stack.size(); i++) {
            StackItem e = stack.get(i);
            if (e.getType().equals(type) && e.getBlockSize() == blockSize) return i;
        }
        return -1;
    }
 
    private String getNextColor() {
        return COLORS[colors++ % COLORS.length];
    }
 
    private void showMessage(String message) {
        if (visible) {
            JOptionPane.showMessageDialog(null, message,
                "StackingItems Simulator", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}