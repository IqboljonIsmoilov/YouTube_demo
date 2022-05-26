package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.exception.EmailAlreadyExistsException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Value("${server.domain.name}")
    private String domainName;

    public ProfileDTO create(ProfileDTO dto) {

        Email(dto.getEmail());

        ProfileEntity entity = new ProfileEntity();
        entity.setId(UUID.randomUUID().toString());

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setStatus(ProfileStatus.ACTIVE);

        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }


    public void Email(String email) {
        profileRepository.findByEmail(email).ifPresent(profileEntity -> {
            throw new EmailAlreadyExistsException("Email already used!");
        });
    }


    public String updateEmail(ProfileDTO dto, String id) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("Email alredy exits");
        }
        Integer email = profileRepository.updateEmail(dto.getSurname(), dto.getName(), dto.getPassword(), dto.getEmail(), id);
        if (email > 0) {
            return "Update";
        }
        return "not Update";
    }


    public ProfileDTO get(String id) {
        ProfileEntity entity = getById(id);

        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());

        return dto;
    }


    public String updateProfileDetail(ProfileDTO dto, String id) {
        Optional<ProfileEntity> optional = profileRepository.findByNameAndSurname(dto.getName(), dto.getSurname());

        Integer nameAndSurname = profileRepository.updateProfileDetail(dto.getName(), dto.getSurname(), id);

        if (nameAndSurname > 0) {
            return "Update";
        }
        return "not Update";
    }


    public String toOpenUrl(String id) {
        return domainName + "profile/" + id;
    }


    public ProfileEntity getById(String id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            return new ItemNotFoundException("Not Found!");
        });
    }


    public PageImpl<ProfileDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ProfileDTO> dtoList = new ArrayList<>();

        Page<ProfileEntity> entityPage = profileRepository.findAll(pageable);
        entityPage.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }


    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setStatus(dto.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }


    public Object updateProfileAttach(String pId, String id) {
        return null;
    }
}
