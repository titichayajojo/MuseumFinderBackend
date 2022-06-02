package com.springboot.thymeleaf.controller;


import com.springboot.controller.MuseumController;
import com.springboot.controller.UserController;
import com.springboot.entity.Museum;
import com.springboot.entity.Role;
import com.springboot.entity.User;
import com.springboot.exception.ResourceNotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class HomePageControllerUI {

    @Autowired
    UserController userController;
    @Autowired
    MuseumController museumController;

    @Autowired
    TopNavigationBarControllerUI topNavigationBarControllerUI;

    @RequestMapping("/")
    public String HomePage(HttpServletRequest request, Model model, @RequestParam(name = "keyword", required = false) String keyword){

        List<Museum> museums = userController.getMuseumsFromCurrentUser();
        model.addAttribute("museums", museums);

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "homePage.html";
    }

    @RequestMapping(value = "/museums/{tag}", method = GET)
    public String HomePageByTag(HttpServletRequest request, Model model, @PathVariable("tag") String tag) throws ResourceNotFoundException {

        List<Museum> museums = (List<Museum>) museumController.getMuseumsByTag(tag).getBody();
        model.addAttribute("museums", museums);
        model.addAttribute("tag", tag);

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "homePage.html";
    }

    @RequestMapping(value = "/museums")
    public String HomePageByKeyword(HttpServletRequest request, Model model, @RequestParam(name = "keyword", required = false) String keyword){
        List<Museum> museums = (List<Museum>) museumController.searchByKeyword(keyword).getBody();

        model.addAttribute("museums", museums);

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "homePage.html";
    }

    public String MuseumRow(HttpServletRequest request, Model model){
        List<Museum> museums = userController.getMuseumsFromCurrentUser();
        model.addAttribute("museums", museums);

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "museumRow.html";
    }



}
