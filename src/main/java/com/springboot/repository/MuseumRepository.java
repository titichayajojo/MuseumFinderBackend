package com.springboot.repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.springboot.entity.Museum;
import org.json.JSONArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MuseumRepository extends JpaRepository<Museum, Long>, JpaSpecificationExecutor<Museum> {

    Optional<Museum> findByName(String name);
    Optional<Museum> findById(Long id);

    @Query(value = " SELECT * FROM `springboot_demo`.`museums` WHERE `tags` LIKE %:tagName% ;", nativeQuery = true)
    List<Museum> findByTag(@Param("tagName")String tagName);

    @Query(value = "select * from museums m where m.name like %:keyword% or m.tags like %:keyword%", nativeQuery = true)
    List<Museum> findByKeyword(@Param("keyword") String keyword);
    Boolean existsByName(String name);
}
