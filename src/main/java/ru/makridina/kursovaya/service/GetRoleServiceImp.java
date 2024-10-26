package ru.makridina.kursovaya.service;

import org.springframework.stereotype.Service;
import ru.makridina.kursovaya.entity.User;

@Service
public class GetRoleServiceImp implements GetRoleService{
    private final GetUsernameService getUsernameService;
    private final UserService userService;
    public GetRoleServiceImp(GetUsernameService getUsernameService, UserService userService)
    {
        this.getUsernameService = getUsernameService;
        this.userService = userService;
    }
    @Override
    public String getRoleCurrentUser() {
        User user = userService.findUserByEmail(getUsernameService.getusername());
        return user.getRoles().get(0).getName();
    }
}
