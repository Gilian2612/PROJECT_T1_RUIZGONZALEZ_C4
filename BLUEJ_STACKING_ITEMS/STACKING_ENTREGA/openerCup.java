/**
 * Copa de tipo Opener.
 * 
 * @author William Santiago Ruiz / Sergio
 * @version 4.0 (Ciclo 4)
 */
public class openerCup extends Cup {

    public openerCup(String color, int blockSize, int id) {
        super(color, blockSize, id);
    }

    @Override
    public String getType() {
        return "opener";
    }
}