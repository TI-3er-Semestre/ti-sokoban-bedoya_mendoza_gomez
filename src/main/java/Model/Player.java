package Model;

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

    public Player(String name, String email, String username,
                  String avatar, ExperienceLevel experienceLevel) {

        this.name = name;
        this.email = email;
        this.username = username;
        this.avatar = avatar;
        this.experienceLevel = experienceLevel;

        this.stats = new PlayerStats();
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
}
