package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.entity.CategoryEntity;
import com.ticketApp.ticketApp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
}
