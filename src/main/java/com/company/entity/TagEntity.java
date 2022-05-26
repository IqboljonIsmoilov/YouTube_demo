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
@Table(name = "tag")
public class TagEntity {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime UpdatedDate;
}
