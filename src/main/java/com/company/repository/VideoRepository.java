package com.company.repository;

import com.company.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<VideoEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "update VideoEntity set visible = false where id =:id")
    void updateVisible(@Param("id") String id);


    Optional<VideoEntity> findByIdAndVisible(String id, Boolean visible);
}
