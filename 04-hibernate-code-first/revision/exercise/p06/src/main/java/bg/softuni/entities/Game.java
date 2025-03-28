package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class Game {
//    Games – Id, Home Team, Away Team, Home Goals, Away Goals, Date and Time of Game,
//    Home team Winbet rate, Away Team Win Bet Rate, Draw Game Bet Rate, Round, Competition

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @Column(name = "home_team_goals")
    private byte homeTeamGoals;

    @Column(name = "away_team_goals")
    private byte awayTeamGoals;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "home_team_win_rate")
    private BigDecimal homeTeamWinBetRate;

    @Column(name = "away_team_win_rate")
    private BigDecimal awayTeamWinBetRate;

    @Column(name = "draw_game_bet_rate")
    private BigDecimal drawGameBetRate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Round round;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Competition competition;

    public Game() {}

    public long getId() {
        return id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Game setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
        return this;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Game setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
        return this;
    }

    public byte getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public Game setHomeTeamGoals(byte homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
        return this;
    }

    public byte getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public Game setAwayTeamGoals(byte awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Game setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public BigDecimal getHomeTeamWinBetRate() {
        return homeTeamWinBetRate;
    }

    public Game setHomeTeamWinBetRate(BigDecimal homeTeamWinBetRate) {
        this.homeTeamWinBetRate = homeTeamWinBetRate;
        return this;
    }

    public BigDecimal getAwayTeamWinBetRate() {
        return awayTeamWinBetRate;
    }

    public Game setAwayTeamWinBetRate(BigDecimal awayTeamWinBetRate) {
        this.awayTeamWinBetRate = awayTeamWinBetRate;
        return this;
    }

    public BigDecimal getDrawGameBetRate() {
        return drawGameBetRate;
    }

    public Game setDrawGameBetRate(BigDecimal drawGameBetRate) {
        this.drawGameBetRate = drawGameBetRate;
        return this;
    }

    public Round getRound() {
        return round;
    }

    public Game setRound(Round round) {
        this.round = round;
        return this;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Game setCompetition(Competition competition) {
        this.competition = competition;
        return this;
    }
}
