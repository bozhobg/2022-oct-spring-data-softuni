package bg.softuni.entities.composite_pks;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BetGameEmbeddedPK implements Serializable {

    @Column(name = "bet_id")
    private long betId;

    @Column(name = "game_id")
    private long gameId;

    public BetGameEmbeddedPK() {}

    public long getBetId() {
        return betId;
    }

    public BetGameEmbeddedPK setBetId(long betId) {
        this.betId = betId;
        return this;
    }

    public long getGameId() {
        return gameId;
    }

    public BetGameEmbeddedPK setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }
}
