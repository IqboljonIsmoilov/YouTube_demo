package com.company.repository;

import com.company.dto.ChannelDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChannelStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Integer> {
    Optional<ChannelEntity> findById(String id);


    @Transactional
    @Modifying
    @Query("update ChannelEntity set status = :status where id = :id")
    int updateStatus(@Param("status") ChannelStatus status, @Param("id") String id);

    List<ChannelEntity> findAllByProfileId(UUID profileId, Sort sort);

    Page<ChannelEntity> findAllByStatus(ChannelStatus status, Pageable pageable);

}
