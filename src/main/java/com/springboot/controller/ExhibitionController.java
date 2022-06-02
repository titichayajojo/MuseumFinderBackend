package com.springboot.controller;

import com.springboot.config.FileUploadUtil;
import com.springboot.entity.Exhibition;
import com.springboot.entity.Museum;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.repository.ExhibitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExhibitionController {
    @Autowired
    ExhibitionRepository exhibitionRepository;


    @GetMapping("/exhibitions")
    public List<Exhibition> getAllExhibitions(){
        return exhibitionRepository.findAll();
    }

    @PostMapping("/exhibitions")
    public ResponseEntity<?> createExhibition(@Valid @RequestBody Exhibition exhibition){
        exhibitionRepository.save(exhibition);
        return ResponseEntity.ok(exhibition);
    }


    @GetMapping("/exhibitions/{id}")
    public ResponseEntity<?> getExhibitionById(@PathVariable(value = "id") Long exhibitionId){
        try {
            Exhibition exhibition = exhibitionRepository.findById(exhibitionId).get();
            return ResponseEntity.ok().body(exhibition);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("exhibition not found");
        }

    }


    @PostMapping("/exhibitions/image/{id}")
    public ResponseEntity<?> addImage(@PathVariable(value = "id") Long exhibitionId, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Exhibition exhibition = exhibitionRepository.getById(exhibitionId);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        exhibition.setFileName(fileName);

        Exhibition savedExhibition = exhibitionRepository.save(exhibition);
        String uploadDir = "src/main/resources/static/exhibition-images/" + savedExhibition.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return ResponseEntity.ok(uploadDir);
    }

}
