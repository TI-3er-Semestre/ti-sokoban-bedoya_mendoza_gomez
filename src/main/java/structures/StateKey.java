package structures;

import Model.Position;
import java.util.Arrays;

/**
 * Represents a unique identifier for a game state.
 * Includes player position and all box positions.
 */
public class StateKey {

    private Position playerPosition;
    private Position[] boxPositions;

    public StateKey(Position playerPosition, Position[] boxPositions) {
        this.playerPosition = playerPosition;

        this.boxPositions = new Position[boxPositions.length];
        for (int i = 0; i < boxPositions.length; i++) {
            Position p = boxPositions[i];
            this.boxPositions[i] = new Position(p.getRow(), p.getCol());
        }

        Arrays.sort(this.boxPositions, (a, b) -> {
            if (a.getRow() != b.getRow()) return a.getRow() - b.getRow();
            return a.getCol() - b.getCol();
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateKey)) return false;

        StateKey other = (StateKey) o;

        if (!playerPosition.equals(other.playerPosition)) return false;

        return Arrays.equals(boxPositions, other.boxPositions);
    }

    @Override
    public int hashCode() {
        int result = playerPosition.hashCode();
        result = 31 * result + Arrays.hashCode(boxPositions);
        return result;
    }
}