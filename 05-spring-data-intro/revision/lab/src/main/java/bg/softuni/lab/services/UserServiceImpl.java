package bg.softuni.lab.services;

import bg.softuni.lab.model.User;
import bg.softuni.lab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {

        if (user == null) throw new NullPointerException("User is null!");
        Long id = user.getId();

        if (id != null && this.userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with id: " + id + "exists!");
        }

        String username = user.getUsername();

        if (this.userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Entity with username: " + username + " exists!");
        }


        return this.userRepository.save(user);
    }
}
