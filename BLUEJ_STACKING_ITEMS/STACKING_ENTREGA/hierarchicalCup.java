/**
 * Copa de tipo Hierarchical.
 * 
 * @author William Santiago Ruiz / Sergio
 * @version 4.0 (Ciclo 4)
 */
public class hierarchicalCup extends Cup {

    private boolean reachedBottom;

    public hierarchicalCup(String color, int blockSize, int id) {
        super(color, blockSize, id);
        this.reachedBottom = false;
    }

    @Override
    public String getType() {
        return "hierarchical";
    }

    public boolean hasReachedBottom() {
        return reachedBottom;
    }

    public void setReachedBottom(boolean reachedBottom) {
        this.reachedBottom = reachedBottom;
    }
}