 

import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 1.0  (15 July 2000)()
 * 
 * Refactor en Ciclo 4 
 */


 
public class Rectangle extends AllShapes {

    public static int EDGES = 4;
    

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle() {
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
    }
    
    /**
     * Create a new square with equal sides (a square) given the perimeter.
     * @param perimeter 4 times the side of the square
     */
    public Rectangle(int perimeter) {
        if (perimeter % 4 != 0) {
            throw new IllegalArgumentException("perimeter must be a multiple of 4");
        }
        height = perimeter / 4;
        width = perimeter / 4;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
    }

    
    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
    
    /**
     * Returns the perimeter of the Rectangle
     */
    public int perimeter() {
        return 2 * (height + width);
    }
    
    /**
     * Make the size of the rectangle change so that the perimeter increases
     * by 100% or decreases by 50%, while preserving the proportion between
     * width and height.
     * @param z to select zomming in ('+') or zomming out ('-').
     */
    public void zoom(char z) {
        erase();
        switch (z) {
            case '+':
                width *= 2;
                height *= 2;
                break;
            case '-':
                width /= 2;
                height /= 2;
                break;
            default:
                throw new IllegalArgumentException("z must be '+' or '-'");
        }
        draw();
    }
    
    /**
     * Slowly move the rectangle horizontally and vertically downwards by the same amount.
     * @param times the desired horizontal distance in pixels
     */
    public void walk(int times) {
        int delta;
        if (times < 0) {
            delta = -1;
            times = -times;
        } else {
            delta = 1;
        }
        for (int i = 0; i < times; i++) {
            xPosition += delta;
            yPosition++;
            draw();
        }
    }
    
    /**
     * Changes the size of the rectangle: height and width change to the
     * integer part of a fourth of the perimeter, so that the final perimeter
     * is almost the original.
     */
     public void toSquare() {
        erase();
        int perimeter = perimeter();
        changeSize(perimeter / 4, perimeter / 4);
        draw();
    }
    
    public void moveToOrigin() {
        this.xPosition = 0;
        this.yPosition = 0;
        this.draw();
    }
    
    public void moveHorizontallyTo(int x) {
        moveHorizontal(x - xPosition);
    }

    public void moveVerticallyTo(int y) {
        moveVertical(y - yPosition);
    }

    public void moveTo(int x, int y) {
        this.moveHorizontallyTo(x);
        this.moveVerticallyTo(y);
    }

    /**
     * Make this rectangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the rectangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the rectangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the rectangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the rectangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidth must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        if (newHeight < 0) {
            throw new IllegalArgumentException("newHeight must >= 0");
        } else if (newWidth < 0) {
            throw new IllegalArgumentException("newWidth must >= 0");
        }
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }

    /*
     * Draw the rectangle with current specifications on screen.
     */

    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, width, height));
            canvas.wait(10);
        }
    }
    /*
     * Erase the rectangle on screen.
     */
    @Override
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}

