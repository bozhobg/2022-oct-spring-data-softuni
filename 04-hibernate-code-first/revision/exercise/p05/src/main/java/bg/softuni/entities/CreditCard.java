package bg.softuni.entities;

import jakarta.persistence.*;

import java.time.Year;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends BillingDetail {
//    card type, expiration month, expiration year

    @Column(name = "card_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

//    TODO: ways to store month data to be directly usable by the DB (this relies on conversions)
    @Column(name = "expiration_month", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private MonthEnum expirationMonth;

    @Column(name = "expiration_year", columnDefinition = "YEAR", nullable = false)
    private Year expirationYear;

    public CreditCard() {
        super();
    }

    public CardType getCardType() {
        return cardType;
    }

    public CreditCard setCardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public MonthEnum getExpirationMonth() {
        return expirationMonth;
    }

    public CreditCard setExpirationMonth(MonthEnum expirationMonth) {
        this.expirationMonth = expirationMonth;
        return this;
    }

    public Year getExpirationYear() {
        return expirationYear;
    }

    public CreditCard setExpirationYear(Year expirationYear) {
        this.expirationYear = expirationYear;
        return this;
    }
}
