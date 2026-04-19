/**
 * Clase concreta que representa un elemento apilable en la torre.
 * Usa composición para referenciar al objeto visual (Cup o Lid).
 * 
 * @author William Santiago Ruiz  / Sergio Morales
 * @version 4.0 (Ciclo 4 - Refactor con clase concreta StackItem)
 */
public class StackItem {
    
    private String type;      // "cup", "opener", "hierarchical", "Vanishing", "lid", "fearful", "crazy"
    private int blockSize;    // tamaño en cm
    private int cupId;        // id de la copa propietaria
    private Cup cupRef;       // referencia a la copa (si es tipo copa)
    private Lid lidRef;       // referencia a la tapa (si es tipo tapa)
    
    // Constructor para elementos tipo Copa
    public StackItem(Cup cup) {
        this.type = cup.getType();
        this.blockSize = cup.getBlockSize();
        this.cupId = cup.getId();
        this.cupRef = cup;
        this.lidRef = null;
    }
    
    // Constructor para elementos tipo Tapa
    public StackItem(Lid lid, int cupId) {
        this.type = lid.getType();
        this.blockSize = lid.getBlockSize();
        this.cupId = cupId;
        this.cupRef = null;
        this.lidRef = lid;
    }
    
    // Getters
    public String getType() { return type; }
    public int getBlockSize() { return blockSize; }
    public int getCupId() { return cupId; }
    public Cup getCup() { return cupRef; }
    public Lid getLid() { return lidRef; }
    
    public int heightCm() {
        if (isCupVariant()) return blockSize;
        return 1; // las tapas miden 1 cm
    }
    
    public boolean isCupVariant() {
        return type.equals("cup") || type.equals("opener") 
            || type.equals("hierarchical") || type.equals("Vanishing");
    }
    
    public boolean hasDependant() {
        if (!isCupVariant()) return false;
        return cupRef != null && cupRef.hasLid();
    }
    
    public StackItem getDependant() {
        if (!hasDependant()) return null;
        return new StackItem(cupRef.getLid(), cupRef.getId());
    }
    
    public void makeVisible() {
        if (cupRef != null) cupRef.makeVisible();
        if (lidRef != null) lidRef.makeVisible();
    }
    
    public void makeInvisible() {
        if (cupRef != null) cupRef.makeInvisible();
        if (lidRef != null) lidRef.makeInvisible();
    }
    
    public int reposition(int xLeft, int currentBottom, int towerWidthPx) {
        if (cupRef != null) {
            return cupRef.reposition(xLeft, currentBottom, towerWidthPx);
        } else if (lidRef != null) {
            return lidRef.reposition(xLeft, currentBottom, towerWidthPx);
        }
        return currentBottom;
    }
    
    @Override
    public String toString() {
        return type + " " + blockSize;
    }
}