package com.company.entity;

import com.company.enums.EmailType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_history")
@Getter
@Setter
public class EmailEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "created_Date")
    protected LocalDateTime createDate = LocalDateTime.now();
    @Column
    private String toEmail;
    @Column
    @Enumerated(EnumType.STRING)
    private EmailType type;
}