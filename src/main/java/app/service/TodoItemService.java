package app.service;

import app.model.TodoItem;
import app.model.TodoList;
import app.model.User;
import app.repository.TodoItemRepository;
import app.repository.TodoListRepository;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoItemService {
    private final TodoListRepository todoListRepository;
    private final TodoItemRepository todoItemRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<TodoItem> findItemsByToday() {
        String username = userService.getUsernameFromAuthentication();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User by username " + username + " was not found"));

        List<TodoList> foundLists =  todoListRepository.findAllByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List by id " + user.getId() + " was not found"));

        List<TodoItem> foundItems = new ArrayList<>();
        for (TodoList todoList : foundLists) {
            for (TodoItem todoItem : todoList.getItems()) {
                if (todoItem.getDate() != null && todoItem.getDate().equals(LocalDate.now())) {
                    foundItems.add(todoItem);
                }
            }
        }

        return foundItems;
    }

    public List<TodoItem> findCompletedItems() {
        String username = userService.getUsernameFromAuthentication();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User by username " + username + " was not found"));

        List<TodoList> foundLists = todoListRepository.findAllByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List by id " + user.getId() + " was not found"));

        List<TodoItem> foundItems = new ArrayList<>();
        for (TodoList todoList : foundLists) {
            for (TodoItem todoItem : todoList.getItems()) {
                if (todoItem.isCompleted() == true) {
                    foundItems.add(todoItem);
                }
            }
        }

        return foundItems;
    }

    public TodoItem createItem(Long listId, TodoItem item) {
        item.setList(todoListRepository.findById(listId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List by id " + listId + " was not found")));
        return todoItemRepository.save(item);
    }

    public TodoItem updateItem(TodoItem item) {
        TodoItem foundItem = todoItemRepository.findById(item.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item by id " + item.getId() + " was not found"));
        item.setList(foundItem.getList());
        return todoItemRepository.save(item);
    }

    public void completeTask(Long itemId) {
        TodoItem foundItem = todoItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item by id " + itemId + " was not found"));

        if (foundItem.isCompleted() != true) {
            foundItem.setCompleted(true);
            todoItemRepository.save(foundItem);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void restoreTask(Long itemId) {
        TodoItem foundItem = todoItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item by id " + itemId + " was not found"));

        if (foundItem.isCompleted() == true) {
            foundItem.setCompleted(false);
            todoItemRepository.save(foundItem);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void deleteItemById(Long itemId) {
        if (todoItemRepository.existsById(itemId)) {
            todoItemRepository.deleteById(itemId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Page<TodoItem> findItemsByListId(Long id, @PageableDefault Pageable pageable) {
        String username = userService.getUsernameFromAuthentication();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User by username " + username + " was not found"));

        TodoList todoList = todoListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List by id " + id + " was not found"));

        if (todoList.getUser().getUsername().equals(username)) {
            return todoItemRepository.findAllByListId(id, pageable);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized");
        }
    }
}