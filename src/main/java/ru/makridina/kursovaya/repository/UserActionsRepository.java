package ru.makridina.kursovaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.makridina.kursovaya.entity.UserActions;

public interface UserActionsRepository extends JpaRepository<UserActions, Long> {

}
