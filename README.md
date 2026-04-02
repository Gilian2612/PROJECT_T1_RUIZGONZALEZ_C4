Ciclo 3 RETROSPECTIVA: 

1. Tiempo total invertido
El tiempo total invertido en el CICLO fue de 12 horas
Estado: Completado 
3. Practicas XP: Refactor para el id interno de liddedCus en lugar del blockSize, posicion correcta  de las tapas en pushLid , y la implementación del cover

Mayor logro y problema: El mayor logro fue implementar correctamente el algoritmo de solve() para el problema de la maratón ICPC 2025. Entender la lógica de apilamiento
El mayor problema fue la compatibilidad de tipos entre int y long en los métodos de TowerContest. El enunciado del problema permite valores de h hasta 4·10¹⁰, que supera el límite de int, por lo que fue necesario usar long. Sin embargo, BlueJ no soporta long en su interfaz gráfica de pruebas, lo que obligó a crear métodos sobrecargados que reciben int y delegan en los métodos con long. Esto se resolvió aplicando sobrecarga de métodos manteniendo ambas versiones disponibles.
