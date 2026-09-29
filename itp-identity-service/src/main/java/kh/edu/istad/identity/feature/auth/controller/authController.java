package kh.edu.istad.identity.feature.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class authController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
