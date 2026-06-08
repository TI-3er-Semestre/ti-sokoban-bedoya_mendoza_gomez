package Model;

/**
 * Stores the result of a completed game session for a player on a specific level.
 * Implements Comparable to allow sorting in the leaderboard.
 */
public class GameResult implements Comparable<GameResult> {

    private transient Player player;
    private int levelId;
    private int moves;
    private int pushes;
    private long timeMillis;

    public GameResult(Player player, int levelId, int moves, int pushes, long timeMillis) {
        this.player = player;
        this.levelId = levelId;
        this.moves = moves;
        this.pushes = pushes;
        this.timeMillis = timeMillis;
    }

    /**
     * Compares results: lower moves wins; if tied, lower time wins.
     * @param r other GameResult to compare against
     * @return negative if this is better, positive if r is better
     */
    @Override
    public int compareTo(GameResult r) {

        //se compara por movimeintos
        if (this.moves != r.moves) {
            return this.moves - r.moves;
        }

        //si son iguales en movimientos entonces porel tiempo
        return Long.compare(this.timeMillis, r.timeMillis);

    }

    public Player getPlayer() {
        return player;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getMoves() {
        return moves;
    }

    public int getPushes() {
        return pushes;
    }

    public long getTimeMillis() {
        return timeMillis;
    }
}
