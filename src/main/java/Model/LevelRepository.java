package Model;

import java.io.*;

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
     * Loads a single level from its corresponding JSON file.
     * @param id level identifier
     * @return Level object, or null if not found
     */
    public Level loadLevel(int id) {
        String filePath = levelsPath + File.separator + "level" + id + ".json";
        File file = new File(filePath);

        if (!file.exists()) return null;

        try (Reader reader = new FileReader(file)) {

            com.google.gson.Gson gson = new com.google.gson.Gson();
            return gson.fromJson(reader, Level.class);

        } catch (IOException e) {
            System.err.println("Error al cargar el nivel: " + e.getMessage());
            return null;
        }
    }

    /**
     * Loads all available levels from the levels directory.
     * @return array of Level objects
     */
    public Level[] loadAllLevels() {
        File dir = new File(levelsPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        if (files == null) return new Level[0];

        Level[] levels = new Level[files.length];

        for (int i = 0; i < files.length; i++) {
            try (Reader reader = new FileReader(files[i])) {
                com.google.gson.Gson gson = new com.google.gson.Gson();
                levels[i] = gson.fromJson(reader, Level.class);
            } catch (IOException e) {
                levels[i] = null;
            }
        }

        return levels;
    }

    /**
     * Returns true if a level file exists for the given id.
     * @param id level identifier
     * @return true if level file exists
     */
    public boolean levelExists(int id) {
        String filePath = levelsPath + File.separator + "level" + id + ".json";
        return new File(filePath).exists();
    }

    /**
     * Validates that a level has consistent data:
     * exactly one player, at least one box and one goal, equal boxes and goals.
     * @param level level to validate
     * @return true if valid
     */
    public boolean validateLevel(Level level) {

        if (level == null || level.getInitialBoard() == null) return false;

        Board board = level.getInitialBoard();

        int players = 0;
        int boxes = 0;
        int goals = 0;

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {

                Cell c = board.getCell(i, j);

                if (c instanceof PlayerCell || c instanceof PlayerOnGoalCell) {
                    players++;
                }

                if (c instanceof BoxCell || c instanceof BoxOnGoalCell) {
                    boxes++;
                }

                if (c instanceof GoalCell ||
                        c instanceof BoxOnGoalCell ||
                        c instanceof PlayerOnGoalCell) {
                    goals++;
                }
            }
        }

        return players == 1 && boxes >= 1 && goals >= 1 && boxes == goals;
    }

    /**
     * Returns the total number of level files available.
     * @return count of available levels
     */
    public int getLevelCount() {
        File dir = new File(levelsPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        if (files == null) return 0;

        return files.length;
    }
}
