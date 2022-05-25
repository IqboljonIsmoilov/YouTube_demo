package com.company.repository;

import com.company.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity, String> {

}
