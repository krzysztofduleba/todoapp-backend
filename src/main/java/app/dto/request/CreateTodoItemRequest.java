package app.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class CreateTodoItemRequest {
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = 50, message = "Name length must be between 1 and 50 characters.")
    private String name;
    @Size(max = 100, message = "Description length must be between 0 and 100 characters.")
    private String description;
    private LocalDate date;
}