package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TodoItemResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private boolean completed;
}