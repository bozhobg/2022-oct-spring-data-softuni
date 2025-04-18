package bg.softuni.exercisemore;

import bg.softuni.exercisemore.entities.Country;
import bg.softuni.exercisemore.entities.Town;
import bg.softuni.exercisemore.entities.User;
import bg.softuni.exercisemore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final TownService townService;
    private final CountryService countryService;
    private final AlbumService albumService;
    private final PictureService pictureService;

    @Autowired
    public ConsoleRunner(
            UserService userService,
            TownService townService,
            CountryService countryService,
            AlbumService albumService,
            PictureService pictureService
    ) {
        this.userService = userService;
        this.townService = townService;
        this.countryService = countryService;
        this.albumService = albumService;
        this.pictureService = pictureService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.countryService.seed();
        this.townService.seed();
        this.userService.seed();
        this.albumService.seed();
        this.pictureService.seed();

//        persistUser();
//        getUsersByEmailProvider();
        removeInactiveUsersBefore(LocalDateTime.now().minus(6, ChronoUnit.MONTHS));

    }

    private void persistUser() {
        Country testCountry1 = this.countryService.persist(new Country().setName("Bulgaria"));
        Country testCountry2 = this.countryService.persist(new Country().setName("France"));

        Town testTown1 = this.townService.persist(
                new Town().setName("Varna")
                        .setCountry(testCountry1));
        Town testTown2 = this.townService.persist(new Town().setName("Paris")
                .setCountry(testCountry2));

        User testUser = new User(
                "bozho",
//                "bozho",
                "Boz1@G",
//                ".boz_@b.s.",
                "bozho-bg@mail.anywhere.c",
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                1,
                false,
                testTown1,
                testTown2
        );

        User userEntity = this.userService.register(testUser);
        System.out.println(userEntity);
    }

    private void removeInactiveUsersBefore(LocalDateTime before) {
        this.userService.deleteUsersBefore(before);
    }

    private void getUsersByEmailProvider() {
        Scanner sc = new Scanner(System.in);
        String emailDomain = sc.nextLine();

        List<User> users = this.userService.getUsersByEmailProvider(emailDomain);
        if (users.isEmpty()) System.out.println("No users found with email domain " + emailDomain);
        else users.stream().map(u -> u.getUsername() + " " + u.getEmail()).forEach(System.out::println);
    }
}
