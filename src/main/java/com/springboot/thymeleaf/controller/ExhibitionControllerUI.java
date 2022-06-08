package com.springboot.thymeleaf.controller;

import com.springboot.controller.ExhibitionController;
import com.springboot.entity.Exhibition;
import com.springboot.entity.Museum;
import com.springboot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
public class ExhibitionControllerUI {

    @Autowired
    ExhibitionController exhibitionController;

    @Autowired
    TopNavigationBarControllerUI topNavigationBarControllerUI;

    @RequestMapping(value = "/exhibition/{id}", method = GET)
    public String ExhibitionByIdPage(HttpServletRequest request, Model model, @PathVariable("id") long id) throws ResourceNotFoundException {

        Exhibition exhibition = (Exhibition) exhibitionController.getExhibitionById(id).getBody();
        model.addAttribute("exhibition", exhibition);

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "exhibition.html";
    }
}
