package pl.mateusz.example.springsecurityproject.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mateusz.example.springsecurityproject.user.UserDisplayDto;
import pl.mateusz.example.springsecurityproject.user.UserService;

import java.util.List;

@Controller
class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    String adminPanel(Model model) {
        List<UserDisplayDto> users = userService.getUsersList();
        model.addAttribute("users", users);
        return "admin";
    }
}
