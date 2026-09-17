package it.aulab.progetto_blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthController {

    //restituisce il tamplate login
    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

}
