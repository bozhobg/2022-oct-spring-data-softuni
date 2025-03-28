package bg.softuni.entities;


import bg.softuni.entities.composite_pks.BetGameEmbeddedPK;
import jakarta.persistence.*;

@Entity
@Table(name = "bet_games")
public class BetGame {
//    Game, Bet, Result Prediction (PK = Game + Bet)

    @EmbeddedId
    private BetGameEmbeddedPK pk;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("gameId")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("betId")
    private Bet bet;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ResultPrediction resultPrediction;

    public BetGame() {}

    public BetGameEmbeddedPK getPk() {
        return pk;
    }

    public BetGame setPk(BetGameEmbeddedPK pk) {
        this.pk = pk;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public BetGame setGame(Game game) {
        this.game = game;
        return this;
    }

    public Bet getBet() {
        return bet;
    }

    public BetGame setBet(Bet bet) {
        this.bet = bet;
        return this;
    }

    public ResultPrediction getResultPrediction() {
        return resultPrediction;
    }

    public BetGame setResultPrediction(ResultPrediction resultPrediction) {
        this.resultPrediction = resultPrediction;
        return this;
    }
}
