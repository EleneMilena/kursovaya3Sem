package ru.makridina.kursovaya.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.makridina.kursovaya.dto.UserDto;
import ru.makridina.kursovaya.entity.User;
import ru.makridina.kursovaya.repository.UserRepository;
import ru.makridina.kursovaya.service.GetRoleService;
import ru.makridina.kursovaya.service.UserActionService;
import ru.makridina.kursovaya.service.UserService;
import ru.makridina.kursovaya.service.GetUsernameService;

import java.util.*;

@Controller
public class SecurityController {
    private UserService userService;
    private UserRepository userRepository;
    private GetUsernameService getUsernameService;
    private GetRoleService getRoleService;
    private UserActionService userActionService;

    public SecurityController(UserService userService,
                              UserRepository userRepository,
                              GetUsernameService getUsernameService,
                              GetRoleService getRoleService,
                              UserActionService userActionService) {
        this.getUsernameService = getUsernameService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.getRoleService = getRoleService;
        this.userActionService = userActionService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about-form";
    }

    @GetMapping("/login")
    public String login() {
        if (userRepository.findByEmail("e-valieva@mail.ru") == null) {
            setAdminAccount();
        }
        return "login";
    }
    private void setAdminAccount()
    {
        UserDto userDto = new UserDto();
        userDto.setRole("ROLE_ADMIN");
        userDto.setLastName("Elena");
        userDto.setFirstName("Makridin");
        userDto.setEmail("e-valieva@mail.ru");
        userDto.setPassword("1");
        userService.saveUser(userDto);
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null &&
                existingUser.getEmail() != null &&
                !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "На этот адрес электронной почты уже зарегистрирована учетная запись");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";

        }
        userDto.setRole("ROLE_READONLY");
        userService.saveUser(userDto);
        return "redirect:/login";

    }

    @GetMapping("/users")
    public String users(Model model) {
        User user = userService.findUserByEmail(getUsernameService.getusername());
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("mainUserRole", getRoleService.getRoleCurrentUser());
        model.addAttribute("users", users);
        userActionService.setUserAction("Просмотр пользователей");
        return "users";
    }
    @PostMapping("/saveRole")
    public String saveRole(@ModelAttribute UserDto userDto)
    {
        String r = userDto.getRole();
        userService.saveUserRole(userDto);
        userActionService.setUserAction("Сохранение роли");
        return "redirect:/users";
    }
    @GetMapping("/addNewRole")
    public ModelAndView addNewRole(@RequestParam String userEmail)//@RequestParam String userEmail
    {
        ModelAndView mav = new ModelAndView("add-new-role");
        UserDto user = userService.mapToUserDto(userService.findUserByEmail(userEmail));
        mav.addObject("user", user);
        userActionService.setUserAction("Редактирование роли");
        return mav;
    }
}