package bg.softuni.lab;

import bg.softuni.lab.model.Account;
import bg.softuni.lab.model.User;
import bg.softuni.lab.services.AccountService;
import bg.softuni.lab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {

        User newUser = new User().setUsername("user4").setAge(37);
        newUser.getAccounts().add(new Account().setUser(newUser).setBalance(BigDecimal.valueOf(100)));
        newUser = this.userService.registerUser(newUser);

        Account account = newUser.getAccounts()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No account for user!"));

        this.accountService.withdrawMoney(BigDecimal.valueOf(100), account.getId());
        this.accountService.transferMoney(BigDecimal.valueOf(100), account.getId());
    }
}
