package edu.tcu.cs.superfrogbackendfinal.Authentication.Controllers;


import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Services.StudentService;
import edu.tcu.cs.superfrogbackendfinal.domain.Result;
import edu.tcu.cs.superfrogbackendfinal.domain.StatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@PreAuthorize("hasRole('STUDENT') or hasRole('DIRECTOR')")
public class StudentController {
    private StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping()
    public Result findAll(){
        List<User> all = service.findAll();
        Result result = new Result(true, StatusCode.SUCCESS, "Find All Success", all);
        return result;
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id){
        return new Result(true, StatusCode.SUCCESS, "Find One Success", service.findById(id));
    }

    //doesn't need a add new customer option here because the only time a Student account is created is when
    //a dirctor makes one, this is in director controller.

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody User user){
        service.update(id, user);
        return new Result(true, StatusCode.SUCCESS, "Update Success");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        service.delete(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
