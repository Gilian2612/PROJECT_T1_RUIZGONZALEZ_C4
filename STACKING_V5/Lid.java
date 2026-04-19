
/**
 * Representa una tapa en el simulador de apilamiento.
 * La tapa mide 1 cm de alto y debe moverse/posicionarse alineada en la torre.
 */
public class Lid implements StackItem {
 
    private static final int LID_HEIGHT_CM  = 1;
    private static final int PIXELS_PER_CM  = 10;
 
    private String color;
    private int blockSize;
    private final int cupId;  
    private Triangle triangle;
    private boolean visible;
 
    public Lid(String color, int blockSize, int cupId) {
        this.color     = color;
        this.blockSize = blockSize;
        this.cupId     = cupId;
        this.visible   = false;
        this.triangle  = new Triangle();
    }
    
    @Override
    public int heightCm() {
        return LID_HEIGHT_CM;
    }
 
    @Override
    public int getBlockSize() {
        return blockSize;
    }
 
    @Override
    public int getCupId() {
        return cupId;
    }
 
    @Override
    public String getType() {
        return "lid";
    }
    
    @Override
    public int reposition(int xLeft, int currentBottom, int towerWidthPx) {
        int lidHeightPx = LID_HEIGHT_CM * PIXELS_PER_CM;
        int lidWidthPx = getWidthPx();
        int yLidTop = currentBottom - lidHeightPx;
        int xLid = xLeft + (towerWidthPx - lidWidthPx) / 2;
        setupRectangle(xLid, yLidTop, lidWidthPx);
        return yLidTop;
    }
    
    @Override
    public boolean hasDependant() {
        return false;
    }
    @Override
    public StackItem getDependant() {
        return null;
    }
    
    
    @Override
    public void makeVisible() {
        visible = true;
        if (triangle != null) {
            triangle.makeVisible();
        }
    }  
    

     public void makeInvisible() {
        visible = false;
        if (triangle != null) {
            triangle.makeInvisible();
        }
    }
 
    public String getColor() {
        return color;
    }
 
    public int getId() {
        return cupId;
    }
 
    public int getHeightCm() {
        return LID_HEIGHT_CM;
    }
 
    public int getWidthPx() {
        return blockSize * PIXELS_PER_CM;
    }
 
    public void moveTo(int x, int y) {
        if (triangle != null) {
            triangle.moveTo(x, y);
        }
    }
    
    /**
     * triangulo visual de la tapa.
     * @param x esquina superior izq
     * @param y esquina superior izq
     * @param widthPx ancho 
     */
    
    public void setupRectangle(int x, int y, int widthPx) {
        int heightPx = LID_HEIGHT_CM * PIXELS_PER_CM;
        triangle.makeInvisible();
        triangle.changeSize(heightPx, widthPx);
        triangle.changeColor(color);
        triangle.moveTo(x, y);
        if (visible) {
            triangle.makeVisible();
        }
    }
}