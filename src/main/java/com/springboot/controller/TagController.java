package com.springboot.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.springboot.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.exception.ResourceNotFoundException;
import com.springboot.entity.Tag;
import com.springboot.repository.TagRepository;

@RestController
@RequestMapping("/api")
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/guest/tags")
    public List < Tag > getAllTags() {
        return tagRepository.findAll();
    }

    @GetMapping("/guest/tags/{id}")
    public ResponseEntity < Tag > getTagById(@PathVariable(value = "id") Long tagId)
            throws ResourceNotFoundException {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found for this id :: " + tagId));
        return ResponseEntity.ok().body(tag);
    }

    @PostMapping("/admin/tags")
    public ResponseEntity<?> createTag(@Valid @RequestBody Tag tag) {
        if(tagRepository.existsByName(tag.getName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag already exist");
        }
        tagRepository.save(tag);
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/admin/tags/{id}")
    public Map < String, Boolean > deleteTagById(@PathVariable(value = "id") Long tagId)
            throws ResourceNotFoundException {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found for this id :: " + tagId));

        tagRepository.delete(tag);
        Map < String, Boolean > response = new HashMap < > ();
        response.put(tag.getName() + " is deleted", Boolean.TRUE);
        return response;
    }
}