package ru.makridina.kursovaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.makridina.kursovaya.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}