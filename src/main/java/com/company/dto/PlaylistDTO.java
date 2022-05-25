package com.company.dto;

import com.company.entity.ChannelEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO extends IdNameKeyDecDTO {

    @NotBlank(message = "name")
    private String name;

    private Integer channelId;

    @NotNull(message = "not null")
    private Integer orderNum;


    private String status;
}
