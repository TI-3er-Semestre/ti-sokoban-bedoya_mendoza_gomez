package Model;

import Model.Direction;
/**
 * Represents a registered player with their profile and statistics.
 */
public class Player {

    private String name;
    private String email;
    private String username;
    private String avatar;
    private ExperienceLevel experienceLevel;
    private PlayerStats stats;
    private Direction currentDirection;

    public Player(String name, String email, String username,
                  String avatar, ExperienceLevel experienceLevel) {

        this.name = name;
        this.email = email;
        this.username = username;
        this.avatar = avatar;
        this.experienceLevel = experienceLevel;

        this.stats = new PlayerStats();
        this.currentDirection = Direction.DOWN;
    }

    public Player(String username) {
        this.username = username;
        this.name = username;
        this.email = "";
        this.avatar = "";
        this.experienceLevel = ExperienceLevel.BEGINNER;
        this.stats = new PlayerStats();
        this.currentDirection = Direction.DOWN;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void setStats(PlayerStats stats) {

        this.stats = stats;
    }
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
