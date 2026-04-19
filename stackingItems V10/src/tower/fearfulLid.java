package tower;
/**
 * Tapa de tipo Fearful (temerosa).
 * 
 * @author William Santiago Ruiz/ Sergio
 * @version 4.0 (Ciclo 4)
 */
public class fearfulLid extends Lid {

    public fearfulLid(String color, int blockSize, int cupId) {
        super(color, blockSize, cupId);
    }

    @Override
    public String getType() {
        return "fearful";
    }
}