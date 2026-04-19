package tower;
/**
 * Copa de tipo Vanishing (desapareciente).
 * 
 * @author William Santiago Ruiz / Sergio
 * @version 4.0 (Ciclo 4)
 */
public class VanishingCup extends Cup {

    private static final int SHRINK_STEPS = 5;
    private static final int SHRINK_DELAY = 80;

    public VanishingCup(String color, int blockSize, int id) {
        super(color, blockSize, id);
    }

    @Override
    public String getType() {
        return "Vanishing";
    }

    @Override
    public void vanish() {
        int originalHeight = getBlockSize() * 10;
        int originalWidth  = getWidth();
        for (int step = SHRINK_STEPS; step >= 1; step--) {
            int newHeight = Math.max(1, (originalHeight * step) / SHRINK_STEPS);
            int newWidth  = Math.max(1, (originalWidth * step) / SHRINK_STEPS);
            // Mantener la posición X actual
            int currentX = getWidth() / 2; // Esto debería mejorarse
            setupRectangle(currentX, newHeight, newWidth);
            try { Thread.sleep(SHRINK_DELAY); } catch (InterruptedException e) { }
        }
        makeInvisible();
    }
}