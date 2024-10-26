package ru.makridina.kursovaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.makridina.kursovaya.entity.UserActions;
import ru.makridina.kursovaya.repository.UserActionsRepository;
import ru.makridina.kursovaya.util.DateTimeUtil;

import java.util.Date;

@Service
public class UserActionServiceImp implements UserActionService{
    private GetUsernameService getUsernameService;
    private UserActionsRepository userActionsRepository;
    @Autowired
    public void UserActionsServiceImp(GetUsernameService getUsernameService,
                                      UserActionsRepository userActionsRepository)
    {
        this.getUsernameService = getUsernameService;
        this.userActionsRepository = userActionsRepository;
    }
    @Override
    public void setUserAction(String userAction) {
        String date = DateTimeUtil.getCustomFormat().format(new Date());
        UserActions userActions = new UserActions();
        userActions.setDate_actions(date);
        userActions.setUser_email(getUsernameService.getusername());
        userActions.setDescription(userAction);
        userActionsRepository.save(userActions);
    }
}
