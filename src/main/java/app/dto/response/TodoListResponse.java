package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TodoListResponse {
    private Long id;
    private String name;
    private List<TodoItemResponse> items = new ArrayList<>();
}