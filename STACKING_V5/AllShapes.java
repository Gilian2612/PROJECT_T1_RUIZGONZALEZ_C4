
/**
 * Clase base abstracta para todas las figuras del simulador.
 * deja draw() y erase() como responsabilidad de cada subclase
 * de cada subclase
 * */
 
public abstract class AllShapes {
 
    protected int height;
    protected int width;
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;
    protected abstract void draw();

    protected abstract void erase();
 
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    public void makeInvisible() {
        erase();
        isVisible = false;
    }
 
    public void moveRight() {
        moveHorizontal(20);
    }
 
    public void moveLeft() {
        moveHorizontal(-20);
    }
 
    public void moveUp() {
        moveVertical(-20);
    }
 
    public void moveDown() {
        moveVertical(20);
    }
 
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }
 
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }
 
    public void slowMoveHorizontal(int distance) {
        int delta;
        if (distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }
        for (int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }
 
    public void slowMoveVertical(int distance) {
        int delta;
        if (distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }
        for (int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }
 
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }
 
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
 
    public void moveTo(int x, int y) {
        erase();
        xPosition = x;
        yPosition = y;
        draw();
    }
 
    public void moveToOrigin() {
        erase();
        xPosition = 0;
        yPosition = 0;
        draw();
    }
 
    public int getXPosition() {
        return xPosition;
    }
 
    public int getYPosition() {
        return yPosition;
    }
 
    public int getWidth() {
        return width;
    }
 
    public int getHeight() {
        return height;
    }
 
    public boolean isVisible() {
        return isVisible;
    }
}
    
