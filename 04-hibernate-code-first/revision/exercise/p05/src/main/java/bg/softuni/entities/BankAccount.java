package bg.softuni.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount extends BillingDetail {
//    bank name and SWIFT code

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "swift_code", nullable = false, unique = true)
    private String swiftCode;

    public BankAccount() {
    }

    public String getBankName() {
        return bankName;
    }

    public BankAccount setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public BankAccount setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
        return this;
    }
}
