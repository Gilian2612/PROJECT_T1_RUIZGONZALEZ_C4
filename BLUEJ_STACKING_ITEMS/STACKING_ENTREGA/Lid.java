/**
 * Representa una tapa (lid) normal en el simulador de apilamiento.
 * NO extiende StackItem - es una clase independiente.
 *
 * Cada tapa está asociada a una copa específica mediante cupId.
 * El cupId es el ID único de la copa propietaria (uniforme con operaciones de Tower).
 *
 * @author William Santiago Ruiz / Sergio
 * @version 4.1 (Ciclo 4 - Refactor con ID uniforme)
 */
public class Lid {
 
    private static final int LID_HEIGHT_CM = 1;
    private static final int PIXELS_PER_CM = 10;
 
    private String color;
    private int blockSize;
    private final int cupId;   // ID de la copa propietaria (uniforme)
    private Triangle triangle;
    private boolean visible;
 
    /**
     * Constructor de Tapa
     *
     * @param color Color de la tapa
     * @param blockSize Tamaño en cm (debe coincidir con el blockSize de la copa)
     * @param cupId ID único de la copa propietaria (uniforme con operaciones de Tower)
     */
    public Lid(String color, int blockSize, int cupId) {
        this.color     = color;
        this.blockSize = blockSize;
        this.cupId     = cupId;    // Referencia uniforme a la copa propietaria
        this.visible   = false;
        this.triangle  = new Triangle();
    }
 
    /**
     * Altura en centímetros de la tapa (siempre 1 cm)
     */
    public int heightCm() {
        return LID_HEIGHT_CM;
    }
 
    /**
     * Tamaño del bloque de la tapa (debe coincidir con su copa)
     */
    public int getBlockSize() {
        return blockSize;
    }
 
    /**
     * ID único de la copa propietaria (uniforme para identificar la tapa en operaciones)
     * Usar este valor para removeLidById() y operaciones de Tower
     */
    public int getCupId() {
        return cupId;
    }
 
    /**
     * ID de la tapa (alias para getCupId() por compatibilidad)
     * @deprecated Usar getCupId() para obtener el ID de la copa propietaria
     */
    @Deprecated
    public int getId() {
        return cupId;
    }
 
    /**
     * Tipo de esta tapa (sobrescrito en subclases)
     */
    public String getType() {
        return "lid";
    }
 
    /**
     * Reposiciona esta tapa en la pantalla según la pila actual
     *
     * @param xLeft Posición X izquierda de la torre
     * @param currentBottom Posición Y actual (fondo)
     * @param towerWidthPx Ancho de la torre en píxeles
     * @return Nueva posición Y después de este elemento
     */
    public int reposition(int xLeft, int currentBottom, int towerWidthPx) {
        int lidHeightPx = LID_HEIGHT_CM * PIXELS_PER_CM;
        int lidWidthPx  = blockSize * PIXELS_PER_CM;
        int yLidTop     = currentBottom - lidHeightPx;
        int xLid        = xLeft + (towerWidthPx - lidWidthPx) / 2;
        setupTriangle(xLid, yLidTop, lidWidthPx);
        return yLidTop;
    }
 
    public void makeVisible() {
        visible = true;
        if (triangle != null) triangle.makeVisible();
    }
 
    public void makeInvisible() {
        visible = false;
        if (triangle != null) triangle.makeInvisible();
    }
 
    // Métodos específicos de Lid
    public String getColor()   { return color; }
    public int    getHeightCm(){ return LID_HEIGHT_CM; }
    public int    getWidthPx() { return blockSize * PIXELS_PER_CM; }
 
    public void moveTo(int x, int y) {
        if (triangle != null) triangle.moveTo(x, y);
    }
 
    public void setupTriangle(int x, int y, int widthPx) {
        int heightPx = LID_HEIGHT_CM * PIXELS_PER_CM;
        int centerX = x + widthPx / 2;
        triangle.makeInvisible();
        triangle.changeSize(heightPx, widthPx);
        triangle.changeColor(color);
        triangle.moveTo(centerX, y);
        if (visible) triangle.makeVisible();
    }
 
    public void setupRectangle(int x, int y, int widthPx) {
        setupTriangle(x, y, widthPx);
    }
}