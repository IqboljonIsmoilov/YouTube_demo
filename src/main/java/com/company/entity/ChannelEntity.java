package com.company.entity;

import com.company.enums.ChannelStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity {

    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String key;
    @Column
    private LocalDateTime CreatedDate;
    @Column
    private LocalDateTime UpdatedDate;

    @Column
    @Enumerated(EnumType.STRING)
    private ChannelStatus status;

    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "attach_id")
    private String photoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity photo;


    @Column(name = "banner_id")
    private String bannerId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id", insertable = false, updatable = false)
    private AttachEntity banner;
}
