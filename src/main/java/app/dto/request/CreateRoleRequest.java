package app.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateRoleRequest {
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = 20, message = "Name length must be between 5 and 50 characters.")
    private String name;
}