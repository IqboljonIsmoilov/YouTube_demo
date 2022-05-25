package com.company.service;

import com.company.dto.ChannelDTO;
import com.company.dto.ChannelUpdateDTO;
import com.company.dto.ProfileDTO;
import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChannelStatus;
import com.company.enums.ProfileRole;
import com.company.exception.AppBadRequestException;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ProfileService profileService;

    @Value("${server.domain.name}")
    private String domainName;


    public ChannelDTO create(ChannelDTO dto, String profileId) {
        ChannelEntity entity = new ChannelEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(ChannelStatus.ACTIVE);
        entity.setProfileId(profileId);

        try {
            channelRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new AppBadRequestException("Unique!");
        }
        return toDTO(entity);
    }


    public ChannelEntity getById(String id) {
        return channelRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ItemNotFoundException("Not found!");
                });
    }

    public ChannelDTO get(String id) {
        ChannelEntity entity = getById(id);
        return toDTO(entity);
    }


    public ChannelDTO updateChannel(ChannelUpdateDTO dto, String channelId, String profileId) {
        ChannelEntity entity = getById(channelId);

        if (!entity.getProfileId().toString().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUpdatedDate(LocalDateTime.now());

        return toDTO(entity);
    }


    public Boolean changeStatus(String channelId, String profileId) {
        ChannelEntity entity = getById(channelId);

        ProfileEntity profileEntity = profileService.getById(profileId);

        if (entity.getProfileId().toString().equals(profileId) || profileEntity.getRole().equals(ProfileRole.ADMIN)) {
            switch (entity.getStatus()) {
                case ACTIVE -> {
                    channelRepository.updateStatus(ChannelStatus.BLOCK, entity.getId());
                }
                case BLOCK -> {
                    channelRepository.updateStatus(ChannelStatus.ACTIVE, entity.getId());
                }
            }

            return true;
        }
        throw new AppForbiddenException("Not access!");
    }


    public PageImpl<ChannelDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ChannelDTO> dtoList = new ArrayList<>();

        Page<ChannelEntity> entityPage = channelRepository.findAll(pageable);

        entityPage.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public ChannelDTO toDTO(ChannelEntity entity) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        ProfileDTO profileDTO = new ProfileDTO(profileService.toOpenUrl(entity.getProfileId()));
        dto.setProfile(profileDTO);
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }


    public Object updateChannelPhoto(String id, String channelId, String id1) {
        return null;
    }

    public Object updateChannelBanner(String id, String channelId, String id1) {
        return null;
    }
}
