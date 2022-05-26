package com.company.service;

import com.company.dto.VideoAboutDTO;
import com.company.dto.VideoDTO;
import com.company.entity.AttachEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.ChannelEntity;
import com.company.entity.VideoEntity;
import com.company.enums.VideoStatus;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VideoService {

    public final VideoRepository videoRepository;

    private final ChannelService channelService;

    private final CategoryService categoryService;

    private final AttachService attachService;

    @Value("${server.domain.name}")
    private String domainName;


    public VideoDTO create(VideoDTO dto, String profileId) {
        ChannelEntity channelEntity = channelService.getById(dto.getChannelId().toString());

        if (!channelEntity.getProfileId().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }
        CategoryEntity categoryEntity = categoryService.getById(dto.getCategoryId().toString());
        AttachEntity attachEntity = attachService.getById(dto.getVideoId());
        VideoEntity entity = new VideoEntity();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        entity.setStatus(String.valueOf(VideoStatus.CREATED));
        entity.setType(dto.getType());

        entity.setCategoryId(categoryEntity.getId());
        entity.setAttachId(attachEntity.getId());
        entity.setChannelId(Integer.valueOf(channelEntity.getId()));

        videoRepository.save(entity);
        return toDTO(entity);
    }


    public VideoDTO updateVideoDetail(VideoAboutDTO dto, String videoId, String profileId) {
        VideoEntity entity = getById(videoId);

        if (!entity.getChannel().getProfileId().toString().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUpdatedDate(LocalDateTime.now());

        videoRepository.save(entity);
        return toDTO(entity);
    }


    public Boolean delete(String videoId, String profileId) {
        VideoEntity entity = getById(videoId);

        if (!entity.getChannel().getProfileId().toString().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }
        videoRepository.updateVisible(entity.getId().toString());

        attachService.delete(entity.getAttachId().toString());

        if (Optional.ofNullable(entity.getPreviewAttachId()).isPresent()) {
            attachService.delete(entity.getPreviewAttachId().toString());
        }
        return true;
    }


    public Object update(Integer id, VideoDTO dto) {
        return null;
    }


    public VideoEntity getById(String id) {
        return videoRepository.findByIdAndVisible(id, true).orElseThrow(() -> {
            throw new ItemNotFoundException("Not found!");
        });
    }


    public VideoDTO toDTO(VideoEntity entity) {
        VideoDTO dto = new VideoDTO();
        dto.setId(entity.getId().toString());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
        dto.setSharedCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());
        dto.setDuration(entity.getDuration());
        dto.setPublishedDate(dto.getPublishedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }
}