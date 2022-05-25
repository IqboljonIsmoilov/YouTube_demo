package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "video")
public class VideoEntity {
    @Id
    private Integer id;
    @Column
    private String description;
    @Column
    private String key;
    @Column
    private String title;
    @Column
    private Integer previewAttachId;
    @Column
    private LocalDateTime publishedDate;
    @Column
    private LocalDateTime createdDate;
    private LocalDateTime UpdatedDate;
    @Column
    private Integer viewCount;
    @Column
    private Integer sharedCount;
    @Column
    private String status;
    @Column
    private Integer type; //  (video,short)

    @Column
    private Long duration;



    @Column(name = "channel_id")
    private Integer channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;


    @Column(name = "attach_id")
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "category_id")
    private Integer categoryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column
    private Boolean visible = true;

}
