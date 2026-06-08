package controller;

import Model.*;
import gui.SokobanApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class GameScreenController {
    @FXML private BorderPane rootPane;
    @FXML private Canvas gameCanvas;
    @FXML private Label lblMoves;
    @FXML private Label lblPushes;
    @FXML private Label lblTime;
    @FXML private Label lblLevel;
    @FXML private Button btnUndo;
    @FXML private Button btnReset;
    @FXML private Button btnSave;
    @FXML private Button btnMenu;

    // Tamaño de cada celda renderizada en pantalla (escala x3 sobre los 16px originales)
    private static final int CELL_SIZE   = 48;
    // Tamaño original del tile en el spritesheet
    private static final int TILE_SIZE   = 16;

    // ── Coordenadas en el spritesheet (col*16, row*16) — índices desde 0 ──
    // Muros
    private static final int[] WALL_1      = {0,  0};   // fila 1, col 1
    private static final int[] WALL_2      = {16, 0};   // fila 1, col 2
    private static final int[] WALL_3      = {32, 0};   // fila 1, col 3
    private static final int[] WALL_4      = {32, 16};  // fila 2, col 3
    // Terreno / EmptyCell
    private static final int[] FLOOR_1    = {0,  16};  // fila 2, col 1
    private static final int[] FLOOR_2    = {16, 16};  // fila 2, col 2
    // Objetivo / GoalCell
    private static final int[] GOAL       = {64, 0};   // fila 1, col 5
    // Cajas
    private static final int[] BOX_1      = {0,  64};  // fila 5, col 1
    private static final int[] BOX_2      = {16, 64};  // fila 5, col 2

    private Image tileset;
    private GameController gameController;

    // Hilo del temporizador
    private Thread timerThread;
    private volatile boolean timerRunning = false;

    @FXML
    void initialize() {
        gameController = MainMenuController.getGameController();

        // Cargar tileset desde resources
        tileset = new Image(
                getClass().getResourceAsStream("/images/tileset.png")
        );

        System.out.println("Tileset cargado: " + !tileset.isError());
        System.out.println("Tileset ancho: " + tileset.getWidth());
        System.out.println("Tileset alto: " + tileset.getHeight());
        System.out.println("Estado: " + gameController.getCurrentState());

        Platform.runLater(() -> {
            // Solo cargar nivel nuevo si no estamos retomando partida
            if (!gameController.isResumingGame()) {
                gameController.loadLevel(1);
            } else {
                gameController.setResumingGame(false); // resetear bandera
            }
            gameController.startTimer();
            drawBoard();
            updateCounters();
            startTimerThread();
            rootPane.getScene().setOnKeyPressed(this::handleKeyPress);
            rootPane.requestFocus();
        });
        gameController.startTimer();

        Platform.runLater(() -> {
            drawBoard();
            updateCounters();
            startTimerThread();
            rootPane.getScene().setOnKeyPressed(this::handleKeyPress);
            rootPane.requestFocus();  // ← verifica que esta línea esté
        });
    }

    // ---------------------------------------------------------------
    // Renderizado con tileset
    // ---------------------------------------------------------------

    private void drawBoard() {
        GameState state = gameController.getCurrentState();
        if (state == null) return;

        Board board = state.getBoard();
        int rows = board.getRows();
        int cols = board.getCols();

        gameCanvas.setWidth(cols  * CELL_SIZE);
        gameCanvas.setHeight(rows * CELL_SIZE);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                drawCell(gc, board.getCell(r, c), r, c);
            }
        }
    }

    /**
     * Draws a single cell using the tileset spritesheet.
     * Uses alternating tiles for visual variety on walls and floors.
     */
    private void drawCell(GraphicsContext gc, Cell cell, int row, int col) {
        double destX = col * CELL_SIZE;
        double destY = row * CELL_SIZE;

        if (cell instanceof WallCell) {
            // Alternar variantes de muro según posición para variedad visual
            int[] tile = selectWallTile(row, col);
            drawTile(gc, tile, destX, destY);

        } else if (cell instanceof EmptyCell) {
            // Alternar entre dos texturas de terreno
            int[] tile = ((row + col) % 2 == 0) ? FLOOR_1 : FLOOR_2;
            drawTile(gc, tile, destX, destY);

        } else if (cell instanceof GoalCell) {
            // Terreno debajo + objetivo encima
            drawTile(gc, FLOOR_1, destX, destY);
            drawTile(gc, GOAL,    destX, destY);

        } else if (cell instanceof BoxCell) {
            // Terreno debajo + caja encima
            drawTile(gc, FLOOR_1, destX, destY);
            drawTile(gc, BOX_1,   destX, destY);

        } else if (cell instanceof BoxOnGoalCell) {
            // Terreno + objetivo + caja variante 2 (caja sobre meta)
            drawTile(gc, FLOOR_1, destX, destY);
            drawTile(gc, GOAL,    destX, destY);
            drawTile(gc, BOX_2,   destX, destY);

        } else if (cell instanceof PlayerCell) {
            // Terreno debajo + jugador (círculo naranja sobre el tile)
            drawTile(gc, FLOOR_1, destX, destY);
            drawPlayer(gc, destX, destY, false);

        } else if (cell instanceof PlayerOnGoalCell) {
            // Terreno + objetivo + jugador
            drawTile(gc, FLOOR_1, destX, destY);
            drawTile(gc, GOAL,    destX, destY);
            drawPlayer(gc, destX, destY, true);
        }
    }

    /**
     * Selects a wall tile variant based on position for visual variety.
     */
    private int[] selectWallTile(int row, int col) {
        int variant = (row * 3 + col) % 4;
        switch (variant) {
            case 0:  return WALL_1;
            case 1:  return WALL_2;
            case 2:  return WALL_3;
            default: return WALL_4;
        }
    }

    /**
     * Draws a tile from the spritesheet at the given canvas coordinates.
     * Scales from TILE_SIZE (16px) to CELL_SIZE (48px).
     */
    private void drawTile(GraphicsContext gc, int[] tile, double destX, double destY) {
        gc.drawImage(
                tileset,
                tile[0], tile[1], TILE_SIZE, TILE_SIZE,  // fuente en spritesheet
                destX,   destY,   CELL_SIZE, CELL_SIZE   // destino en canvas (escalado)
        );
    }

    /**
     * Draws the player as a colored circle overlay on the current tile.
     * @param onGoal true if player is standing on a goal (changes color)
     */
    private void drawPlayer(GraphicsContext gc, double destX, double destY, boolean onGoal) {
        int padding = 6;
        gc.setFill(onGoal ? Color.web("#16c79a") : Color.web("#f5a623"));
        gc.fillOval(
                destX + padding,
                destY + padding,
                CELL_SIZE - padding * 2,
                CELL_SIZE - padding * 2
        );
        // Contorno
        gc.setStroke(Color.web("#1a1a2e"));
        gc.setLineWidth(2);
        gc.strokeOval(
                destX + padding,
                destY + padding,
                CELL_SIZE - padding * 2,
                CELL_SIZE - padding * 2
        );
        // Cara simple
        gc.setFill(Color.web("#1a1a2e"));
        gc.fillOval(destX + 16, destY + 16, 5, 5); // ojo izquierdo
        gc.fillOval(destX + 27, destY + 16, 5, 5); // ojo derecho
        gc.strokeArc(destX + 16, destY + 24, 16, 8, 0, -180,
                javafx.scene.shape.ArcType.OPEN);  // boca
    }

    // ---------------------------------------------------------------
    // Temporizador — hilo separado
    // ---------------------------------------------------------------

    private void startTimerThread() {
        timerRunning = true;
        timerThread = new Thread(() -> {
            while (timerRunning) {
                try {
                    Thread.sleep(1000);
                    long elapsed = gameController.getElapsedTime();
                    String timeStr = formatTime(elapsed);
                    Platform.runLater(() -> lblTime.setText("⏱ " + timeStr));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        timerThread.setDaemon(true);
        timerThread.setName("TimerThread");
        timerThread.start();
    }

    private void stopTimerThread() {
        timerRunning = false;
        if (timerThread != null) timerThread.interrupt();
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // ---------------------------------------------------------------
    // Teclado
    // ---------------------------------------------------------------

    @FXML
    void handleKeyPress(KeyEvent event) {
        Direction direction = null;
        switch (event.getCode()) {
            case UP:    case W: direction = Direction.UP;    break;
            case DOWN:  case S: direction = Direction.DOWN;  break;
            case LEFT:  case A: direction = Direction.LEFT;  break;
            case RIGHT: case D: direction = Direction.RIGHT; break;
            default: return;
        }

        boolean moved = gameController.move(direction);
        if (moved) {
            drawBoard();
            updateCounters();
            if (gameController.checkVictory()) {
                stopTimerThread();
                showVictory();
            }
        }
    }

    // ---------------------------------------------------------------
    // Contadores y botones
    // ---------------------------------------------------------------

    private void updateCounters() {
        lblMoves.setText("Movimientos: " + gameController.getMoveCount());
        lblPushes.setText("Empujes: " + gameController.getPushCount());
        lblLevel.setText("Nivel: " + (gameController.getCurrentState() != null
                ? gameController.getCurrentState().getCurrentLevel() : "-"));
    }

    @FXML void handleUndo() {
        gameController.undo();
        drawBoard();
        updateCounters();
        rootPane.requestFocus();
    }

    @FXML void handleReset() {
        gameController.resetLevel();
        drawBoard();
        updateCounters();
        stopTimerThread();
        gameController.startTimer();
        startTimerThread();
        rootPane.requestFocus();
    }

    @FXML void handleSave() {
        gameController.saveGame();
        showInfo("Partida guardada correctamente.");
        rootPane.requestFocus();
    }

    @FXML void handleMenu() {
        stopTimerThread();
        try {
            SokobanApplication.navigateTo("MainMenu");
        } catch (Exception e) {
            showError("Error al volver al menú.");
        }
    }

    private void showVictory() {
        try {
            SokobanApplication.navigateTo("Victory");
        } catch (Exception e) {
            showError("Error al cargar la pantalla de victoria.");
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Información");
        a.setContentText(msg);
        a.showAndWait();
    }
}
