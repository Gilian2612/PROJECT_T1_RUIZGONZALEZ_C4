/**
 * Interfaz que Reemplaza a StackEntry.
 *Cada implementación responde por sí misma a:
 *cuanto mide (heightCm, getBlockSize)
 *a que taza pertenece (getCupId)
 *si tiene un dependiente que deba apilarse inmediatamente encima(hasDependant, getDependant)
 * como reposicionarse visualmente (reposition)
 *
 *La regla de "dependiente" permite que normalizeStack() funcione sin
 *preguntar tipo concreto de cada elemento:
 *Cup es dependiente = su Lid (si tiene); si no, null.
 * Lid siempre null (una tapa no arrastra nada encima).
 * @author WSRM
 * @version 3.0 (Ciclo 3 — refactor herencia)
 */
public interface StackItem {
    /** cm en torre. 
       */
    int heightCm();

    /** Tamaño del bloque (blockSize de la taza asociada). 
       */
    int getBlockSize();

    /** Id de la taza a la que pertenece este elemento.
       */
    int getCupId();

    /**cup o lid 
     * 
     */
    String getType();

    void makeVisible();
    void makeInvisible();

    /**
     * Reposiciona elemento en el canvas.
     *
     * @param xLeft es el borde izquierdo interior de la torre (px)
     * @param currentBottom es la coordenada Y del piso disponible (px)
     * @param towerWidthPx es el ancho interior de la torre (px)
     * @return nueva Y del piso tras colocar este elemento
     */
    int reposition(int xLeft, int currentBottom, int towerWidthPx);

    /**
     * Indica si este elemento arrastra un dependiente que debe ir justo encima.
     * Cup con Lid = true.  Cup sin Lid y Lid =  false.
     */
    boolean hasDependant();

    /**
     * Retorna el StackItem dependiente (el Lid de su Cup), o null.
     * Lid siempre retorna null.
     */
    StackItem getDependant();
}