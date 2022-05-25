package com.company.service;

import com.company.dto.VideoLikeDTO;
import com.company.entity.VideoLikeEntity;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.VideoLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VideoLikeService {

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    public VideoLikeDTO createVideolike(VideoLikeDTO dto) {

        VideoLikeEntity entity = new VideoLikeEntity();

        entity.setCreatedDate(dto.getCreatedDate());
        entity.setVideoId(dto.getVideoId());
        entity.setProfileId(dto.getProfileId());

        videoLikeRepository.save(entity);
        dto.setId(entity.getId().toString());
        return dto;
    }

    public Boolean remove(String likeId, String profileId) {
        VideoLikeEntity entity = getById(likeId);

        if (!entity.getProfileId().toString().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }

        videoLikeRepository.delete(entity);
        return true;
    }


    public VideoLikeEntity getById(String id) {
        return videoLikeRepository.findById(id)
                .orElseThrow(() -> {
                    return new ItemNotFoundException("Not found!");
                });
    }

}
