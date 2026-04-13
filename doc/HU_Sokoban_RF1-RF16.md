# Historias de Usuario —  Sokoban

---

## Criterio INVEST 

| Criterio | Descripción |
|---|---|
| **I** — Independent | Cada HU puede desarrollarse sin depender de otra |
| **N** — Negotiable | El alcance puede ajustarse en conversación con el cliente |
| **V** — Valuable | Genera valor real para el usuario o el negocio |
| **E** — Estimable | El equipo puede estimar el esfuerzo de implementación |
| **S** — Small | Puede completarse dentro de un Sprint |
| **T** — Testable | Tiene criterios de aceptación claros y verificables |

---

## RF1 — Registrar usuario

| Campo | Detalle |
|---|---|
| **Yo como** | nuevo jugador de la plataforma |
| **Quiero** | registrarme con nombre de usuario, correo electrónico y contraseña |
| **Para** | tener una cuenta propia que guarde mi progreso y estadísticas de juego |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 1.1 — Registro exitoso con datos válidos**
```gherkin
Given el jugador accede al formulario de registro y aún no tiene cuenta
When  ingresa un nombre de usuario único, un correo válido y una contraseña
      de al menos 8 caracteres y presiona "Registrarse"
Then  el sistema crea la cuenta, muestra "Registro exitoso" y redirige al menú principal
And   se envía un correo de bienvenida a la dirección registrada
```

**Escenario 1.2 — Registro con correo ya existente**
```gherkin
Given el jugador accede al formulario de registro
When  ingresa un correo que ya está asociado a otra cuenta
Then  el sistema muestra "El correo ya está registrado" y no crea una cuenta nueva
And   el formulario conserva el nombre de usuario ingresado para corrección
```

**Escenario 1.3 — Registro con contraseña débil**
```gherkin
Given el jugador accede al formulario de registro
When  ingresa una contraseña con menos de 8 caracteres
Then  el sistema muestra "La contraseña debe tener al menos 8 caracteres"
      y no envía el formulario
```

**Escenario 1.4 — Registro con nombre de usuario duplicado**
```gherkin
Given el jugador accede al formulario de registro
When  ingresa un nombre de usuario que ya existe en el sistema
Then  el sistema muestra "El nombre de usuario ya está en uso"
      y sugiere alternativas disponibles
```

---

## RF2 — Iniciar sesión de usuario

| Campo | Detalle |
|---|---|
| **Yo como** | jugador registrado |
| **Quiero** | iniciar sesión con mi correo y contraseña |
| **Para** | acceder a mi perfil, historial de partidas y niveles desbloqueados |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 2.1 — Inicio de sesión exitoso**
```gherkin
Given el jugador accede a la pantalla de inicio de sesión y tiene una cuenta registrada
When  ingresa su correo y contraseña correctos y presiona "Entrar"
Then  el sistema autentica al jugador, carga su perfil y redirige al menú principal
And   se muestra el nombre de usuario en la esquina superior de la pantalla
```

**Escenario 2.2 — Credenciales incorrectas**
```gherkin
Given el jugador accede a la pantalla de inicio de sesión
When  ingresa un correo o contraseña incorrectos
Then  el sistema muestra "Correo o contraseña incorrectos" sin revelar cuál es el error
And   tras 5 intentos fallidos, la cuenta se bloquea por 15 minutos
```

**Escenario 2.3 — Sesión persistente (recordar sesión)**
```gherkin
Given el jugador marca la opción "Recordarme" antes de iniciar sesión
When  cierra el navegador o la aplicación y vuelve a abrirla
Then  el sistema lo reconoce automáticamente y lo lleva al menú principal
      sin pedir credenciales
```

---

## RF3 — Cargar nivel desde archivo JSON

| Campo | Detalle |
|---|---|
| **Yo como** | jugador autenticado |
| **Quiero** | que el sistema cargue los niveles del juego desde archivos JSON |
| **Para** | disponer de niveles con estructura, cajas y objetivos correctamente definidos sin errores de diseño |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 3.1 — Carga exitosa de nivel válido**
```gherkin
Given existe un archivo JSON con la estructura correcta del nivel
      (tablero, cajas, objetivos, posición inicial)
When  el sistema procesa el archivo al seleccionar el nivel
Then  el tablero se renderiza con el jugador, las cajas y los objetivos
      en las posiciones definidas en el JSON
```

