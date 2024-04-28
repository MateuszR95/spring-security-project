package pl.mateusz.example.springsecurityproject.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mateusz.example.springsecurityproject.exceptions.UserNotFoundException;
import pl.mateusz.example.springsecurityproject.exceptions.UserWithEmptyFirstNameException;
import pl.mateusz.example.springsecurityproject.exceptions.UserWithEmptyLastNameException;


@Controller
class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-profile/{username}/edit")
    public String displayEditForm(@PathVariable String username, Model model) {
        UserEditDto userEditDto = userService.getUserEditDtoByUsername(username).orElseThrow(() -> new UserNotFoundException("Brak użytkownika"));
        model.addAttribute("user", userEditDto);
        return "edit-form";
    }

    @PostMapping("/user-profile/{username}/edit")
    public String editAccount(@PathVariable String username, UserEditDto userEditDto, Model model) {
        try {
            userService.editAccount(username, userEditDto);
        } catch (UserWithEmptyFirstNameException | UserWithEmptyLastNameException e) {
            logger.info(e.getMessage());
            model.addAttribute("error", e.getMessage());
            UserEditDto user = userService.getUserEditDtoByUsername(username).orElseThrow(() -> new UserNotFoundException("Brak użytkownika"));
            model.addAttribute("user", user);
            return "edit-form";
        }
        return "redirect:/edit-success";
    }

    @GetMapping("/edit-success")
    public String editSuccessPage() {
        return "edit-success";
    }

    @GetMapping("/promote/{username}")
    public String promotionsForm(@PathVariable String username, Model model) {
        UserDisplayDto userToPromote = userService.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException("Nie znaleziono użytkownika"));
        model.addAttribute("user", userToPromote);
        return "promote";
    }

    @GetMapping("/dismiss/{username}")
    public String dismissForm(@PathVariable String username, Model model) {
        UserDisplayDto userToDismiss = userService.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException("Nie znaleziono użytkownika"));
        model.addAttribute("user", userToDismiss);
        return "dismiss";
    }

    @PostMapping("/promote/{username}")
    public String promoteUser(@PathVariable String username, UserDisplayDto userDisplayDto, Model model,
                              @RequestParam(value = "adminRole", required = false) String adminRole) {
        if ("true".equals(adminRole)) {
            userService.promoteUser(username, userDisplayDto);
        } else {
            UserDisplayDto userToPromote = userService.getUserByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("Nie znaleziono użytkownika"));
            model.addAttribute("user", userToPromote);
        }
        return "redirect:/admin";
    }

    @PostMapping("/dismiss/{username}")
    public String dismissUser(@PathVariable String username, UserDisplayDto userDisplayDto, Model model,
                              @RequestParam(value = "adminRole", required = false) String adminRole) {
        if ("true".equals(adminRole)) {
            userService.dismissUser(username, userDisplayDto);
        } else {
            UserDisplayDto userToPromote = userService.getUserByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("Nie znaleziono użytkownika"));
            model.addAttribute("user", userToPromote);
        }
        return "redirect:/admin";
    }
}
