package tower;
/**
 * Representa una taza (copa) normal en el simulador de apilamiento.
 * NO extiende StackItem - es una clase independiente que puede ser apilable.
 *
 * Cada copa tiene un ID único que la identifica globalmente en la Torre.
 * Este ID se usa uniformemente en operaciones de push/pop/remove de copas y tapas.
 *
 * @author William Santiago Ruiz  / Sergio G
 * @version 4.1 (Ciclo 4 - Refactor con ID uniforme)
 */
public class Cup {
 
    private static final int PIXELS_PER_CM = 10;
 
    private String color;
    private int blockSize;
    private final int id;      // ID único: identificador global uniforme
    private boolean visible;
    private Rectangle rectangle;
    private Lid lid;
 
    /**
     * Constructor de Copa
     *
     * @param color Color de la copa
     * @param blockSize Tamaño en cm (también determina altura visual)
     * @param id ID único asignado por Tower (uniforme para todas las operaciones)
     */
    public Cup(String color, int blockSize, int id) {
        this.color     = color;
        this.blockSize = blockSize;
        this.id        = id;      // ID único para esta copa en toda la sesión
        this.visible   = false;
        this.rectangle = new Rectangle();
        this.lid       = null;
    }
 
    /**
     * Altura en centímetros (igual al blockSize)
     */
    public int heightCm() {
        return blockSize;
    }
 
    /**
     * Tamaño del bloque (anchura y altura en cm)
     */
    public int getBlockSize() {
        return blockSize;
    }
 
    /**
     * ID único de esta copa (usado uniformemente en todas las operaciones)
     * Utilizar este método para identificar la copa en pushLid, removeLid, removeCup, etc.
     */
    public int getId() {
        return id;
    }
 
    /**
     * Compatibilidad: alias para getId()
     * @deprecated Usar getId() en su lugar
     */
    @Deprecated
    public int getCupId() {
        return id;
    }
 
    /**
     * Tipo de esta copa (sobrescrito en subclases)
     */
    public String getType() {
        return "cup";
    }
 
    /**
     * Reposiciona esta copa en la pantalla según la pila actual
     *
     * @param xLeft Posición X izquierda de la torre
     * @param currentBottom Posición Y actual (fondo)
     * @param towerWidthPx Ancho de la torre en píxeles
     * @return Nueva posición Y después de este elemento
     */
    public int reposition(int xLeft, int currentBottom, int towerWidthPx) {
        int cupHeightPx = blockSize * PIXELS_PER_CM;
        int cupWidthPx  = blockSize * PIXELS_PER_CM;
        int yCupTop     = currentBottom - cupHeightPx;
        int xCup        = xLeft + (towerWidthPx - cupWidthPx) / 2;
        setupRectangle(xCup, yCupTop, cupWidthPx);
        return yCupTop;
    }
 
    public void makeVisible() {
        visible = true;
        if (rectangle != null) rectangle.makeVisible();
    }
 
    public void makeInvisible() {
        visible = false;
        if (rectangle != null) rectangle.makeInvisible();
    }
 
    public boolean hasDependant() {
        return lid != null;
    }
 
    // Métodos específicos de Cup
    public String getColor()       { return color; }
    public boolean isVisible()     { return visible; }
    public int    getHeight()      { return blockSize; }
    public int    getWidth()       { return blockSize * PIXELS_PER_CM; }
 
    public void setLid(Lid lid)    { this.lid = lid; }
    public Lid  getLid()           { return lid; }
    public boolean hasLid()        { return lid != null; }
 
    public boolean hasReachedBottom() { return false; }
    public void vanish() { }
 
    public void moveTo(int x, int y) {
        if (rectangle != null) rectangle.moveTo(x, y);
    }
 
    public void setupRectangle(int x, int y, int width) {
        rectangle.makeInvisible();
        rectangle.changeSize(blockSize * PIXELS_PER_CM, width);
        rectangle.changeColor(color);
        rectangle.moveTo(x, y);
        if (visible) rectangle.makeVisible();
    }
 
    public int getTotalHeight() {
        return blockSize + (hasLid() ? 1 : 0);
    }
}