**Escenario 3.2 — Archivo JSON con estructura inválida**
```gherkin
Given existe un archivo JSON con campos obligatorios faltantes o mal formados
When  el sistema intenta cargar el nivel
Then  el sistema muestra "El archivo del nivel está corrupto o es inválido"
      y no carga el tablero
And   el jugador permanece en el menú de selección de nivel
```

**Escenario 3.3 — Archivo JSON no encontrado**
```gherkin
Given el nivel seleccionado referencia un archivo JSON que no existe
When  el sistema intenta acceder al archivo
Then  el sistema muestra "Nivel no disponible"
      y ofrece volver al menú de niveles
```

---

## RF4 — Seleccionar nivel de juego

| Campo | Detalle |
|---|---|
| **Yo como** | jugador autenticado |
| **Quiero** | ver y seleccionar un nivel del listado disponible |
| **Para** | elegir el desafío que quiero jugar según mi progreso y dificultad deseada |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 4.1 — Selección de nivel desbloqueado**
```gherkin
Given el jugador accede al menú de selección y tiene niveles disponibles
When  hace clic en un nivel que ya ha desbloqueado o que es el primero disponible
Then  el sistema carga el nivel seleccionado y muestra el tablero listo para jugar
```

**Escenario 4.2 — Intento de selección de nivel bloqueado**
```gherkin
Given el jugador accede al menú de selección de nivel
When  intenta acceder a un nivel bloqueado que aún no ha desbloqueado
Then  el sistema muestra "Debes completar el nivel anterior para desbloquearlo"
      y no carga el nivel
```

**Escenario 4.3 — Visualización de estado de niveles**
```gherkin
Given el jugador accede al menú de niveles
When  observa el listado completo
Then  cada nivel muestra su estado (completado con estrella, disponible o bloqueado)
      y el récord personal de movimientos
```

---

## RF5 — Iniciar tablero de juego

| Campo | Detalle |
|---|---|
| **Yo como** | jugador que ha seleccionado un nivel |
| **Quiero** | que el tablero se muestre correctamente con todos sus elementos iniciales |
| **Para** | comenzar la partida en un estado válido y visualmente comprensible |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 5.1 — Tablero renderizado correctamente**
```gherkin
Given el jugador selecciona un nivel válido
When  el sistema carga el archivo JSON del nivel
Then  el tablero muestra el jugador, las cajas, los objetivos y las paredes
      en las posiciones exactas definidas en el JSON
```

**Escenario 5.2 — Contadores inicializados en cero**
```gherkin
Given el jugador inicia un nivel
When  el tablero se renderiza
Then  el contador de movimientos y el contador de empujes muestran 0
And   el cronómetro inicia en 00:00
```

**Escenario 5.3 — Reinicio del tablero**
```gherkin
Given el jugador está en medio de una partida
When  presiona el botón "Reiniciar"
Then  el tablero vuelve al estado inicial del nivel con todos los contadores en cero
```

---

## RF6 — Mover jugador

| Campo | Detalle |
|---|---|
| **Yo como** | jugador durante una partida activa |
| **Quiero** | mover al personaje en las cuatro direcciones usando teclado o controles táctiles |
| **Para** | navegar por el tablero e interactuar con las cajas para resolver el nivel |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 6.1 — Movimiento válido a celda libre**
```gherkin
Given el jugador está en una posición con al menos una celda libre adyacente
When  presiona la tecla de dirección hacia la celda libre
Then  el personaje se mueve a la celda destino
And   el contador de movimientos aumenta en 1
```

**Escenario 6.2 — Movimiento bloqueado por pared**
```gherkin
Given el jugador está adyacente a una celda de tipo pared
When  intenta moverse hacia la pared
Then  el sistema no mueve al personaje, el contador no cambia
      y se muestra una animación de choque breve
```

**Escenario 6.3 — Movimiento al borde del tablero**
```gherkin
Given el jugador está en una celda del límite del tablero
When  intenta moverse fuera de los límites
Then  el sistema ignora el movimiento y el jugador permanece en su posición actual
```

---

## RF7 — Empujar cajas

| Campo | Detalle |
|---|---|
| **Yo como** | jugador durante una partida activa |
| **Quiero** | empujar las cajas moviéndome hacia ellas cuando hay espacio libre detrás |
| **Para** | colocar cada caja sobre su objetivo y avanzar en la solución del nivel |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 7.1 — Empuje exitoso de caja a celda libre**
```gherkin
Given el jugador está adyacente a una caja que tiene una celda libre al otro lado
When  el jugador se mueve en dirección a la caja
Then  la caja se desplaza a la celda libre
And   el jugador ocupa la posición anterior de la caja
And   el contador de empujes aumenta en 1
```

