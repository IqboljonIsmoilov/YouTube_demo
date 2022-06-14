package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO extends BaseDTO {

    private String url;

    public CategoryDTO() {
    }

    public CategoryDTO(String url) {
        this.url = url;
    }
}
