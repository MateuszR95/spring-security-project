package pl.mateusz.example.springsecurityproject.user;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class LoginController {

    @GetMapping("/login")
    String displayLoginForm(@RequestParam(required = false) String error, Model model) {
        boolean showErrorMessage = error != null;
        model.addAttribute("error", showErrorMessage);
        return "login-form";
    }

    @PostMapping("/login")
    String login() {
        return "redirect:/user-profile";
    }

    @GetMapping("/user-profile")
    String displayUserProfile(Authentication authentication, Model model) {
//        authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).anyMatch(Role.ADMIN.name())
        model.addAttribute("username", authentication.getName());
        return "user-profile";
    }


}