**Escenario 7.2 — Empuje bloqueado por pared**
```gherkin
Given el jugador está adyacente a una caja que tiene una pared al otro lado
When  intenta empujar la caja hacia la pared
Then  ni el jugador ni la caja se mueven
And   el sistema muestra una animación de bloqueo
```

**Escenario 7.3 — Empuje bloqueado por otra caja**
```gherkin
Given el jugador está adyacente a una caja que tiene otra caja detrás
When  intenta empujar la primera caja
Then  el sistema no permite el movimiento
And   ni el jugador ni ninguna caja cambia de posición
```

**Escenario 7.4 — Caja empujada sobre objetivo**
```gherkin
Given el jugador empuja una caja y detrás de ella hay una celda de objetivo
When  el jugador se mueve hacia la caja
Then  la caja se desplaza sobre el objetivo y cambia de color para indicar posicionamiento correcto
And   el contador de objetivos cumplidos aumenta en 1
```

---

## RF8 — Detectar condición de victoria

| Campo | Detalle |
|---|---|
| **Yo como** | jugador durante una partida activa |
| **Quiero** | que el sistema detecte automáticamente cuando todas las cajas están sobre sus objetivos |
| **Para** | recibir confirmación de que completé el nivel y ver mis resultados finales |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 8.1 — Victoria detectada al colocar la última caja**
```gherkin
Given todas las cajas menos una están sobre sus objetivos correspondientes
When  el jugador empuja la última caja sobre su objetivo
Then  el sistema muestra la pantalla de victoria con los resultados:
      movimientos, empujes y tiempo total
And   se desbloquea el siguiente nivel si existía bloqueado
```

**Escenario 8.2 — Sin victoria si faltan cajas en objetivos**
```gherkin
Given el jugador ha movido cajas pero aún no están todas sobre objetivos
When  el sistema evalúa el estado del tablero tras cada movimiento
Then  la partida continúa sin mostrar pantalla de victoria
```

**Escenario 8.3 — Guardar puntuación récord al completar nivel**
```gherkin
Given el jugador completa el nivel con menos movimientos que su récord anterior
When  se detecta la condición de victoria
Then  el sistema actualiza el récord del jugador para ese nivel
And   lo muestra resaltado en la pantalla de resultados
```

---

## RF9 — Guardar partida

| Campo | Detalle |
|---|---|
| **Yo como** | jugador durante una partida activa |
| **Quiero** | guardar el estado actual de mi partida en cualquier momento |
| **Para** | poder retomar la partida desde el mismo punto sin perder mi progreso |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 9.1 — Guardado exitoso de partida en curso**
```gherkin
Given el jugador tiene una partida activa con al menos un movimiento realizado
When  presiona el botón "Guardar partida"
Then  el sistema almacena el estado del tablero, contador de movimientos,
      empujes y tiempo transcurrido
And   muestra "Partida guardada correctamente" con la hora de guardado
```

**Escenario 9.2 — Sobreescribir partida guardada existente**
```gherkin
Given el jugador ya tiene una partida guardada para ese nivel
When  presiona "Guardar partida" nuevamente
Then  el sistema muestra el diálogo "¿Deseas sobreescribir la partida guardada?"
And   al confirmar, sobreescribe el guardado anterior con el estado actual
```

**Escenario 9.3 — Guardado automático al salir del nivel**
```gherkin
Given el jugador está en una partida activa y decide salir al menú principal
When  presiona "Salir al menú"
Then  el sistema guarda automáticamente el estado de la partida
      antes de redirigir al menú
```

---

## RF10 — Cargar partida guardada

| Campo | Detalle |
|---|---|
| **Yo como** | jugador con una partida previamente guardada |
| **Quiero** | retomar una partida guardada desde el punto exacto donde la dejé |
| **Para** | continuar resolviendo el nivel sin tener que empezar desde el inicio |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 10.1 — Carga exitosa de partida guardada**
```gherkin
Given el jugador tiene al menos una partida guardada
When  selecciona "Continuar partida" desde el menú o la pantalla del nivel
Then  el sistema restaura el tablero con el estado exacto guardado:
      posiciones de cajas, jugador, movimientos y tiempo
```

