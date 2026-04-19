package tower;

/**
 * @Autor William Santiago Ruiz 
 * Requerimiento ciclo 3 
 * Nueva clase: Resuelve el problema de la maratón
 * @param n es el número de cups 
 * @param h height Tyoe long por requerimientos del problema 
 * h (1 <= h <= 4*10^10)
 * @return String puede ser "Impossible" o h ordenados 
 */
public class TowerContest {
 
    public TowerContest() {}
    
/**
 * +1cm a h solo si se agrega una taza que sea mas alta que la taza que la contiene
 * minHeight = 2n -1 == todas caben en la mas grande 
 * maxHeight = 2n-1 +(n-1) todas las tazaas añaden 1cm 
 * h out of range == impossible 
 */

    /**
     * h de cups = 2n-1
     * 
     */
    public static String solve(int n, long h) {
        if (n <= 0) {
            return "impossible";
        }
        long maxCup = 2L * n - 1;
        long minHeight = maxCup;
        long maxHeight = (n >= 2) ? maxCup + (n - 2) : maxCup;
 
        if (h < minHeight || h > maxHeight) {
            return "impossible";
        }
        if (n == 1) {
            return String.valueOf((int) maxCup);
        }
        int[] small = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            small[i] = 2 * (i + 1) - 1;
        }
        int outOf = (int)(h - maxCup);
        int descCount = (n - 1) - outOf;
        StringBuilder sb = new StringBuilder();
        sb.append((int) maxCup);
        for (int i = descCount - 1; i >= 0; i--) {
            sb.append(" ").append(small[i]);
        }
        for (int i = descCount; i < n - 1; i++) {
            sb.append(" ").append(small[i]);
        }
        return sb.toString();
    }

        
        
    
    /**
     * Simula la solucion del problema con Tower 
     * 
     *@param n 
     *@param h 
     *Muestra la torre o imprime impossible 
     */
    public static void simulate(int n, long h) {
        String solution = solve(n, h);
        if (solution.equals("impossible")) {
            System.out.println("impossible");
            return;
        }
        String[] parts = solution.split(" ");
        int totalHeight = 0;
        for (String part : parts) {
            totalHeight += Integer.parseInt(part);
        }
        Tower tower = new Tower(2 * n, totalHeight);
        tower.makeVisible();
        for (String part : parts) {
            tower.pushCup(Integer.parseInt(part));
        }
    }
    
    /**
     * Solve() sobrecargado, recible h como int 
     * USAR PARA PRUEBAS 
     * h a long y delega en solve (int, long)
     * @param n
     * @param h
     * @return String con h en orden o "impossible"
     */
    public static String solve(int n, int h) {
        return solve(n, (long) h);
    }
    
    /**
     * Sobrecarga de simulate(), recibe h como int 
     * USAR PARA PRUEBAS
     * convierte h a long y delega en simulate (int, long) 
     * @param n 
     * @param h 
     * 
     */
    public static void simulate(int n, int h) {
        simulate(n, (long) h);
    }
    
    /**
     * Método para verificar que el height esté bien 
     * recibe el output "h1 h2 h3 h.. hn " correspondiente a los cm de cada una de las tazas
     * @param order String con h de tazas (de abajo hacia arriba) separadas con esapcio 
     * @return h int altura total de la torre 
     */
    public static int height(String order) {
        String[] parts = order.split(" ");
        int total = Integer.parseInt(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            int current = Integer.parseInt(parts[i]);
            int prev = Integer.parseInt(parts[i - 1]);
            if (current > prev) {
                total += 1;
            }
        }
        return total;
    }
        
}

