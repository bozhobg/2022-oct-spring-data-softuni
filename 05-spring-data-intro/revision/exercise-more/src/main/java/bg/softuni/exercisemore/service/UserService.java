package bg.softuni.exercisemore.service;

import bg.softuni.exercisemore.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    User register(User user);

    @Transactional
    default void seed() {
        seedUsers();
        seedFriends();
    };

    void seedUsers();

    void seedFriends();

    User getRandom();

    List<User> getUsersByEmailProvider(String emailDomain);

    @Transactional
    void deleteUsersBefore(LocalDateTime before);

}