**Escenario 10.2 — No hay partida guardada disponible**
```gherkin
Given el jugador accede al nivel pero no tiene ninguna partida guardada
When  el sistema busca datos de guardado
Then  muestra "No hay partida guardada para este nivel"
      y ofrece iniciar desde el principio
```

**Escenario 10.3 — Archivo de guardado corrupto**
```gherkin
Given el archivo de guardado existe pero está dañado o incompleto
When  el sistema intenta cargar la partida
Then  muestra "La partida guardada está dañada" y ofrece iniciar el nivel desde el principio
And   el archivo corrupto es eliminado para evitar futuros intentos fallidos
```

---

## RF11 — Mostrar estadísticas del jugador

| Campo | Detalle |
|---|---|
| **Yo como** | jugador registrado |
| **Quiero** | ver mis estadísticas personales de juego |
| **Para** | conocer mi rendimiento general, niveles completados y mis mejores marcas |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 11.1 — Visualización de estadísticas completas**
```gherkin
Given el jugador accede a la sección "Mi perfil" o "Estadísticas"
When  el sistema carga sus datos
Then  se muestran: niveles completados, total de movimientos realizados,
      total de empujes, tiempo total jugado y mejor marca por nivel
```

**Escenario 11.2 — Jugador sin estadísticas aún**
```gherkin
Given el jugador se registró recientemente y no ha completado ningún nivel
When  accede a la sección de estadísticas
Then  el sistema muestra "Aún no tienes estadísticas registradas. ¡Juega tu primer nivel!"
      con un botón de acceso rápido al primer nivel
```

**Escenario 11.3 — Actualización de estadísticas al completar nivel**
```gherkin
Given el jugador completa un nivel exitosamente
When  el sistema procesa la victoria
Then  las estadísticas del jugador se actualizan automáticamente
      incluyendo el nuevo nivel completado y su marca
```

---

## RF12 — Mostrar ranking de jugadores

| Campo | Detalle |
|---|---|
| **Yo como** | jugador registrado |
| **Quiero** | ver el ranking global de jugadores ordenado por rendimiento |
| **Para** | comparar mi desempeño con otros jugadores y motivarme a mejorar mis marcas |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 12.1 — Visualización del ranking global**
```gherkin
Given el jugador accede a la sección "Ranking"
When  el sistema carga los datos del ranking
Then  se muestra la lista de los 10 mejores jugadores con su posición,
      nombre de usuario y puntuación total
```

**Escenario 12.2 — Jugador visualiza su propia posición fuera del top 10**
```gherkin
Given el jugador accede al ranking y su posición supera el top 10
When  el sistema renderiza el ranking
Then  adicionalmente al top 10, se muestra la posición del jugador actual
      resaltada al final de la lista
```

**Escenario 12.3 — Ranking sin suficientes jugadores**
```gherkin
Given la plataforma tiene menos de 2 jugadores con partidas completadas
When  el jugador accede al ranking
Then  el sistema muestra "El ranking estará disponible cuando más jugadores completen niveles"
```

---

## RF13 — Contabilizar movimientos realizados

| Campo | Detalle |
|---|---|
| **Yo como** | jugador durante una partida activa |
| **Quiero** | ver en pantalla el número exacto de movimientos que he realizado |
| **Para** | controlar mi eficiencia e intentar mejorar mi marca en cada intento |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 13.1 — Contador aumenta en movimiento válido**
```gherkin
Given el jugador tiene el tablero activo con el contador en N
When  realiza un movimiento válido (se desplaza a celda libre o empuja una caja)
Then  el contador de movimientos muestra N+1 inmediatamente después del movimiento
```

**Escenario 13.2 — Contador no aumenta en movimiento inválido**
```gherkin
Given el jugador intenta moverse hacia una pared o fuera del tablero
When  el sistema bloquea el movimiento
Then  el contador de movimientos permanece en el mismo valor sin cambio
```

**Escenario 13.3 — Contador se reinicia al reiniciar nivel**
```gherkin
Given el jugador tiene el contador en cualquier valor mayor que 0
When  presiona el botón "Reiniciar"
Then  el contador de movimientos vuelve a 0 y el tablero regresa al estado inicial
```

---

## RF14 — Contabilizar empujes de cajas

