package com.example.todo_mgr.Controller;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import com.example.todo_mgr.Model.Todo;
import com.example.todo_mgr.Repository.TodoRepository;
import com.example.todo_mgr.Security.UserDetailsServiceImpl;
// import com.example.todo_mgr.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    // @Autowired
    // private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;    

    @GetMapping("/todos")
    public String getTodos( @RequestParam(defaultValue = "0", required = false)String page, 
                            @RequestParam(defaultValue = "20", required = false)String size,
                            Principal principal, 
                            Model model ){
        int pageVal = Integer.parseInt(page);
        int sizeVal = Integer.parseInt(size);

        List<Todo> todos = todoRepository.findTodosByUserName(principal.getName(), PageRequest.of(pageVal, sizeVal, Direction.ASC, "description"));
        model.addAttribute("todos", todos);
        return "todos";
    }
    
    @PostMapping("/todos/add")
    public String addTodos(@ModelAttribute Todo todo, Principal principal, Model model){
        Todo newTodo = new Todo();
        newTodo.setUser(userDetailsService.getLoggedUser());
        newTodo.setDescription(todo.getDescription());
        newTodo.setDueDate(todo.getDueDate());

        todoRepository.save(newTodo);
        return getTodos("0", "10", principal, model );

    }

    @PostMapping("/todos")
    public void addTodos(@ModelAttribute Todo todo){
        todo.setUser(userDetailsService.getLoggedUser());
        
        todoRepository.save(todo);
        
        try {
            ResponseEntity.created(new URI("/todos/"+todo.getId()));    
        } catch (Exception e) {
            //TODO: handle exception
        }
         
    }


    @PostMapping("/todos/{id}/delete")
    public void deletePost(@PathVariable int id) {
        todoRepository.deleteById(id);
    }
    
    @PostMapping("/todos/{id}/update")
    public void updatePost(@PathVariable int id, @ModelAttribute Todo todo) {
        Todo updateTodo = todoRepository.findById(id).get();
        updateTodo.setDescription(todo.getDescription());
        updateTodo.setDueDate(todo.getDueDate());

        todoRepository.save(updateTodo);
    }


}