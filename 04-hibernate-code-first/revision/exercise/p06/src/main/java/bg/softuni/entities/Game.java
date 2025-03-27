package bg.softuni.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class Game {
//    Games – Id, Home Team, Away Team, Home Goals, Away Goals, Date and Time of Game,
//    Home team Winbet rate, Away Team Win Bet Rate, Draw Game Bet Rate, Round, Competition

//    TODO:
    private long id;

    private Team homeTeam;

    private Team awayTeam;

    private byte homeTeamGoals;

    private byte awayTeamGoals;

    private LocalDateTime dateTime;

    private BigDecimal homeTeamWinBetRate;

    private BigDecimal awayTeamWinBetRate;

    private BigDecimal drawGameBetRate;

    private Round round;

    private Competition competition;
}
