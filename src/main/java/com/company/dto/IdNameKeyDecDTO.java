package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdNameKeyDecDTO {
    private String id;
    private String description;
    private String key;
    private LocalDateTime CreatedDate;
    private LocalDateTime UpdatedDate;

}
