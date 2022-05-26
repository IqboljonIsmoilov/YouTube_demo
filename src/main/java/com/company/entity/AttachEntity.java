package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {

    @Id
    private String id;
    @Column(name = "origen_name")
    private String origenName;
    @Column
    private Long size;
    @Column
    private String type;
    @Column
    private String path;
    @Column
    private LocalDateTime createdDate;
}
