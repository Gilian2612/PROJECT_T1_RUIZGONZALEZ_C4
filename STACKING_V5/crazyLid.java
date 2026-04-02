
/**
 * Write a description of class crazyLid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class crazyLid extends Lid 
{
    public crazyLid (String color, int blockSize, int cupId) {
        super (color, blockSize,cupId); 
        
    }
    @Override 
    public String getType(){
        return "crazy"; 
        
    }
}