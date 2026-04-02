
/**
 * 
 * Tapa Type fearful
 * Si su taza no está en la torre, no puede entrar 
 * Si está tapando a su taza, no puede salir 
 * Tower debe verificar estas condiciones antes de poder hacer push o pop de Lid 
 */

public class fearfulLid extends Lid 
{
    public fearfulLid (String color, int blockSize, int cupId){ 
        super (color, blockSize, cupId); 
    
    } 
    @Override
    public String getType () { 
        return "fearful"; 
    }
}
