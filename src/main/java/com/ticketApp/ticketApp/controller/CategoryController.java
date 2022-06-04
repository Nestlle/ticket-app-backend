package com.ticketApp.ticketApp.controller;

import com.ticketApp.ticketApp.dto.CategoryDTO;
import com.ticketApp.ticketApp.entity.CategoryEntity;
import com.ticketApp.ticketApp.service.CategoryService;
import com.ticketApp.ticketApp.service.EventService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    private List<String> getAllCategories(){
        return categoryService.getAllCategories()
                .stream().map(CategoryEntity::getCategoryName).collect(Collectors.toList());
    }

    @GetMapping("/withId")
    private List<CategoryDTO> getAllCategoriesWithId(){
        return categoryService.getAllCategoriesWithId();
    }

}
