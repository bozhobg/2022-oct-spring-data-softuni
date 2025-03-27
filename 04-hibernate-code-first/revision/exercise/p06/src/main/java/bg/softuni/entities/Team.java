package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "teams")
public class Team {
//    Id, Name, Logo, 3 letter Initials (JUV, LIV, ARS…), Primary Kit Color, Secondary Kit Color, Town, Budget

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1_000_000)
    private byte[] logo;

    @Column(columnDefinition = "CHAR(3)", length = 3, nullable = false, unique = true)
    private char[] initials;

    @ManyToOne(optional = false)
    @JoinColumn(name = "primary_kit_color_id")
    private Color primaryKitColor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "secondary_kit_color")
    private Color secondaryKitColor;

    @ManyToOne(optional = false)
    private Town town;

    @Basic
    private BigDecimal budget;

    public Team() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Team setName(String name) {
        this.name = name;
        return this;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Team setLogo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public char[] getInitials() {
        return initials;
    }

    public Team setInitials(char[] initials) {
        this.initials = initials;
        return this;
    }

    public Color getPrimaryKitColor() {
        return primaryKitColor;
    }

    public Team setPrimaryKitColor(Color primaryKitColor) {
        this.primaryKitColor = primaryKitColor;
        return this;
    }

    public Color getSecondaryKitColor() {
        return secondaryKitColor;
    }

    public Team setSecondaryKitColor(Color secondaryKitColor) {
        this.secondaryKitColor = secondaryKitColor;
        return this;
    }

    public Town getTown() {
        return town;
    }

    public Team setTown(Town town) {
        this.town = town;
        return this;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public Team setBudget(BigDecimal budget) {
        this.budget = budget;
        return this;
    }
}
