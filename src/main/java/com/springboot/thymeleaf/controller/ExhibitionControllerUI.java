package com.springboot.thymeleaf.controller;

import com.springboot.controller.ExhibitionController;
import com.springboot.controller.MuseumController;
import com.springboot.entity.Exhibition;
import com.springboot.entity.Museum;
import com.springboot.entity.Tag;
import com.springboot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class ExhibitionControllerUI {

    @Autowired
    ExhibitionController exhibitionController;

    @Autowired
    MuseumController museumController;

    @Autowired
    TopNavigationBarControllerUI topNavigationBarControllerUI;

    @RequestMapping(value = "/exhibition/{id}", method = GET)
    public String ExhibitionByIdPage(HttpServletRequest request, Model model, @PathVariable("id") long id) throws ResourceNotFoundException {

        Exhibition exhibition = (Exhibition) exhibitionController.getExhibitionById(id).getBody();
        model.addAttribute("exhibition", exhibition);

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "exhibition.html";
    }

    @RequestMapping(value = "/exhibition-add", method = GET)
    public String CreateMuseumPage(HttpServletRequest request, Model model){
        List<Museum> museums = museumController.getAllMuseums();
        model.addAttribute("exhibition", new Exhibition());
        model.addAttribute("museums", museums);

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "createExhibition.html";
    }

    @RequestMapping(value = "/exhibition-add/created", method = POST)
    public String CreateMuseumMethod(HttpServletRequest request, Model model,Exhibition exhibitionDetails ,@RequestParam("image") MultipartFile multipartFile) throws IOException {
        Exhibition exhibition = (Exhibition) exhibitionController.createExhibition(exhibitionDetails).getBody();
        exhibitionController.addImage(exhibition.getId(),multipartFile);

        model.addAttribute("exhibition", exhibition);

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "redirect:/exhibition/" + exhibition.getId();
    }
}
