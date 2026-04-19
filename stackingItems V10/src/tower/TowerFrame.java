package tower;
/**
 * Representa el marco visual de la torre en el simulador.
 * Tiene rectángulo principal y las marcas de altura.
 * 
 * @author (William Santiago Ruiz)
 * @version 2.0 (Ciclo 1)
 */
public class TowerFrame {
    private int margin;
    private boolean visible;
    private Rectangle border;
    private Rectangle inside;
    private int width;
    private int height;
    
    public TowerFrame(int width, int height, int margin) {
        this.width = width;
        this.height = height;
        this.margin = margin;
        this.visible = false;
        this.border = new Rectangle();
        this.inside = new Rectangle();
        setupFrame();
    }
    
    private void setupFrame() {
        int pixelHeight = height * 10;
        int pixelWidth = width * 10;
        
        border.changeSize(pixelHeight, pixelWidth);
        border.changeColor("black");
        border.moveTo(margin, margin);

        int thickness = 2;
        inside.changeSize(pixelHeight - 2 * thickness, pixelWidth - 2 * thickness);
        inside.changeColor("white");
        inside.moveTo(margin + thickness, margin + thickness);
    }
    
    public void makeVisible() {
        visible = true;
        border.makeVisible();
        inside.makeVisible();
        drawHeightMarks();

    }
    
    public void makeInvisible() {
        visible = false;
        border.makeInvisible();
        inside.makeInvisible();
    }
    
    private void drawHeightMarks() {
        for (int i = 1; i < height; i++) {
            Rectangle mark = new Rectangle();
            mark.changeSize(1, 5);
            mark.changeColor("gray");
            mark.moveTo(margin - 10, margin + (i * 10));
            if (visible) {
                mark.makeVisible();
            }
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getMargin() {
        return margin;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public int getXPosition() {
        return margin;
    }
    
    public int getYPosition() {
        return margin;
    }
}