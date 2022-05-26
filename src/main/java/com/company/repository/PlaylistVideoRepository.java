package com.company.repository;

import com.company.entity.PlaylistVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideoEntity, String> {

    Optional<PlaylistVideoEntity> findByPlaylistIdAndVideoId(String playlistId, String videoId);
}