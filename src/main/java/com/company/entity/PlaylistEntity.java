package com.company.entity;

import com.company.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "playlist")
public class PlaylistEntity {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;

    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime UpdatedDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaylistStatus status;

    @Column(name = "order_num", nullable = false)
    private Integer orderNum;

    @Column(name = "channel_Id")
    private Integer channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

}
