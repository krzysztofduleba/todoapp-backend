package app.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TodoListEditRequest {
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = 50, message = "Name length must be between 1 and 50 characters.")
    private String name;
}