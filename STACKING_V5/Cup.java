
/**
 * Representa una taza en el simulador de apilamiento
 * Cada taza tiene un color, tamaño y puede tener una tapa asociada, cuando una taza está tapada, ambas se mueven juntas
 * 
 * @author (William Santiago Ruiz
 * @version 2.0 (Ciclo 1)
 */
public class Cup implements StackItem {
    
    private static final int PIXELS_PER_CM = 10;

    private String color;
    private int blockSize;
    private final int id;
    private boolean visible;
    private Rectangle rectangle;
    private Lid lid;
    
    public Cup(String color, int blockSize, int id) {
        this.color = color;
        this.blockSize = blockSize;
        this.id = id;
        this.visible = false;
        this.rectangle = new Rectangle();
        this.lid = null;
    }
    
    @Override
    public int heightCm() {
        return blockSize;
    }
 
    @Override
    public int getBlockSize() {
        return blockSize;
    }
 
    @Override
    public int getCupId() {
        return id;
    }
 
    @Override
    public String getType() {
        return "cup";
    }
    
    /**
     * reposition 
     * Devuelve coordenada Y del piso disponible al reposicional la taza en Tower
     */
    @Override
    public int reposition(int xLeft, int currentBottom, int towerWidthPx) {
        int cupHeightPx = blockSize * PIXELS_PER_CM;
        int cupWidthPx  = getWidth();
        int yCupTop     = currentBottom - cupHeightPx;
        int xCup        = xLeft + (towerWidthPx - cupWidthPx) / 2;
        setupRectangle(xCup, yCupTop, cupWidthPx);
        return yCupTop;
    }
    
    /**
     * Cup con lid asociada tiene un dependiente 
     */
    @Override
    public boolean hasDependant() {
        return lid != null;
    }
    
    /**
     * @return lid (como StackingItem) de una copa, o null si no hay 
     */
    @Override
    public StackItem getDependant() {
        return lid;
    }
    
    public void makeVisible() {
        visible = true;
        if (rectangle != null) {
            rectangle.makeVisible();
        }
    }
    
    public void makeInvisible() {
        visible = false;
        if (rectangle != null) {
            rectangle.makeInvisible();
        }
    }
    
    public String getColor() {
        return color;
    }
    
    public int getId() {
        return id;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public int getHeight() {
        return blockSize;
    }
    
    public int getWidth() {
        return blockSize * PIXELS_PER_CM;
    }
    
    public void setLid(Lid lid) {
        this.lid = lid;
    }
    
    public Lid getLid() {
        return lid;
    }
    
    public boolean hasLid() {
        return lid != null;
    }
    
    /**
     * mueve una taza a un punto x,y determinado, si esta tiene tapa, se mueve tanto la taza como 
     * su tapa 
     */
    public void moveTo(int x, int y) {
        if (rectangle != null) {
            rectangle.moveTo(x, y);
        }
        if (lid != null) {
            lid.moveTo(x, y - PIXELS_PER_CM);
        }
    }
    
    /**
     * Posiciona la taza 
     */
    
    public void setupRectangle(int x, int y, int width) {
        boolean wasVisible = rectangle.isVisible();
        
        rectangle.makeInvisible();
        
        rectangle.changeSize(blockSize * PIXELS_PER_CM, width);
        rectangle.changeColor(color);
        rectangle.moveTo(x, y);
        
        if (visible) {
            rectangle.makeVisible();
        }
    }
    
    public boolean hasReachedBottom() {
        return false;
    }
    public void vanish() {
    }
    
    
    public int getTotalHeight() {
        return blockSize + (hasLid() ? 1 : 0);
    }
}
