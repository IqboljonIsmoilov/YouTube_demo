package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    @Value("${server.domain.name}")
    private String domainName;

    private final CategoryRepository categoryRepository;


    public CategoryDTO created(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());

        try {
            categoryRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new AppBadRequestException("Xato!");
        }
        return toDTO(entity);
    }


    public CategoryEntity getById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    return new ItemNotFoundException("Not Found!");
                });
    }


    public CategoryDTO get(String categoryId) {
        CategoryEntity entity = getById(categoryId);
        return toDTO(entity);
    }


    public List<CategoryDTO> list() {
        List<CategoryDTO> list = new ArrayList<>();
        categoryRepository
                .findAll(Sort.by(Sort.Direction.ASC, "name"))
                .forEach(entity -> list.add(toDTO(entity)));
        return list;
    }


    public CategoryDTO update(String id, CategoryDTO dto) {
        CategoryEntity entity = getById(id);
        entity.setName(dto.getName());
        entity.setUpdatedDate(LocalDateTime.now());

        try {
            categoryRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new AppBadRequestException("Xato!");
        }
        return toDTO(entity);
    }


    public String toOpenUrl(String id) {
        return domainName + "category/" + id;
    }

    public Boolean delete(String id) {
        CategoryEntity entity = getById(id);
        categoryRepository.delete(entity);
        return true;
    }


    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}