import shapes.*; 

/**
 * VanishingCup es una copa que se achica cada vez que se utiliza la función de 
 * invertir la torre, se va a encoger progresivamente hasta desaparecer por cada iteración
 * de la función invertir 
 */
public class VanishingCup extends Cup 
{

    private static final int SHRINK_STEPS = 5;
    private static final int SHRINK_DELAY = 80;
 
    public VanishingCup(String color, int blockSize, int id) {
        super(color, blockSize, id);
    }
 
    @Override
    public String getType() {
        return "vanishing";
    }
    /**
     * Va a encoger la taza progresivamente hasta que se desaparezca, al terminar, 
     * la taza queda invisible 
     */
    public void vanish() {
        int originalHeight = getBlockSize() * 10;
        int originalWidth = getWidth();
        for (int step = SHRINK_STEPS; step >= 1; step--) {
            int newHeight = (originalHeight * step) / SHRINK_STEPS;
            int newWidth = (originalWidth * step) / SHRINK_STEPS;
            if (newHeight < 1) {
                newHeight = 1;
            }
            if (newWidth < 1) {
                newWidth = 1;
            }
            setupRectangle(getWidth() / 2, newHeight, newWidth);
            try {
                Thread.sleep(SHRINK_DELAY);
            } catch (InterruptedException e) {
                // ignorar
            }
        }
        makeInvisible();
    }
}
 
    