public class TowerException extends RuntimeException {
    private final String unknownType; 
    
    public TowerException(String unknownType) {
        super("Tipo de StackItem desconocido: '" + unknownType + "'. Se esperaba 'cup' o 'lid'.");
        this.unknownType = unknownType;
    }
 
    public String getUnknownType() {
        return unknownType;
    }
}
        