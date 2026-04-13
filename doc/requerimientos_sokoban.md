# Análisis y Especificación del Problema — Sokoban

## Información General del Proyecto

| Campo | Descripción |
|-------|-------------|
| **Cliente** | Empresa de desarrollo de software |
| **Usuario** | Personas que deseen jugar el juego Sokoban |
| **Contexto** | Una empresa de desarrollo de software desea construir una versión académica del clásico juego Sokoban como producto base para futuras extensiones (como editores de niveles o rankings globales). El objetivo del juego es ubicar cajas sobre posiciones objetivo en una cuadrícula bidimensional. El sistema deberá validar movimientos, evitar acciones inválidas y detectar automáticamente cuándo se cumple la condición de victoria. Además, permitirá guardar y cargar partidas, mostrar estadísticas del jugador e incluirá un módulo de resolución automática de niveles mediante algoritmos de búsqueda. El desarrollo seguirá buenas prácticas de POO, arquitectura MVC y uso de estructuras de datos y algoritmos eficientes. |

---

## Requerimientos Funcionales

| ID | Nombre |
|----|--------|
| RF1 | Registrar usuario |
| RF2 | Iniciar sesión de usuario |
| RF3 | Cargar nivel desde archivo JSON |
| RF4 | Seleccionar nivel de juego |
| RF5 | Iniciar tablero de juego |
| RF6 | Mover jugador |
| RF7 | Empujar cajas |
| RF8 | Detectar condición de victoria |
| RF9 | Guardar partida |
| RF10 | Cargar partida guardada |
| RF11 | Mostrar estadísticas del jugador |
| RF12 | Mostrar ranking de jugadores |
| RF13 | Contabilizar movimientos realizados |
| RF14 | Contabilizar empujes de cajas |
| RF15 | Medir tiempo de partida |
| RF16 | Ejecutar solución automática del nivel |

---

## Requerimientos No Funcionales

| ID | Descripción |
|----|-------------|
| RNF1 | El sistema debe ser desarrollado en Java. |
| RNF2 | La interfaz gráfica debe ser implementada usando JavaFX. |
| RNF3 | El sistema debe seguir la arquitectura Modelo-Vista-Controlador (MVC). |
| RNF4 | El sistema debe permitir la lectura de archivos JSON mediante una librería adecuada (Gson). |
| RNF5 | El sistema debe garantizar la persistencia de datos de partidas. |
| RNF6 | El sistema debe ser modular, mantenible y escalable. |
| RNF7 | El sistema debe validar todas las entradas del usuario. |
| RNF8 | Las pruebas unitarias deben ejecutarse sin depender de la interfaz gráfica. |

---

## Requerimientos de Proceso

| ID | Descripción |
|----|-------------|
| RP1 | El desarrollo debe realizarse en equipos de tres personas. |
| RP2 | Se debe utilizar control de versiones con Git y GitHub. |
| RP3 | Se debe aplicar la estrategia de ramas Gitflow. |
| RP4 | El desarrollo debe ser incremental por entregas. |
| RP5 | El código debe estar documentado y seguir buenas prácticas de programación. |
| RP6 | Se deben realizar pruebas unitarias para validar el sistema. |

---

## Especificación de Requerimientos Funcionales

### RF1 — Registrar usuario

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir al usuario registrarse antes de acceder al juego, ingresando nombre completo, correo electrónico, nombre de usuario, avatar y nivel de experiencia. El sistema validará que los campos obligatorios no estén vacíos, que el correo tenga formato válido y que el nombre de usuario no esté previamente registrado. |
| **Postcondición** | El usuario queda registrado en el sistema si cumple con todas las condiciones. En caso contrario, se mostrará un mensaje de error. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Nombre completo | `String` | No debe estar vacío; mínimo 3 caracteres |
| Correo electrónico | `String` | Debe tener formato válido (ej: usuario@dominio.com) |
| Nombre de usuario | `String` | Debe ser único y no estar registrado previamente |
| Avatar | `String` | Debe seleccionarse una opción de avatar |
| Nivel de experiencia | `String` | Debe ser uno de: `basico`, `avanzado` o `experto` |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Mensaje de confirmación | `String` | `"Usuario registrado exitosamente"` |
| Mensaje de error | `String` | `"Error en el registro, verifique los datos ingresados"` |

