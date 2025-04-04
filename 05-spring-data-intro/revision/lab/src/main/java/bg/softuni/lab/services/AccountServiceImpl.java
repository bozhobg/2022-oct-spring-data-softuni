package bg.softuni.lab.services;

import bg.softuni.lab.model.Account;
import bg.softuni.lab.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) {
        Account account = getAccount(id);
        validateAmount(amount);

        BigDecimal currentBalance = account.getBalance();

        if (currentBalance.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds!");

        account.setBalance(currentBalance.subtract(amount));
        this.accountRepository.save(account);
    }

    @Override
    public void transferMoney(BigDecimal amount, Long id) {
        Account account = getAccount(id);
        validateAmount(amount);
        BigDecimal updatedBalance = account.getBalance().add(amount);
        this.accountRepository.save(account.setBalance(updatedBalance));
    }

    private Account getAccount(Long id) {
        Optional<Account> optAccount = this.accountRepository.findAccountById(id);

        if (optAccount.isEmpty()) {
            throw new IllegalArgumentException(String.format("Account with id: %d doesn't exist"));
        }

        return optAccount.get();
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount negative or zero!");
    }
}
