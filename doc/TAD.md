# tipos abstractos de datos (tad)

## 1. tad: lista enlazada (linkedlist)
* **descripcion**: estructura de datos lineal dinamica compuesta por nodos, donde cada nodo contiene un dato y una referencia al nodo siguiente. permite almacenar elementos de manera secuencial sin usar capacidades fijas.
* **invariante**: la lista puede estar vacia. cada nodo apunta a otro nodo o a nulo si es el ultimo. no hay ciclos. la longitud de la lista es igual al numero de nodos conectados.
* **operaciones**:

| operacion | entrada | salida | descripcion | tiempo | espacio |
|---|---|---|---|---|---|
| `add(element)` | elemento | void | agrega un elemento a la lista manteniendo un orden | o(n) | o(1) |
| `remove(element)` | elemento | boolean | elimina la primera ocurrencia del elemento | o(n) | o(1) |
| `get(index)` | int | elemento | retorna el elemento en la posicion dada | o(n) | o(1) |
| `getlength()` | n/a | int | retorna la cantidad de elementos en la lista | o(1) | o(1) |
| `isempty()` | n/a | boolean | indica si la estructura carece de elementos | o(1) | o(1) |
| `iterator()` | n/a | iterator | devuelve un objeto para recorrer los elementos | o(1) | o(1) |

## 2. tad: arbol binario de busqueda (binary search tree)
* **descripcion**: estructura jerarquica donde cada nodo almacena un dato y referencias a sus hijos izquierdo y derecho. se organiza de forma que los valores menores van a la izquierda y los mayores a la derecha.
* **invariante**: para todo nodo, su subarbol izquierdo contiene datos menores y su subarbol derecho contiene datos mayores. no existen valores duplicados y solo hay una raiz.
* **operaciones**:

| operacion | entrada | salida | descripcion | tiempo (promedio) | espacio |
|---|---|---|---|---|---|
| `add(element)` | elemento | void | inserta un elemento nuevo en la estructura | o(log n) | o(1) |
| `search(element)` | elemento | nodo | busca un valor especifico dentro del arbol | o(log n) | o(1) |
| `delete(element)` | elemento | void | borra un dato especifico reacomodando los nodos | o(log n) | o(1) |
| `isempty()` | n/a | boolean | revisa si la estructura no tiene elementos | o(1) | o(1) |
| `getroot()` | n/a | nodo | entrega el nodo principal o raiz | o(1) | o(1) |

## 3. tad: pila (stack)
* **descripcion**: estructura lineal basada en el principio de ultimo en entrar primero en salir.
* **invariante**: unicamente se puede interactuar con el dato ubicado en la cima. no hay acceso a las posiciones intermedias y la capacidad refleja los datos guardados.
* **operaciones**:

| operacion | entrada | salida | descripcion | tiempo | espacio |
|---|---|---|---|---|---|
| `push(element)` | elemento | void | pone un elemento en la parte mas alta | o(1) | o(1) |
| `pop()` | n/a | elemento | saca y entrega el elemento mas alto | o(1) | o(1) |
| `peek()` | n/a | elemento | muestra el elemento mas alto sin sacarlo | o(1) | o(1) |
| `isempty()` | n/a | boolean | dice si la estructura esta sin datos | o(1) | o(1) |

## 4. tad: cola (queue)
* **descripcion**: estructura lineal basada en el principio de primero en entrar primero en salir.
* **invariante**: los elementos entran por la parte trasera y salen por la parte delantera. el orden de llegada se respeta estrictamente y no se accede al medio.
* **operaciones**:

| operacion | entrada | salida | descripcion | tiempo | espacio |
|---|---|---|---|---|---|
| `enqueue(element)` | elemento | void | mete un dato al final de la linea | o(1) | o(1) |
| `dequeue()` | n/a | elemento | saca y entrega el primer dato de la linea | o(1) | o(1) |
| `peek()` | n/a | elemento | muestra el primer dato sin sacarlo | o(1) | o(1) |
| `isempty()` | n/a | boolean | dice si la linea no tiene datos | o(1) | o(1) |

## 5. tad: gamestate
* **descripcion**: objeto que guarda como se encuentra el juego sokoban en un momento especifico, incluyendo el mapa, donde esta el jugador y el nivel.
* **invariante**: el mapa jamas es nulo. el jugador siempre esta en una coordenada que existe en el mapa. es una configuracion que respeta las reglas del juego.
* **operaciones**:

| operacion | entrada | salida | descripcion | tiempo | espacio |
|---|---|---|---|---|---|
| `getboard()` | n/a | board | entrega la cuadricula de juego | o(1) | o(1) |
| `getplayerposition()` | n/a | position | entrega las coordenadas del jugador | o(1) | o(1) |
| `setplayerposition(pos)` | position | void | cambia las coordenadas del jugador | o(1) | o(1) |
| `getmovecount()` | n/a | int | entrega la cantidad de pasos dados | o(1) | o(1) |
| `getpushcount()` | n/a | int | entrega la cantidad de cajas empujadas | o(1) | o(1) |

## 6. tad: grafo (graph)
* **descripcion**: estructura no lineal que representa nodos (vertices) y sus conexiones (aristas) con un costo asociado. puede programarse con listas de conexiones o con una matriz bidimensional.
* **invariante**: dos nodos conectados siempre existen previamente. el costo de conexion es un numero. no se permiten conexiones repetidas con la misma direccion entre dos nodos exactos.
* **operaciones**:

| operacion | entrada | salida | descripcion | tiempo (lista / matriz) | espacio |
|---|---|---|---|---|---|
| `addvertex(data)` | elemento | void | mete un nodo nuevo al sistema | o(1) / o(v^2)* | o(1) |
| `addedge(src, dest, weight)` | elemento, elemento, int | void | conecta dos nodos indicando el costo | o(1) / o(1) | o(1) |
| `containsvertex(data)` | elemento | boolean | dice si un nodo ya esta adentro | o(v) / o(v) | o(1) |
| `containsedge(src, dest)` | elemento, elemento | boolean | dice si dos nodos estan conectados | o(e) / o(1) | o(1) |
| `bfs(start)` | elemento | linkedlist | explora por niveles (anchura) | o(v+e) / o(v^2) | o(v) |
| `dfs(start)` | elemento | linkedlist | explora por caminos profundos (profundidad) | o(v+e) / o(v^2) | o(v) |

*nota: en la matriz agregar vertice es o(v^2) cuando requiere incrementar la capacidad.*