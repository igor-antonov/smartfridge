package ru.antonov.smartfridge.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.antonov.smartfridge.domain.User;
import ru.antonov.smartfridge.repository.UserRepository;

@Controller
public class AuthorizationController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String main(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String j_login,@RequestParam String fullName,
                               @RequestParam String email, @RequestParam String j_password){
        userRepository.save(new User(j_login, fullName, email, passwordEncoder.encode(j_password)));
        return "loginPage";
    }
}