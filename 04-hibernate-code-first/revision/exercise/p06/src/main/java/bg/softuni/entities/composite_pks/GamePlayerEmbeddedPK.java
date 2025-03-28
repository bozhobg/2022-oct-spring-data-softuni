package bg.softuni.entities.composite_pks;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GamePlayerEmbeddedPK implements Serializable {

    @Column(name = "game_id")
    private long gameId;

    @Column(name = "game_id")
    private long playerId;

//    required
    public GamePlayerEmbeddedPK() {}

    public long getGameId() {
        return gameId;
    }

    public GamePlayerEmbeddedPK setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }

    public long getPlayerId() {
        return playerId;
    }

    public GamePlayerEmbeddedPK setPlayerId(long playerId) {
        this.playerId = playerId;
        return this;
    }

    @Override // required
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        GamePlayerEmbeddedPK that = (GamePlayerEmbeddedPK) object;
        return gameId == that.gameId && playerId == that.playerId;
    }

    @Override // required
    public int hashCode() {
        return Objects.hash(gameId, playerId);
    }
}
