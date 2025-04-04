package bg.softuni.lab;

import bg.softuni.lab.model.Account;
import bg.softuni.lab.model.User;
import bg.softuni.lab.repositories.AccountRepository;
import bg.softuni.lab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DbInit implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public DbInit(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        if (accountRepository.count() != 0 && userRepository.count() != 0) {
            return;
        }

        for (int i = 1; i <= 3; i++) {
            User user = new User()
                    .setAge(i)
                    .setUsername("user" + i);
            Account account = new Account()
                    .setBalance(BigDecimal.valueOf(i).multiply(BigDecimal.valueOf(1500)))
                    .setUser(user);


            user.getAccounts().add(account);
            this.userRepository.save(user);
        }


    }
}
