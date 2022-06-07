package com.springboot.thymeleaf.controller;

import com.springboot.controller.UserController;
import com.springboot.entity.Role;
import com.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
@Controller
public class TopNavigationBarControllerUI {

    @Autowired
    UserController userController;

    public void topNavigationBarController(HttpServletRequest request, Model model){
        try {
            User user = userController.getCurrentLoggedInUserProfile();
            model.addAttribute("user", user);
            String role = "";
            for(Role r: user.getRoles()){
                role = r.getName();
            }
            model.addAttribute("role",role);
        }catch (Exception e){}
    }
}