---

### RF2 — Iniciar sesión de usuario

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir al usuario iniciar sesión ingresando su nombre de usuario. El sistema verificará su existencia en el almacenamiento. Si existe, se permite el acceso; de lo contrario, se muestra un mensaje de error. |
| **Postcondición** | El usuario accede al sistema si el nombre de usuario existe. En caso contrario, no se permite el acceso. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Nombre de usuario | `String` | Debe corresponder a un usuario previamente registrado |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Acceso al sistema | `Boolean` | `true` (acceso permitido) / `false` (acceso denegado) |
| Mensaje de confirmación | `String` | `"Inicio de sesión exitoso"` |
| Mensaje de error | `String` | `"Usuario no registrado"` |

---

### RF3 — Cargar nivel desde archivo JSON

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir cargar un nivel desde un archivo externo en formato JSON. El sistema leerá el archivo, interpretará su contenido y construirá el tablero como una cuadrícula bidimensional con muros, cajas, objetivos y la posición del jugador. Se validará la estructura del archivo. |
| **Postcondición** | El tablero queda correctamente cargado en memoria. En caso de error en el archivo, el nivel no se carga. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Archivo JSON del nivel | `Archivo` | Debe ser un archivo JSON válido con la estructura definida del nivel |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Tablero de juego | `Matriz bidimensional` | Estructura que representa el nivel cargado |
| Mensaje de confirmación | `String` | `"Nivel cargado correctamente"` |
| Mensaje de error | `String` | `"Error al cargar el nivel, verifique el archivo"` |

---

### RF4 — Seleccionar nivel de juego

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir al usuario seleccionar el nivel de juego desde un menú principal. Los niveles disponibles son: básico, avanzado y experto. Una vez seleccionado, el sistema cargará el escenario correspondiente. |
| **Postcondición** | El nivel seleccionado es cargado correctamente y queda listo para ser jugado. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Selección de nivel | `String` | Debe ser uno de: `básico`, `avanzado` o `experto` |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Nivel cargado | `String` | Nombre del nivel seleccionado |
| Transición a pantalla de juego | `Interfaz` | Cambio de vista hacia el tablero del nivel seleccionado |

---

### RF5 — Iniciar tablero de juego

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir al usuario visualizar el tablero mediante una interfaz gráfica 2D. El tablero representará una cuadrícula bidimensional con muros, cajas, objetivos y la posición del jugador, actualizándose dinámicamente con cada acción. |
| **Postcondición** | El tablero se muestra correctamente en pantalla y se actualiza en tiempo real. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Estado del tablero | `Matriz` | Debe contener una representación válida del nivel con todos sus elementos |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Representación gráfica del tablero | `Interfaz gráfica` | Visualización en 2D del tablero con sus elementos |

---

### RF6 — Mover jugador

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir al usuario mover al personaje en cuatro direcciones (arriba, abajo, izquierda, derecha) mediante el teclado. Se validará que la posición destino no esté bloqueada. Si el movimiento es válido, se actualizará la posición del jugador. |
| **Postcondición** | La posición del jugador se actualiza si el movimiento es válido. En caso contrario, el tablero permanece sin cambios. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Dirección de movimiento | `String` | Debe ser uno de: `arriba`, `abajo`, `izquierda` o `derecha` |
| Estado actual del tablero | `Matriz` | Debe representar correctamente la posición del jugador y los elementos del nivel |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Nueva posición del jugador | `Coordenadas (x, y)` | Par de valores enteros |
| Estado actual del tablero | `Matriz` | Representación actualizada del tablero |
| Mensaje de error | `String` | `"Movimiento inválido"` |

