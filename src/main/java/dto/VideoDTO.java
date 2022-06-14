package dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class VideoDTO extends IdNameKeyDecDTO {


    private String title;
    private Integer categoryId;
    private String channelId;  // (like_count,dislike_count)
    private Integer viewCount;
    private String attachId;
    private Integer publishedDate;
    private Integer sharedCount;
    private Integer status;
    private Integer type; //  (video,short)
    private Integer previewAttachId;

    private Long duration;

    @NotBlank(message = "VideoId required")
    private String videoId;
}


