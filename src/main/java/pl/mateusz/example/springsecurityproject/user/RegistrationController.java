package pl.mateusz.example.springsecurityproject.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.mateusz.example.springsecurityproject.exceptions.UserWithProvidedEmailExist;
import pl.mateusz.example.springsecurityproject.exceptions.UserWithProvidedUsernameExist;

@Controller
class RegistrationController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String displayRegisterForm(Model model) {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        model.addAttribute("user", userRegisterDto);
        return "register-form";
    }

    @PostMapping("/register")
    String register(UserRegisterDto userRegisterDto, Model model) {
        try {
            userService.register(userRegisterDto);
        } catch (UserWithProvidedEmailExist | UserWithProvidedUsernameExist e) {
            logger.info(e.getMessage());
            model.addAttribute("user", userRegisterDto);
            model.addAttribute("error", e.getMessage());
            return "register-form";
        }
        model.addAttribute("message", "Pomyślnie utworzono konto!");
        return "login-form";
    }
}
