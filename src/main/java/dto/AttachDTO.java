package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private String id;
    private String origenName;
    private String path;
    private Long size;
    private String type;  // extension
    private String duration;
    private LocalDateTime createdDate;
    private String url;


    public AttachDTO() {
    }

    public AttachDTO(String url) {
        this.url = url;
    }
}
