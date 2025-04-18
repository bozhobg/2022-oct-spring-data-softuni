package bg.softuni.exercisemore.service.impl;

import bg.softuni.exercisemore.constants.ExceptionMessageTemplates;
import bg.softuni.exercisemore.entities.User;
import bg.softuni.exercisemore.exception.EntityNotFoundForIdException;
import bg.softuni.exercisemore.repository.UserRepository;
import bg.softuni.exercisemore.service.TownService;
import bg.softuni.exercisemore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static bg.softuni.exercisemore.util.GenerationUtil.*;

@Service
public class UserServiceImpl implements UserService {

    private final static int INIT_COUNT = 10;
    private final static int FRIENDS_UPPER_BOUND = 5;
    private final static String TEMPLATE = "user";

    private final UserRepository userRepository;

    private final TownService townService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TownService townService) {
        this.userRepository = userRepository;
        this.townService = townService;
    }

    @Override
    public User register(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void seedUsers() {
        for (int i = 1; i <= INIT_COUNT; i++) {

            char c = SPECIAL_CHARS.charAt(RANDOM.nextInt(SPECIAL_CHARS.length()));
            String base = TEMPLATE + i;

            long minusMonths = RANDOM.nextLong(1, 12);
            LocalDateTime registeredOn = LocalDateTime.now().minusMonths(minusMonths);
            long until = registeredOn.until(LocalDateTime.now(), ChronoUnit.DAYS);
            LocalDateTime lastLogin = registeredOn.plus(RANDOM.nextLong(until), ChronoUnit.DAYS);

            this.userRepository.save(
                    new User(
                            base,
                            "User" + i + c,
                            base + "@users.com",
                            base + "FirstName",
                            base + "LastName",
                            registeredOn,
                            lastLogin,
                            i,
                            RANDOM.nextBoolean(),
                            townService.getRandom(),
                            townService.getRandom()
                    ));
        }
    }

    @Transactional
    @Override
    public void seedFriends() {

        for (User user : this.userRepository.findAll()) {

            int friendsCount = RANDOM.nextInt(FRIENDS_UPPER_BOUND);

            for (int i = 0; i < friendsCount; i++) {

                User friend = getRandom();
                user.getFriends().add(friend);
                friend.getFriends().add(user);

                this.userRepository.saveAll(List.of(user, friend));
            }

        }
    }

    @Override
    public User getRandom() {
        int index = RANDOM.nextInt((int) this.userRepository.count()) + 1;

        return this.userRepository.findById(index)
                .orElseThrow(() -> new EntityNotFoundForIdException(
                        String.format(ExceptionMessageTemplates.USER_NOT_FOUND_FOR_ID, index)
                ));
    }

    @Override
    public List<User> getUsersByEmailProvider(String emailProvider) {

        if (emailProvider == null || !emailProvider.matches("^(([A-Za-z]+\\-?)+[^\\-]\\.)+[A-Za-z]+$")
        ) {
            throw new IllegalArgumentException(ExceptionMessageTemplates.EMAIL_PROVIDER_FORMAT);
        }

        return this.userRepository.findUsersByEmailEndingWith(emailProvider);
    }

    @Override
    public void deleteUsersBefore(LocalDateTime before) {
        List<User> users = this.userRepository.getAllByLastTimeLoggedInBefore(before);

        clearFriend(users);

        System.out.println(users.size());

//         pictures' parent albums owned by same user to cascade remove user,
//         logically albums owned by user should contain pictures owned by user?! (no direct relation users-pictures, only users-albums)
//         redo pictures and albums init to satisfy condition (not having same pictures as children to albums owned by different users)
        this.userRepository.deleteAll(users);
    }

    private void clearFriend(List<User> users) {
        for (User user : users) {

            for (User friend : user.getFriends()) {
                friend.getFriends().remove(user);
                this.userRepository.save(friend);
            }

            user.getFriends().clear();
            this.userRepository.save(user);
            this.userRepository.flush();
        }
    }

}
