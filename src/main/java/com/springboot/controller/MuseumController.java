package com.springboot.controller;

import com.springboot.entity.Museum;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.repository.MuseumRepository;
import com.springboot.config.FileUploadUtil;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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

    @PostMapping("/museums/image/{id}")
    public ResponseEntity<?> addImage(@PathVariable(value = "id") Long museumId, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Museum museum = museumRepository.getById(museumId);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        museum.setImageURL(fileName);

        Museum savedMuseum = museumRepository.save(museum);
        String uploadDir = "src/main/resources/static/museum-images/" + savedMuseum.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return ResponseEntity.ok(uploadDir);
    }
    @GetMapping("/museums/image/{id}")
    public ResponseEntity<?> getImageById(@PathVariable(value = "id") Long museumId){
        Museum museum = museumRepository.getById(museumId);
        String fileName = museum.getImageURL();
        String url = "/museum-images/" + museumId + "/" + fileName;
        return ResponseEntity.ok(url);
    }

    @PostMapping("/museums/tags/{id}")
    public ResponseEntity<?> updateMuseumTagsById(@PathVariable(value = "id") Long museumId, @Valid @RequestBody ArrayList<String> tags){
        Museum museum = museumRepository.getById(museumId);
        museum.setTags(tags);

        Museum savedMuseum = museumRepository.save(museum);
        return ResponseEntity.ok(savedMuseum);
    }

    @GetMapping("/museums/search/{keyword}")
    public ResponseEntity<?> searchByKeyword(@PathVariable (value = "keyword") String keyword){
        List<Museum> museums = museumRepository.findByKeyword(keyword);

        return ResponseEntity.ok(museums);
    }

}
