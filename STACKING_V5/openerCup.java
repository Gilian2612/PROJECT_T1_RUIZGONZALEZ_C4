
/**
 * Copa de tipo Opener 
 * Cuando entra a Tower, elimina a todas las que le impiden el paso 
 */


public class openerCup extends Cup{
    public openerCup(String color, int blockSize, int id) {
        super(color, blockSize, id); 
        
    }
    @Override
    public String getType(){ 
        return "opener"; 
    }
    
}