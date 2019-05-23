package ru.antonov.smartfridge.repository;

import org.springframework.data.repository.CrudRepository;
import ru.antonov.smartfridge.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
