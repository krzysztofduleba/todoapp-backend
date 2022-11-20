package app.repository;

import app.model.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    Page<TodoList> findAll(Pageable pageable);
    Optional<Page<TodoList>> findAllByUserId(Pageable pageable, Long id);
    Optional<List<TodoList>> findAllByUserId(Long id);
}