---

### RF7 — Empujar cajas

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir al jugador empujar una caja al moverse hacia su posición. La acción es válida si la casilla inmediatamente siguiente en la dirección del movimiento está libre. Si se cumple, se actualizará la posición de la caja y del jugador. |
| **Postcondición** | La caja es desplazada y el jugador ocupa su posición anterior, si la acción es válida. En caso contrario, no se realiza ningún cambio. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Dirección de movimiento | `String` | Debe ser uno de: `arriba`, `abajo`, `izquierda` o `derecha` |
| Estado actual del tablero | `Matriz` | Debe representar correctamente jugador, cajas, muros y objetivos |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Nueva posición del jugador | `Coordenadas (x, y)` | Par de valores enteros |
| Nueva posición de la caja | `Coordenadas (x, y)` | Par de valores enteros |
| Estado actual del tablero | `Matriz` | Representación actualizada del tablero |
| Mensaje de error | `String` | `"No es posible empujar la caja"` |

---

### RF8 — Detectar condición de victoria

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe verificar automáticamente después de cada movimiento si todas las cajas están sobre las posiciones objetivo. Si se cumple la condición, el sistema notificará al usuario que el nivel ha sido completado. |
| **Postcondición** | El sistema identifica la victoria cuando todas las cajas están sobre sus objetivos. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Estado del tablero | `Matriz` | Debe representar correctamente la ubicación de cajas y objetivos |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Estado de victoria | `Boolean` | `true` (nivel completado) / `false` (nivel no completado) |
| Mensaje de victoria | `String` | `"Nivel completado exitosamente"` |

---

### RF9 — Guardar partida

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir al usuario guardar el estado actual de la partida en cualquier momento. Se almacenará el tablero, posición del jugador, cajas, nivel actual, movimientos, empujes y tiempo de juego en un archivo persistente. |
| **Postcondición** | El estado de la partida queda almacenado en un archivo para su recuperación posterior. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Estado del tablero | `Matriz` | Debe representar correctamente la ubicación de cajas y objetivos |
| Datos de la partida | `Objeto` | Debe contener información completa del estado del juego (jugador, cajas, nivel, métricas) |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Archivo de partida guardada | `Archivo` | Archivo serializado o en formato JSON |
| Mensaje de confirmación | `String` | `"Partida guardada exitosamente"` |
| Mensaje de error | `String` | `"Error al guardar la partida"` |

---

### RF10 — Cargar partida guardada

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir cargar una partida previamente guardada. Se leerá el archivo, se reconstruirá el tablero y se restaurará la posición del jugador, las cajas, el nivel y las métricas (movimientos, empujes, tiempo). |
| **Postcondición** | El estado del juego es restaurado correctamente y el usuario puede continuar desde el punto guardado. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Archivo de partida guardada | `Archivo` | Debe ser un archivo válido con información completa de una partida previamente guardada |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Estado del tablero | `Matriz` | Representación del tablero reconstruido |
| Datos de la partida restaurada | `Objeto` | Información completa del estado del juego |
| Mensaje de confirmación | `String` | `"Partida cargada exitosamente"` |
| Mensaje de error | `String` | `"Error al cargar la partida"` |

---

### RF11 — Mostrar estadísticas del jugador

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir visualizar las estadísticas del jugador: cantidad de movimientos, empujes de cajas y tiempo empleado por nivel, organizadas de forma clara para analizar su desempeño. |
| **Postcondición** | Las estadísticas del jugador se muestran correctamente organizadas por nivel. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Datos del jugador | `Objeto` | Debe contener información del jugador y sus partidas registradas |
| Historial de partidas | `Lista` | Debe incluir registros válidos de partidas jugadas |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Estadísticas del jugador | `Interfaz` | Visualización estructurada de métricas (movimientos, empujes, tiempo) |
| Resumen de desempeño | `String` | Información textual del rendimiento del jugador |

