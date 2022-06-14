package dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistVideoDTO extends TimeAndIdDTO {

    private Integer playlistId;
    private Integer videotId;
    private Integer orderNum;

}
