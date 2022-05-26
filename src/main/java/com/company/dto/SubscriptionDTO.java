package com.company.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDTO {

    private Integer id;
    private Integer profile_id;
    private Integer channel_id;
    private LocalDateTime created_date;
}

