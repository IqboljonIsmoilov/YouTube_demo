package dto;

import com.company.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO extends TimeAndIdDTO {
    @NotNull(message = "not name")
    private String name;
    @NotNull(message = "not surname")
    private String surname;
    @NotNull(message = "not email")
    private String email;
    @NotNull(message = "not password")
    private String password;

    private String attachId;

    private String status;

    private ProfileRole role;
    private String jwt;

    private AttachDTO photo;

    private String url;

    public ProfileDTO() {
    }

    public ProfileDTO(String url) {
        this.url = url;
    }
}
