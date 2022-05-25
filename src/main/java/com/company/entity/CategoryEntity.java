package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime UpdatedDate;


}
