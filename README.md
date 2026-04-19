Ciclo 3 RETROSPECTIVA: 

1. Tiempo total invertido
El tiempo total invertido en el CICLO fue de 12 horas
Estado: Completado 
3. Practicas XP: Refactor para el id interno de liddedCus en lugar del blockSize, posicion correcta  de las tapas en pushLid , y la implementación del cover

Mayor logro y problema: El mayor logro fue implementar correctamente el algoritmo de solve() para el problema de la maratón ICPC 2025. Entender la lógica de apilamiento
El mayor problema fue la compatibilidad de tipos entre int y long en los métodos de TowerContest. El enunciado del problema permite valores de h hasta 4·10¹⁰, que supera el límite de int, por lo que fue necesario usar long. Sin embargo, BlueJ no soporta long en su interfaz gráfica de pruebas, lo que obligó a crear métodos sobrecargados que reciben int y delegan en los métodos con long. Esto se resolvió aplicando sobrecarga de métodos manteniendo ambas versiones disponibles.




Análisis Dinámico (EclEmma/JaCoCo)Resultado inicial: La cobertura del paquete tower era de 58.7% y la del paquete shapes de 59.2%, La mayoría de métodos de Tower, Cup, Lid, StackItem y TowerContest no estaban siendo ejercitados por las pruebas existentes.Decisiones tomadas: Se creó la clase TowerCoverageTest.java con pruebas unitarias adicionales que cubren métodos faltantes de Lid (makeVisible, makeInvisible, reposition, moveTo, setupTriangle, setupRectangle), Cup (makeVisible, makeInvisible, reposition, moveTo, setLid, vanish), StackItem (variantes cup y lid, dependant, reposition), TowerContest (solve con casos límite, height, simulate) y Tower (orderTower, reverseTower, swap, swapToReduce, exit, cover, popCup, removeCup/removeLid con casos inválidos, pushCup con duplicados y sin espacio).Resultado final: Cobertura del paquete tower: 90.0%. Cobertura del paquete shapes: 49.2%. Cobertura total del proyecto: 84.1%. Se superó la meta del 75% en código de dominio.

HORAS DEDICADAS: 12h/ Cada uno 
