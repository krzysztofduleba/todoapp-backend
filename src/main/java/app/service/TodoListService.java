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

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TodoItemRepository todoItemRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Page<TodoList> findAllLists(@PageableDefault Pageable pageable) {
        String username = userService.getUsernameFromAuthentication();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User by username " + username + " was not found"));
        return todoListRepository.findAllByUserId(pageable, user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List by id " + user.getId() + " was not found"));
    }

    public TodoList findListById(Long id) {
        String username = userService.getUsernameFromAuthentication();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User by username " + username + " was not found"));
        TodoList todoList = todoListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List by id " + id + " was not found"));

        if (todoList.getUser().getUsername().equals(username)) {
            return todoListRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List by id " + id + " was not found"));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized");
        }
    }

    public TodoList createList(TodoList list) {
        String username = userService.getUsernameFromAuthentication();

        list.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND))));
        return todoListRepository.save(list);
    }

    public TodoList updateList(TodoList list) {
        if (todoListRepository.existsById(list.getId())) {
            String username = userService.getUsernameFromAuthentication();
            list.setUser(userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND))));
            return todoListRepository.save(list);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteListById(Long id) {
        TodoList list = todoListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        for (TodoItem item: list.getItems()) {
            todoItemRepository.delete(item);
        }
        todoListRepository.deleteById(id);
    }

    public void editListById(Long id, String listName) {
        TodoList list = todoListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        list.setName(listName);
        todoListRepository.save(list);
    }
}