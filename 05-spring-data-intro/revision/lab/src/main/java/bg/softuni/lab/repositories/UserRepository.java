package bg.softuni.lab.repositories;

import bg.softuni.lab.model.Account;
import bg.softuni.lab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsById(Long id);
}
