package tower;
/**
 * Tapa de tipo Crazy (loca).
 * 
 * @author William Santiago Ruiz / Sergio
 * @version 4.0 (Ciclo 4)
 */
public class crazyLid extends Lid {

    public crazyLid(String color, int blockSize, int cupId) {
        super(color, blockSize, cupId);
    }

    @Override
    public String getType() {
        return "crazy";
    }
}