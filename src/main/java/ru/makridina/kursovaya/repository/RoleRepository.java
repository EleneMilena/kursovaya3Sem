package ru.makridina.kursovaya.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.makridina.kursovaya.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}