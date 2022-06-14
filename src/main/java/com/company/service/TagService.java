package com.company.service;

import dto.TagDTO;
import com.company.entity.TagEntity;
import com.company.exception.ItemNotFoundException;
import com.company.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {


    private final TagRepository tagRepository;


    public TagDTO create(TagDTO dto) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());

        return toDTO(entity);
    }


    public PageImpl<TagDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<TagDTO> dtoList = new ArrayList<>();

        Page<TagEntity> entityPage = tagRepository.findAll(pageable);
        entityPage.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }


    public TagDTO update(String id, TagDTO dto) {
        TagEntity entity = get(id);
        entity.setName("#" + dto.getName());
        entity.setUpdatedDate(LocalDateTime.now());

        return toDTO(entity);
    }


    public Boolean delete(String id) {
        TagEntity entity = get(id);
        tagRepository.delete(entity);
        return true;
    }


    public TagEntity get(String id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> {
                    return new ItemNotFoundException("Not Found!");
                });
    }


    public TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}