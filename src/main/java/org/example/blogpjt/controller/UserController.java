package org.example.blogpjt.controller;

import lombok.RequiredArgsConstructor;
import org.example.blogpjt.domain.User;
import org.example.blogpjt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/userregform")
    public String userregform(Model model){
        model.addAttribute("user", new User());
        return "users/userregform";
    }

    @PostMapping("/userreg")
    public String userreg(@ModelAttribute("user") User user, BindingResult result){
        if(result.hasErrors()){
            return "userregform";
        }
        User byUsername = userService.findByUsername(user.getUsername());
        if(byUsername != null){
            result.rejectValue("username",null,"이미 사용중인 아이디입니다.");
            return "users/userregerror";
        }

        userService.registUser(user);
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "users/welcome";
    }

    @GetMapping("/loginform")
    public String loginform(){
        return "users/loginform";
    }
    @GetMapping("/")
    public String home(){
        return "home";
    }

}
