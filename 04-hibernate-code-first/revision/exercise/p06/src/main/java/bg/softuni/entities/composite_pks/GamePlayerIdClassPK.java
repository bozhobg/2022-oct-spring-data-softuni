package bg.softuni.entities.composite_pks;

import bg.softuni.entities.Game;
import bg.softuni.entities.Player;

import java.io.Serializable;
import java.util.Objects;

public class GamePlayerIdClassPK implements Serializable {

    private Game game;
    private Player player;

    public GamePlayerIdClassPK() {}

    public Game getGame() {
        return game;
    }

    public GamePlayerIdClassPK setGame(Game game) {
        this.game = game;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public GamePlayerIdClassPK setPlayer(Player player) {
        this.player = player;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        GamePlayerIdClassPK that = (GamePlayerIdClassPK) object;

//        comparison by id
//        TODO: comparison by other entity's properties, not taking into consideration mapped objs changes,
//         or inconsistency of data (may lead to wrong data persisted)

        return this.game.getId() == that.game.getId() && this.player.getId() == that.player.getId();
//        return Objects.equals(game, that.game) && Objects.equals(player, that.player); // default
    }

    @Override
    public int hashCode() {
//        TODO: in case of equals() by ids -> change to generation by id of fields
        return Objects.hash(game, player);
    }
}
