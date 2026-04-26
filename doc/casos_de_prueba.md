# Diseño de Casos de Prueba — Sokoban

## Configuración de Escenarios

| Nombre | Clase | Escenario |
|--------|-------|-----------|
| `setupStage1` | `GameControllerTest` | Tablero 5×5. Jugador en (2,2). Celda libre en (2,1). Muro en (2,0). Sin cajas ni objetivos activos. |
| `setupStage2` | `GameControllerTest` | Tablero 5×5. Jugador en (2,2). Caja en (2,1). Celda libre en (2,0). Sin objetivos. |
| `setupStage3` | `GameControllerTest` | Tablero 5×5. Jugador en (2,2). Caja en (2,1). Muro en (2,0). Jugador no puede empujar la caja. |
| `setupStage4` | `GameControllerTest` | Tablero 5×5. Jugador en (2,3). Caja en (2,2). Caja en (2,1). Celda libre en (2,0). Dos cajas consecutivas. |
| `setupStage5` | `GameStateTest` | Tablero 4×4. 2 objetivos en (0,0) y (0,1). 2 cajas: una en (0,0) sobre objetivo, otra en (1,1) fuera de objetivo. Jugador en (3,3). |
| `setupStage6` | `GameStateTest` | Tablero 4×4. 2 objetivos en (0,0) y (0,1). 2 cajas: ambas sobre sus objetivos. Jugador en (3,3). |
| `setupStage7` | `BoardTest` | Tablero 5×5 vacío (solo `EmptyCell`s). Se verifican posiciones válidas e inválidas. |

---

## Diseño de Casos de Prueba

### Prueba 1: Movimientos válidos e inválidos

**Objetivo de la prueba:** Verificar que el método `move()` de `GameController` permita o bloquee el desplazamiento del jugador según las condiciones del tablero.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|-------|--------|-----------|-------------------|-------------------|
| `GameControllerTest` | `move()` | `setupStage1` | `Direction.LEFT` | `true` — Jugador se mueve a (2,1). Celda (2,2) queda `EmptyCell`. |
| `GameControllerTest` | `move()` | `setupStage1` | `Direction.UP` | `true` — Jugador se mueve a (1,2). Movimiento válido hacia celda libre. |
| `GameControllerTest` | `move()` | `setupStage1` | `Direction.DOWN` | `true` — Jugador se mueve a (3,2). Movimiento válido hacia celda libre. |
| `GameControllerTest` | `move()` | `setupStage1` | `Direction.RIGHT` | `true` — Jugador se mueve a (2,3). Movimiento válido hacia celda libre. |
| `GameControllerTest` | `move()` | `setupStage1` | Dos veces `Direction.LEFT` | `false` — Segundo movimiento bloqueado. Jugador permanece en (2,1). Hay muro en (2,0). |

---

### Prueba 2: Validación de movimiento (`isValidMove`)

**Objetivo de la prueba:** Verificar que `isValidMove()` retorne correctamente para distintas direcciones según el estado del tablero.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|-------|--------|-----------|-------------------|-------------------|
| `GameControllerTest` | `isValidMove()` | `setupStage1` | `Direction.LEFT` (hacia celda libre) | `true` |
| `GameControllerTest` | `isValidMove()` | `setupStage1` | `Direction.LEFT` desde (2,1) — muro en (2,0) | `false` |

---

### Prueba 3: Empuje correcto de cajas

**Objetivo de la prueba:** Verificar que el empuje de cajas funcione correctamente: la caja se desplaza cuando la celda detrás está libre, y se queda cuando está bloqueada.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|-------|--------|-----------|-------------------|-------------------|
| `GameControllerTest` | `move()` | `setupStage2` | `Direction.LEFT` | `true` — Jugador en (2,1), caja empujada a (2,0). `isPushingBox()` retorna `true`. |
| `GameControllerTest` | `move()` | `setupStage3` | `Direction.LEFT` | `false` — Caja en (2,1) bloqueada por muro en (2,0). Jugador no se mueve. |
| `GameControllerTest` | `move()` | `setupStage4` | `Direction.LEFT` | `false` — Dos cajas consecutivas en (2,2) y (2,1). No se puede empujar. Jugador no se mueve. |
| `GameControllerTest` | `isPushingBox()` | `setupStage2` | `Direction.LEFT` | `true` — Hay caja en la dirección del movimiento. |
| `GameControllerTest` | `isPushingBox()` | `setupStage1` | `Direction.LEFT` | `false` — Celda libre en la dirección. No hay caja. |

---

### Prueba 4: Bloqueo por muros u obstáculos

**Objetivo de la prueba:** Verificar que el sistema bloquee el movimiento del jugador ante muros directos, muros detrás de cajas y bordes del tablero.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|-------|--------|-----------|-------------------|-------------------|
| `GameControllerTest` | `move()` | `setupStage1` | Dos veces `Direction.LEFT` | `false` — Muro directo en (2,0). Jugador bloqueado en (2,1). |
| `GameControllerTest` | `move()` | `setupStage3` | `Direction.LEFT` | `false` — Muro detrás de caja en (2,0). Jugador no puede empujar ni moverse. |
| `BoardTest` | `isValidPosition()` | `setupStage7` | fila=`-1`, col=`0` | `false` — Posición fuera del tablero (borde superior). |
| `BoardTest` | `isValidPosition()` | `setupStage7` | fila=`5`, col=`0` | `false` — Posición fuera del tablero (borde inferior en tablero 5×5). |
| `BoardTest` | `isValidPosition()` | `setupStage7` | fila=`2`, col=`2` | `true` — Posición válida dentro del tablero. |
| `BoardTest` | `getCell()` | `setupStage1` | fila=`2`, col=`0` | Retorna instancia de `WallCell`. `isWalkable()` retorna `false`. |

