package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bets")
public class Bet {
//    Id, Bet Money, Date and Time of Bet, User

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "bet_money", nullable = false)
    private BigDecimal betMoney;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private User user;

    public Bet() {}

    public long getId() {
        return id;
    }

    public BigDecimal getBetMoney() {
        return betMoney;
    }

    public Bet setBetMoney(BigDecimal betMoney) {
        this.betMoney = betMoney;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Bet setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Bet setUser(User user) {
        this.user = user;
        return this;
    }
}
