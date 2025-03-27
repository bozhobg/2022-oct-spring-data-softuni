package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wizard_deposits")
public class WizardDeposit {

//    TODO: numbers ranges [1,n] -> as SIGNED ?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "magic_wand_creator", length = 100)
    private String magicWandCreator;

    @Column(name = "magic_wand_size")
    private Short magicWandSize;

    @Column(name = "deposit_group", length = 20)
    private String depositGroup;

    @Column(name = "deposit_start_date")
    private LocalDateTime depositStartDate;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "deposit_interest")
    private BigDecimal depositInterest;

    @Column(name = "deposit_charge")
    private BigDecimal depositCharge;

    @Column(name = "deposit_expiration_date")
    private LocalDateTime depositExpirationDate;

    @Basic
    private boolean isDepositExpired;

    public WizardDeposit() {
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public WizardDeposit setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public WizardDeposit setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public WizardDeposit setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public WizardDeposit setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getMagicWandCreator() {
        return magicWandCreator;
    }

    public WizardDeposit setMagicWandCreator(String magicWandCreator) {
        this.magicWandCreator = magicWandCreator;
        return this;
    }

    public Short getMagicWandSize() {
        return magicWandSize;
    }

    public WizardDeposit setMagicWandSize(Short magicWandSize) {
        this.magicWandSize = magicWandSize;
        return this;
    }

    public String getDepositGroup() {
        return depositGroup;
    }

    public WizardDeposit setDepositGroup(String depositGroup) {
        this.depositGroup = depositGroup;
        return this;
    }

    public LocalDateTime getDepositStartDate() {
        return depositStartDate;
    }

    public WizardDeposit setDepositStartDate(LocalDateTime depositStartDate) {
        this.depositStartDate = depositStartDate;
        return this;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public WizardDeposit setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
        return this;
    }

    public BigDecimal getDepositInterest() {
        return depositInterest;
    }

    public WizardDeposit setDepositInterest(BigDecimal depositInterest) {
        this.depositInterest = depositInterest;
        return this;
    }

    public BigDecimal getDepositCharge() {
        return depositCharge;
    }

    public WizardDeposit setDepositCharge(BigDecimal depositCharge) {
        this.depositCharge = depositCharge;
        return this;
    }

    public LocalDateTime getDepositExpirationDate() {
        return depositExpirationDate;
    }

    public WizardDeposit setDepositExpirationDate(LocalDateTime depositExpirationDate) {
        this.depositExpirationDate = depositExpirationDate;
        return this;
    }

    public boolean isDepositExpired() {
        return isDepositExpired;
    }

    public WizardDeposit setDepositExpired(boolean depositExpired) {
        isDepositExpired = depositExpired;
        return this;
    }
}
