package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    private String content;
    private String profileId;
    private Integer articleId;
    private Integer videoId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
