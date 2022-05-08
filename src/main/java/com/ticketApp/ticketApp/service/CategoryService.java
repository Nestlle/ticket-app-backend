package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.CategoryDTO;
import com.ticketApp.ticketApp.entity.CategoryEntity;
import com.ticketApp.ticketApp.repository.CategoryRepository;
import com.ticketApp.ticketApp.service.adapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<CategoryDTO> getAllCategoriesWithId() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        for (CategoryEntity entity : categoryEntities) {
            CategoryDTO dto = Adapter.convertCategoryEntityToDTO(entity);
            categoryDTOS.add(dto);
        }

        return categoryDTOS;
    }
}
