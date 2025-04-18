package bg.softuni.exercisemore.entities;

import bg.softuni.exercisemore.constraint.Password;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 30)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Password(
            minLength = 6,
            maxLength = 50,
            containsLowerCase = true,
            containsUpperCase = true,
            containsDigit = true,
            containsSpecialSymbol = true
    )
    @Column(nullable = false)
    private String password;

//    info@info.info given as invalid example?
    @Email(regexp = "^([A-Za-z0-9]+[.\\-_]?)+[^.\\-_]@(([A-Za-z]+\\-?)+[^\\-]\\.)+[A-Za-z]+")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Transient
    private String fullName;

    @NotNull
    @PastOrPresent
    @Column(name = "registered_on", nullable = false)
    private LocalDateTime registeredOn;

    @NotNull
    @PastOrPresent
    @Column(name = "last_time_logged_in", nullable = false)
    private LocalDateTime lastTimeLoggedIn;

    @NotNull
    @Min(1)
    @Max(120)
    @Column(nullable = false)
    private Integer age;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "town_living_in")
    private Town townLivingIn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "town_born_in")
    private Town townBornIn;

    @ManyToMany
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id")
    )
    private Set<User> friends;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<Album> albums;

    public User() {
        this.friends = new HashSet<>();
    }

    public User(
            String username,
            String password,
            String email,
            String firstName,
            String lastName,
            LocalDateTime registeredOn,
            LocalDateTime lastTimeLoggedIn,
            Integer age,
            boolean isDeleted,
            Town townLivingIn,
            Town townBornIn
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredOn = registeredOn;
        this.lastTimeLoggedIn = lastTimeLoggedIn;
        this.age = age;
        this.isDeleted = isDeleted;
        this.townLivingIn = townLivingIn;
        this.townBornIn = townBornIn;

        this.friends = new HashSet<>();
        setFullName();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        setFullName();
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        setFullName();
        return this;
    }

    public String getFullName() {
        setFullName();
        return fullName;
    }

    private void setFullName() {
        if (firstName == null && lastName == null) this.fullName = "";
        else if (firstName == null) this.fullName = lastName;
        else if (lastName == null) this.fullName = firstName;
        else this.fullName = firstName + " " + lastName;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public User setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
        return this;
    }

    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public User setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public User setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public Town getTownLivingIn() {
        return townLivingIn;
    }

    public User setTownLivingIn(Town townLivingIn) {
        this.townLivingIn = townLivingIn;
        return this;
    }

    public Town getTownBornIn() {
        return townBornIn;
    }

    public User setTownBornIn(Town townBornIn) {
        this.townBornIn = townBornIn;
        return this;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", registeredOn=" + registeredOn +
                ", lastTimeLoggedIn=" + lastTimeLoggedIn +
                ", age=" + age +
                ", isDeleted=" + isDeleted +
                ", townLivingIn=" + townLivingIn.toString() +
                ", townBornIn=" + townBornIn.toString() +
                '}';
    }
}