---

### RF12 — Mostrar ranking de jugadores

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe visualizar un ranking de jugadores según su desempeño, ordenado por menor cantidad de movimientos, menor número de empujes y menor tiempo empleado. |
| **Postcondición** | El ranking se genera y organiza correctamente según los criterios definidos. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Lista de jugadores | `Lista` | Debe contener jugadores con partidas registradas |
| Resultados de partidas | `Lista` | Debe incluir métricas válidas (movimientos, empujes, tiempo) |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Ranking de jugadores | `Interfaz` | Lista ordenada de jugadores según desempeño |
| Posición del jugador | `Entero` | Número que indica la posición en el ranking |

---

### RF13 — Contabilizar movimientos realizados

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe llevar un conteo del número total de movimientos válidos realizados por el jugador. El contador se incrementa en una unidad por cada movimiento válido, se actualiza en tiempo real y se almacena en las estadísticas. |
| **Postcondición** | El contador de movimientos se incrementa correctamente con cada movimiento válido. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Evento de movimiento | `Evento` | Debe corresponder a un movimiento válido del jugador |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Número de movimientos | `Entero` | Valor numérico acumulado |
| Actualización de contador | `Interfaz` | Visualización del número de movimientos en pantalla |

---

### RF14 — Contabilizar empujes de cajas

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe llevar un conteo del número de veces que el jugador empuja una caja. Cada empuje válido incrementa el contador en una unidad. El valor se actualiza en tiempo real y se almacena en las estadísticas. |
| **Postcondición** | El contador de empujes se incrementa correctamente con cada empuje válido. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Evento de empuje de caja | `Evento` | Debe corresponder a un empuje válido realizado por el jugador |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Número de empujes de caja | `Entero` | Valor numérico acumulado |
| Actualización de contador | `Interfaz` | Visualización del número de empujes en pantalla |

---

### RF15 — Medir tiempo de partida

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe medir el tiempo transcurrido desde el inicio del nivel hasta su finalización. El contador inicia al comenzar el nivel, se actualiza continuamente y se detiene al completar el nivel o finalizar la partida. El tiempo se almacena en las estadísticas. |
| **Postcondición** | El tiempo total de la partida queda registrado correctamente. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Evento de inicio de partida | `Evento` | Debe corresponder al inicio del nivel |
| Evento de finalización de partida | `Evento` | Debe corresponder a la finalización del nivel o cierre del juego |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Tiempo de partida | `Tiempo` | Formato minutos y segundos (`mm:ss`) |
| Actualización de cronómetro | `Interfaz` | Visualización del tiempo en pantalla |

---

### RF16 — Ejecutar solución automática del nivel

| Campo | Descripción |
|-------|-------------|
| **Resumen** | El sistema debe permitir ejecutar una solución automática del nivel mediante algoritmos de búsqueda (BFS, DFS u otros). Se generará una secuencia de movimientos que resuelva el nivel, se almacenará en una estructura de datos y se reproducirá visualmente paso a paso en el tablero. |
| **Postcondición** | Se genera una secuencia de movimientos que resuelve el nivel y se almacena para su ejecución. |

**Entradas:**

| Nombre | Tipo | Condición |
|--------|------|-----------|
| Selección de algoritmo | `String` | Debe ser uno de los disponibles: `BFS`, `DFS` u otros implementados |
| Estado inicial del tablero | `Matriz` | Debe representar un nivel válido con posición inicial del jugador y cajas |

**Salidas:**

| Nombre | Tipo | Valor |
|--------|------|-------|
| Secuencia de movimientos | `Cola / Lista` | Conjunto ordenado de direcciones (`arriba`, `abajo`, `izquierda`, `derecha`) |
| Animación de la solución | `Interfaz` | Ejecución visual paso a paso de la solución |
| Mensaje de confirmación | `String` | `"Solución generada exitosamente"` |
| Mensaje de error | `String` | `"No se encontró solución"` |
