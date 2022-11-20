package app.controller;

import app.dto.request.CreateTodoListRequest;
import app.dto.request.TodoListEditRequest;
import app.dto.response.TodoListResponse;
import app.model.TodoList;
import app.service.TodoListService;
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
public class TodoListController {
    private final TodoListService todoListService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/lists")
    public ResponseEntity findAllLists(@PageableDefault Pageable pageable) {
        List<TodoListResponse> response = todoListService.findAllLists(pageable)
                .stream().map(list -> modelMapper.map(list, TodoListResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity findListById(@PathVariable("id") Long id) {
        TodoListResponse response = modelMapper.map(todoListService.findListById(id), TodoListResponse.class);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/lists")
    public ResponseEntity createList(@Valid @RequestBody CreateTodoListRequest createTodoListRequest) {
        TodoListResponse response = modelMapper.map(todoListService.createList(
                modelMapper.map(createTodoListRequest, TodoList.class)), TodoListResponse.class);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PutMapping("/lists")
    public ResponseEntity updateList(@Valid @RequestBody CreateTodoListRequest listRequest) {
        TodoListResponse response = modelMapper.map(todoListService.updateList(
                modelMapper.map(listRequest, TodoList.class)), TodoListResponse.class);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PatchMapping("/lists/{id}")
    public ResponseEntity editList(@PathVariable("id") Long id, @RequestBody TodoListEditRequest todoListEditRequest) {
        todoListService.editListById(id, todoListEditRequest.getName());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/lists/{id}")
    public ResponseEntity deleteListById(@PathVariable("id") Long id) {
        todoListService.deleteListById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}