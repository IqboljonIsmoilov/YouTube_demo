package com.company.service;

import com.company.dto.PlaylistAboutDTO;
import com.company.dto.PlaylistDTO;
import com.company.entity.ChannelEntity;
import com.company.entity.PlaylistEntity;
import com.company.enums.PlaylistStatus;
import com.company.exception.AppBadRequestException;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final ChannelService channelService;


    public PlaylistDTO create(PlaylistDTO dto, String channelId, String profileId) {
        ChannelEntity channelEntity = channelService.getById(channelId);

        if (!channelEntity.getProfileId().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }
        PlaylistEntity entity = new PlaylistEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(PlaylistStatus.PUBLIC);
        entity.setOrderNum(dto.getOrderNum());

        try {
            playlistRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new AppBadRequestException("Xato!");
        }
        return toDTO(entity);
    }


    public PlaylistDTO toDTO(PlaylistEntity entity) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        dto.setStatus(entity.getStatus().toString());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }


    public PlaylistDTO updatePlaylist(PlaylistAboutDTO dto, String playlistId, String profileId) {
        PlaylistEntity entity = getById(playlistId);

        if (!entity.getChannel().getProfileId().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setOrderNum(dto.getOrderNum());
        entity.setUpdatedDate(LocalDateTime.now());
        try {
            playlistRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new AppBadRequestException("Xato!");
        }
        return toDTO(entity);
    }


    public PlaylistEntity getById(String id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ItemNotFoundException("Not found!");
                });
    }


    public Object delete(String playlistId, String idFromHeader) {
        return null;
    }
}