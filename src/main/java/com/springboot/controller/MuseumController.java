package com.springboot.controller;

import com.springboot.entity.Museum;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.repository.MuseumRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MuseumController {

    @Autowired
    MuseumRepository museumRepository;

    @GetMapping("/museums")
    public List<Museum> getAllMuseums(){
        return museumRepository.findAll();
    }

    @GetMapping("/museums/{id}")
    public ResponseEntity<?> getMuseumById(@PathVariable(value = "id") Long museumId)
        throws ResourceNotFoundException {
            Museum museum = museumRepository.findById(museumId)
                    .orElseThrow(() -> new ResourceNotFoundException("Museum not found for this id :: " + museumId));

        return ResponseEntity.ok().body(museum);
    }

    @PostMapping("/museums")
    public ResponseEntity<?> createMuseum(@Valid @RequestBody Museum museum) {
        museumRepository.save(museum);
        return ResponseEntity.ok(museum);
    }

    @GetMapping("/museums/tags/{tagName}")
    public ResponseEntity<?> getMuseumsByTag(@PathVariable String tagName){
        List<Museum> museums = museumRepository.findByTag(tagName);
        return ResponseEntity.ok(museums);
    }

    @GetMapping("/museums/tags")
    public ResponseEntity<?> getMuseumsByTags(@Valid @RequestBody ArrayList<String> tags){
        List<Museum> museums = museumRepository.findAll();
        ArrayList<Museum> overlapList = new ArrayList<Museum>();

        //loop over each museum
        for(Museum museum : museums){
            //tag array for one museum
            ArrayList<String> museumTags = museum.getTags();

            //find the overlap of the tag array of museum and the input array
            for(String userTag : tags){
                //if tag is matched and museum is not in the list
                if(museumTags.contains(userTag) && !overlapList.contains(museum))
                    overlapList.add(museum);
            }
        }
        return ResponseEntity.ok(overlapList);
    }
}