---

### Prueba 5: Verificación de victoria

**Objetivo de la prueba:** Verificar que `isVictory()` en `GameState` e `isCompleted()` en `Model.Board` detecten correctamente el estado de victoria cuando todas las cajas están sobre los objetivos.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|-------|--------|-----------|-------------------|-------------------|
| `GameStateTest` | `isVictory()` | `setupStage6` | — (estado ya completo) | `true` — Todas las cajas sobre objetivos. Victoria detectada. |
| `GameStateTest` | `isVictory()` | `setupStage5` | — (solo una caja en objetivo) | `false` — Una caja fuera del objetivo. No hay victoria. |
| `BoardTest` | `isCompleted()` | `setupStage6` | — (todas las celdas objetivo con caja) | `true` — Todas las `goalPositions` están cubiertas por `BoxOnGoalCell`. |
| `BoardTest` | `isCompleted()` | `setupStage5` | — (una `goalPosition` sin caja) | `false` — Al menos una `GoalCell` sin caja encima. |
| `GameControllerTest` | `checkVictory()` | `setupStage6` | — (estado ganador) | `true` — El controlador detecta la victoria y activa la pantalla de victoria. |
| `GameControllerTest` | `checkVictory()` | `setupStage5` | — (estado no ganador) | `false` — No todas las cajas en objetivos. No se activa victoria. |


#  Diseño de Casos de Prueba — Estructuras de Datos

## 📌 Introducción

Este documento presenta los casos de prueba diseñados para validar el correcto funcionamiento de las estructuras de datos implementadas en el proyecto.

Cada operación incluye:
- Caso estándar
- Caso límite
- Caso interesante

---

#  Queue

## Prueba 1: Operaciones FIFO

**Objetivo:** Validar comportamiento de cola (FIFO).

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|------|--------|----------|-------------------|-------------------|
| QueueTest | enqueue() | Inserción normal | 5 | Tamaño = 1 |
| QueueTest | dequeue() | Extracción normal | [1,2] | Retorna 1 |
| QueueTest | dequeue() | Cola vacía | — | Retorna null |

---

# Stack

## Prueba 2: Operaciones LIFO

**Objetivo:** Validar comportamiento de pila (LIFO).

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|------|--------|----------|-------------------|-------------------|
| StackTest | push() | Inserción normal | 10 | Tamaño = 1 |
| StackTest | pop() | Extracción normal | [1,2] | Retorna 2 |
| StackTest | pop() | Pila vacía | — | Retorna null |

---

#  PriorityQueue

## Prueba 3: Orden por prioridad

**Objetivo:** Verificar orden automático de elementos.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|------|--------|----------|-------------------|-------------------|
| PriorityQueueTest | enqueue() | Inserción desordenada | 5,1,3 | Frente = 1 |
| PriorityQueueTest | dequeue() | Extracción | [5,1,3] | Retorna 1 |
| PriorityQueueTest | dequeue() | Cola vacía | — | Retorna null |

---

#  TranspositionTable (Hash Table)

## Prueba 4: Inserción y búsqueda

**Objetivo:** Validar almacenamiento y consulta de estados.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|------|--------|----------|-------------------|-------------------|
| TranspositionTableTest | add() | Inserción normal | key(1,1) | Se almacena correctamente |
| TranspositionTableTest | contains() | Búsqueda existente | key(1,1) | true |
| TranspositionTableTest | contains() | Búsqueda inexistente | key(2,2) | false |

---

#  StateKey

## Prueba 5: Igualdad y hashing

**Objetivo:** Verificar consistencia de equals y hashCode.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|------|--------|----------|-------------------|-------------------|
| StateKeyTest | equals() | Mismos datos | (1,1) vs (1,1) | true |
| StateKeyTest | equals() | Datos diferentes | (1,1) vs (2,2) | false |
| StateKeyTest | hashCode() | Consistencia | mismos datos | Hash iguales |

---

# Iterator

## Prueba 6: hasNext()

**Objetivo:** Verificar si el iterador detecta correctamente elementos restantes.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|------|--------|----------|-------------------|-------------------|
| IteratorTest | hasNext() | Lista con elementos | [1,2,3] | true |
| IteratorTest | hasNext() | Lista vacía | [] | false |
| IteratorTest | hasNext() | Un elemento | [5] | true → false |

---

## Prueba 7: next()

**Objetivo:** Validar que el iterador retorne los elementos en orden.

| Clase | Método | Escenario | Valores de entrada | Resultado esperado |
|------|--------|----------|-------------------|-------------------|
| IteratorTest | next() | Recorrido completo | [1,2,3] | 1 → 2 → 3 |
| IteratorTest | next() | Lista vacía | [] | No retorna elementos |
| IteratorTest | next() | Un elemento | [5] | Retorna 5 |

---


