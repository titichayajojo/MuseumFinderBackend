package com.springboot.thymeleaf.controller;

import com.springboot.controller.MuseumController;
import com.springboot.controller.TagController;
import com.springboot.entity.Museum;
import com.springboot.entity.Tag;
import com.springboot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class MuseumControllerUI {

    @Autowired
    MuseumController museumController;

    @Autowired
    TagController tagController;

    @RequestMapping(value = "/museum/{id}", method = GET)
    public String MuseumByIdPage(HttpServletRequest request, Model model, @PathVariable("id") long id) throws ResourceNotFoundException {

        Museum museum = (Museum) museumController.getMuseumById(id).getBody();
        model.addAttribute("museum", museum);

        return "museum.html";
    }

    @RequestMapping(value = "/museum-add", method = GET)
    public String CreateMuseumPage(HttpServletRequest request, Model model){

        List<Tag> tags = tagController.getAllTags();
        model.addAttribute("tags",tags);

        return "createMuseum.html";
    }
}
