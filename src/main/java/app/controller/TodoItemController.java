package app.controller;

import app.dto.request.CreateTodoItemRequest;
import app.dto.request.UpdateTodoItemRequest;
import app.dto.response.TodoItemResponse;
import app.model.TodoItem;
import app.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoItemController {
    private final TodoItemService itemService;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/lists/{id}/items")
    public ResponseEntity createItem(@PathVariable("id") Long listId, @Valid @RequestBody CreateTodoItemRequest itemRequest) {
        TodoItemResponse response = modelMapper.map(itemService.createItem(
                listId, modelMapper.map(itemRequest, TodoItem.class)), TodoItemResponse.class);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PutMapping("/items")
    public ResponseEntity updateItem(@RequestBody UpdateTodoItemRequest itemRequest) {
        TodoItemResponse response = modelMapper.map(itemService.updateItem(
                modelMapper.map(itemRequest, TodoItem.class)), TodoItemResponse.class);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity deleteItemById(@PathVariable("itemId") Long itemId) {
        itemService.deleteItemById(itemId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/lists/{id}/items")
    public ResponseEntity findItemsByListId(@PathVariable("id") Long id, @PageableDefault Pageable pageable) {
        List<TodoItemResponse> response = itemService.findItemsByListId(id, pageable)
                .stream().map(list -> modelMapper.map(list, TodoItemResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/items/today")
    public ResponseEntity findItemsByToday() {
        List<TodoItemResponse> response = itemService.findItemsByToday()
                .stream().map(list -> modelMapper.map(list, TodoItemResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/items/completed")
    public ResponseEntity findCompletedItems() {
        List<TodoItemResponse> response = itemService.findCompletedItems()
                .stream().map(list -> modelMapper.map(list, TodoItemResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PatchMapping("/items/{itemId}/complete")
    public ResponseEntity completeTask(@PathVariable("itemId") Long itemId) {
        itemService.completeTask(itemId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/items/{itemId}/restore")
    public ResponseEntity restoreTask(@PathVariable("itemId") Long itemId) {
        itemService.restoreTask(itemId);
        return new ResponseEntity(HttpStatus.OK);
    }
}