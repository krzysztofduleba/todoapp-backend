package app.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UpdateTodoItemRequest {
    @NotBlank(message = "Id cannot be blank.")
    private Long id;
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = 100, message = "Name length must be between 1 and 100 characters.")
    private String name;
    @Size(min = 1, max = 250, message = "Description length must be between 1 and 250 characters.")
    private String description;
    private LocalDate date;
    private boolean completed;
}