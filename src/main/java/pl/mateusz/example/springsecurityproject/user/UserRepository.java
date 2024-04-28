package pl.mateusz.example.springsecurityproject.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUserName(String userName);

    Optional<User> getUserByEmail(String email);

    List<User> findAllByOrderByUserNameDesc();


}
