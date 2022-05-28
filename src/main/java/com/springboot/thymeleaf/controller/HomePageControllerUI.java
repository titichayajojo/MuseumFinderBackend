package com.springboot.thymeleaf.controller;


import com.springboot.controller.MuseumController;
import com.springboot.controller.UserController;
import com.springboot.entity.Museum;
import com.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomePageControllerUI {

    @Autowired
    UserController userController;

    @RequestMapping("/")
    public String HomePage(HttpServletRequest request, Model model){

        List<Museum> museums = userController.getMuseumsFromCurrentUser();
        model.addAttribute("museums", museums);

        return "homePage.html";
    }

    public String MuseumRow(HttpServletRequest request, Model model){
        List<Museum> museums = userController.getMuseumsFromCurrentUser();
        model.addAttribute("museums", museums);

        return "museumRow.html";
    }

}
