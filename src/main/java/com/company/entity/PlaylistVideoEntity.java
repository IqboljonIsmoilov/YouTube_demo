package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "playlistVideo")
public class PlaylistVideoEntity {

    @Id
    private Integer id;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime updateDate;

    @Column(name = "playlist_id")
    private Integer playlistId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", insertable = false, updatable = false)
    private PlaylistEntity playList;

    @Column(name = "video_id")
    private Integer videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    private PlaylistEntity video;

    @Column
    private Integer orderNum;
}
