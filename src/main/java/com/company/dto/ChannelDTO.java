package com.company.dto;

import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChannelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDTO extends IdNameKeyDecDTO {
    private ChannelStatus status;


    @NotBlank(message = "Name required")
    private String name;

    @NotNull(message = "Description not be null")
    private String description;

    private String photoId;
    private AttachDTO photo;

    private String bannerId;
    private AttachDTO banner;

    private String profileId;
    private ProfileDTO profile;

    private String url;

    public ChannelDTO() {
    }

    public ChannelDTO(String url) {
        this.url = url;
    }
}
