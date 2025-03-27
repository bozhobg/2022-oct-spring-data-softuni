package bg.softuni.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "player_statistics")
public class PlayerStatistics {
//    PlayerStatistics – Game, Player, Scored Goals, Player Assists, Played Minutes During Game, (PK = Game +
//Player)

//    TODO: how to make composite primary key of Game + Player
//    TODO: @Embeddable (on class with id fields) @EmbeddedId (as primary field) OR @ClassId(on class with id fields) + @Id(on fields)

    @ManyToOne
    private Game game;

    @ManyToOne
    private Player player;

    @Column(name = "scored_golas")
    private byte scoredGoals;

    @Column(name = "player_assists")
    private byte playerAssists;

    @Column(name = "played_minutes")
    private short playedMinutes;

    public PlayerStatistics() {}

    public Game getGame() {
        return game;
    }

    public PlayerStatistics setGame(Game game) {
        this.game = game;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerStatistics setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public byte getScoredGoals() {
        return scoredGoals;
    }

    public PlayerStatistics setScoredGoals(byte scoredGoals) {
        this.scoredGoals = scoredGoals;
        return this;
    }

    public byte getPlayerAssists() {
        return playerAssists;
    }

    public PlayerStatistics setPlayerAssists(byte playerAssists) {
        this.playerAssists = playerAssists;
        return this;
    }

    public short getPlayedMinutes() {
        return playedMinutes;
    }

    public PlayerStatistics setPlayedMinutes(short playedMinutes) {
        this.playedMinutes = playedMinutes;
        return this;
    }
}
