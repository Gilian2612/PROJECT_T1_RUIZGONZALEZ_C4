import shapes.*; 

/**
 * copa tipo hierarchial 
 * Entra en tower y desplaza a todo lo que sea de menor tamaño 
 * si llega al fondo de la torre no se puede eliminar 
 * 
 * @author WSRM
 * @version  4
 * */
 
public class hierarchialCup extends Cup {
    private boolean reachedBottom; 
    public hierarchialCup(String color, int blockSize, int id){
        super(color, blockSize, id); 
        this.reachedBottom = false;
    }
    @Override 
    public String getType(){ 
        return "hierarchial"; 
    } 
    public boolean hasReachedBottom() {
        return reachedBottom;
    }
 
    public void setReachedBottom(boolean reachedBottom) {
        this.reachedBottom = reachedBottom;
    }
}
 
    