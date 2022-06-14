package com.company.service;

import dto.PlaylistVideoDTO;
import dto.PlaylistVideoIdDTO;
import dto.UpdateOrderNumDTO;
import com.company.entity.PlaylistEntity;
import com.company.entity.PlaylistVideoEntity;
import com.company.entity.VideoEntity;
import com.company.exception.AppBadRequestException;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.PlaylistRepository;
import com.company.repository.PlaylistVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PlayListVideoService {


    private final PlaylistVideoRepository playlistVideoRepository;
    private final VideoService videoService;

    private final PlaylistRepository playlistRepository;


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