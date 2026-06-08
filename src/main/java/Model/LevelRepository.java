package Model;

import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for loading Sokoban levels from JSON files using Gson.
 * Follows the Repository pattern to separate persistence from business logic.
 */
public class LevelRepository {

    private String levelsPath;

    public LevelRepository(String levelsPath) {
        this.levelsPath = levelsPath;
    }

    /**
     * Intermediate class for Gson deserialization.
     * Represents the raw JSON structure before conversion to Level.
     */
    private static class LevelData {
        int levelId;
        String name;
        String difficulty;
        int rows;
        int cols;
        String[][] grid;
    }

    /**
     * Loads a single level from its corresponding JSON file.
     * @param id level identifier
     * @return Level object, or null if not found
     */
    public Level loadLevel(int id) {
        String filePath = levelsPath + File.separator + "level" + id + ".json";
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("Level file not found: " + filePath);
            return null;
        }

        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            LevelData data = gson.fromJson(reader, LevelData.class);
            return buildLevel(data);
        } catch (IOException e) {
            System.err.println("Error loading level: " + e.getMessage());
            return null;
        }
    }

    /**
     * Converts a LevelData (raw JSON) into a proper Level with a Board.
     * Character mapping:
     *   '#' = WallCell
     *   ' ' = EmptyCell
     *   '$' = BoxCell
     *   '.' = GoalCell
     *   '*' = BoxOnGoalCell
     *   '@' = PlayerCell
     *   '+' = PlayerOnGoalCell
     */
    private Level buildLevel(LevelData data) {
        if (data == null) return null;

        Board board = new Board(data.rows, data.cols);
        List<Position> goals = new ArrayList<>();

        for (int r = 0; r < data.rows; r++) {
            for (int c = 0; c < data.cols; c++) {
                String symbol = data.grid[r][c];
                Cell cell;

                switch (symbol) {
                    case "#":  cell = new WallCell();        break;
                    case "$":  cell = new BoxCell();         break;
                    case ".":  cell = new GoalCell();
                        goals.add(new Position(r, c));
                        break;
                    case "*":  cell = new BoxOnGoalCell();
                        goals.add(new Position(r, c));
                        break;
                    case "@":  cell = new PlayerCell();      break;
                    case "+":  cell = new PlayerOnGoalCell();
                        goals.add(new Position(r, c));
                        break;
                    default:   cell = new EmptyCell();       break;
                }

                board.setCell(r, c, cell);
            }
        }

        // Asignar goalPositions al board
        board.setGoalPositions(goals.toArray(new Position[0]));

        ExperienceLevel difficulty = ExperienceLevel.valueOf(data.difficulty);

        return new Level(data.levelId, data.name, difficulty, board);
    }

    /**
     * Loads all available levels from the levels directory.
     */
    public Level[] loadAllLevels() {
        File dir = new File(levelsPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        if (files == null) return new Level[0];

        Level[] levels = new Level[files.length];

        for (int i = 0; i < files.length; i++) {
            try (Reader reader = new FileReader(files[i])) {
                Gson gson = new Gson();
                LevelData data = gson.fromJson(reader, LevelData.class);
                levels[i] = buildLevel(data);
            } catch (IOException e) {
                levels[i] = null;
            }
        }

        return levels;
    }

    /**
     * Returns true if a level file exists for the given id.
     */
    public boolean levelExists(int id) {
        String filePath = levelsPath + File.separator + "level" + id + ".json";
        return new File(filePath).exists();
    }

    /**
     * Validates that a level has consistent data.
     */
    public boolean validateLevel(Level level) {
        if (level == null || level.getInitialBoard() == null) return false;

        Board board = level.getInitialBoard();
        int players = 0, boxes = 0, goals = 0;

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Cell c = board.getCell(i, j);
                if (c instanceof PlayerCell || c instanceof PlayerOnGoalCell) players++;
                if (c instanceof BoxCell || c instanceof BoxOnGoalCell) boxes++;
                if (c instanceof GoalCell || c instanceof BoxOnGoalCell
                        || c instanceof PlayerOnGoalCell) goals++;
            }
        }

        return players == 1 && boxes >= 1 && goals >= 1 && boxes == goals;
    }

    /**
     * Returns the total number of level files available.
     */
    public int getLevelCount() {
        File dir = new File(levelsPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        return files == null ? 0 : files.length;
    }
}