| Campo | Detalle |
|---|---|
| **Yo como** | jugador durante una partida activa |
| **Quiero** | ver en pantalla el número exacto de empujes de cajas que he realizado |
| **Para** | medir mi eficiencia en el manejo de cajas y optimizar mi estrategia de solución |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 14.1 — Contador de empujes aumenta al empujar caja**
```gherkin
Given el jugador está adyacente a una caja con espacio libre detrás,
      con el contador de empujes en N
When  empuja la caja exitosamente
Then  el contador de empujes muestra N+1 de manera inmediata
```

**Escenario 14.2 — Contador de empujes no aumenta en movimiento sin caja**
```gherkin
Given el jugador se mueve a una celda libre sin interactuar con ninguna caja
When  el movimiento se completa
Then  el contador de empujes permanece igual
And   solo el contador de movimientos aumenta en 1
```

**Escenario 14.3 — Contador de empujes se reinicia al reiniciar nivel**
```gherkin
Given el jugador tiene el contador de empujes en cualquier valor mayor que 0
When  presiona "Reiniciar"
Then  el contador de empujes vuelve a 0 junto con el de movimientos
```

---

## RF15 — Medir tiempo de partida

| Campo | Detalle |
|---|---|
| **Yo como** | jugador durante una partida activa |
| **Quiero** | ver un cronómetro que registre el tiempo transcurrido desde que inició el nivel |
| **Para** | conocer cuánto tiempo tardé en resolver el nivel y compararlo con mi récord y el de otros |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 15.1 — Cronómetro inicia al cargar el nivel**
```gherkin
Given el jugador carga un nivel nuevo o reinicia el tablero
When  el tablero se renderiza completamente
Then  el cronómetro comienza desde 00:00 y avanza en tiempo real segundo a segundo
```

**Escenario 15.2 — Cronómetro se pausa al pausar la partida**
```gherkin
Given el jugador tiene el cronómetro corriendo
When  presiona el botón "Pausar"
Then  el cronómetro se detiene en el tiempo actual
      y no avanza hasta que el jugador reanude la partida
```

**Escenario 15.3 — Tiempo final registrado al completar nivel**
```gherkin
Given el jugador completa el nivel
When  se detecta la condición de victoria
Then  el cronómetro se detiene y el tiempo final se muestra en la pantalla de resultados
And   si el tiempo es mejor que el récord anterior del jugador, se guarda el nuevo récord
```

**Escenario 15.4 — Cronómetro se reinicia al reiniciar nivel**
```gherkin
Given el cronómetro marca cualquier tiempo mayor que 00:00
When  el jugador presiona "Reiniciar"
Then  el cronómetro vuelve a 00:00 y comienza a contar automáticamente desde el inicio
```

---

## RF16 — Ejecutar solución automática del nivel

| Campo | Detalle |
|---|---|
| **Yo como** | jugador que no puede resolver un nivel |
| **Quiero** | activar un modo de solución automática que muestre cómo se resuelve el nivel paso a paso |
| **Para** | aprender la estrategia de solución y avanzar sin quedarme bloqueado indefinidamente |
| **INVEST** | I ✓ N ✓ V ✓ E ✓ S ✓ T ✓ |

### Criterios de Aceptación

**Escenario 16.1 — Solución automática ejecutada correctamente**
```gherkin
Given el jugador activa "Ver solución" en un nivel con solución precalculada
When  el sistema inicia la reproducción automática
Then  el personaje se mueve automáticamente paso a paso hasta colocar
      todas las cajas en sus objetivos, con una pausa configurable entre movimientos
```

**Escenario 16.2 — Solución no disponible para el nivel**
```gherkin
Given el jugador intenta activar la solución automática en un nivel sin solución precalculada
When  presiona "Ver solución"
Then  el sistema muestra "La solución automática no está disponible para este nivel"
```

**Escenario 16.3 — Detener solución automática a mitad**
```gherkin
Given la solución automática está en ejecución
When  el jugador presiona "Cancelar" o presiona cualquier tecla de movimiento
Then  la solución automática se detiene
And   el jugador retoma el control del personaje en la posición actual
```

**Escenario 16.4 — Solución automática no guarda récord**
```gherkin
Given la solución automática se ejecuta y completa el nivel
When  se detecta la condición de victoria
Then  el sistema muestra "Completado en modo automático - no registrado en ranking"
And   el resultado no se guarda en las estadísticas ni en el ranking del jugador
```

---