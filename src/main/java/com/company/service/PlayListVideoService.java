package com.company.service;

import com.company.dto.*;
import com.company.entity.PlaylistEntity;
import com.company.entity.PlaylistVideoEntity;
import com.company.entity.VideoEntity;
import com.company.exception.AppBadRequestException;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.PlaylistRepository;
import com.company.repository.PlaylistVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PlayListVideoService {


    @Autowired
    private PlaylistVideoRepository playlistVideoRepository;
    @Autowired
    private VideoService videoService;
    @Autowired
    private PlaylistRepository  playlistRepository;


    public PlaylistVideoDTO create(PlaylistVideoDTO dto, String profileId) {
        VideoEntity videoEntity = videoService.getById(dto.getVideotId().toString());

        PlaylistEntity playlistEntity = getPlaylistById(dto.getPlaylistId().toString());

        if (!playlistEntity.getChannel().getProfileId().toString().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }

        PlaylistVideoEntity entity = new PlaylistVideoEntity();
        entity.setPlaylistId(playlistEntity.getId());
        entity.setVideoId(videoEntity.getId());
        entity.setOrderNum(dto.getOrderNum());

        playlistVideoRepository.save(entity);

        return toDTO(entity);
    }

    public PlaylistVideoDTO update(UpdateOrderNumDTO dto, String playlistVideoId, String profileId) {
        PlaylistVideoEntity entity = getById(playlistVideoId);

        entity.setOrderNum(dto.getOrderNum());
        entity.setUpdateDate(LocalDateTime.now());

        playlistVideoRepository.save(entity);

        return toDTO(entity);
    }


    public Boolean delete(PlaylistVideoIdDTO dto, String profileId) {
        VideoEntity videoEntity = videoService.getById(dto.getVideoId());

        PlaylistEntity playlistEntity = getPlaylistById(dto.getPlaylistId());

        if (!playlistEntity.getChannel().getProfileId().toString().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }

        PlaylistVideoEntity entity = getByPlaylistIdAndVideoId(dto.getPlaylistId(), dto.getVideoId());

        playlistVideoRepository.delete(entity);
        return true;
    }

    public PlaylistVideoEntity getByPlaylistIdAndVideoId(String playlistId, String videoId) {
        return playlistVideoRepository
                .findByPlaylistIdAndVideoId(playlistId, videoId)
                .orElseThrow(() -> {
                    return new AppBadRequestException("Not found!");
                });
    }


    public PlaylistEntity getPlaylistById(String id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ItemNotFoundException("Not found!");
                });
    }

    public PlaylistVideoEntity getById(String id) {
        return playlistVideoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ItemNotFoundException("Not found!");
                });

    }

    public PlaylistVideoDTO toDTO(PlaylistVideoEntity entity) {
        PlaylistVideoDTO dto = new PlaylistVideoDTO();
        dto.setId(entity.getId().toString());
        dto.setPlaylistId(entity.getPlaylistId());
        dto.setOrderNum(entity.getOrderNum());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdateDate());
        return dto;
    }
}
