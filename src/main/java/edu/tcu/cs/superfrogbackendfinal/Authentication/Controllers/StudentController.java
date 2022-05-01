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
    @PreAuthorize("hasRole('DIRECTOR')")
    public Result findAll(){
        List<User> all = service.findAll();
        Result result = new Result(true, StatusCode.SUCCESS, "Find All Success", all);
        return result;
    }

    //studentID is id of sdtudent being looked up
    @GetMapping("/{studentId}{currentId}")
    public Result findById(@PathVariable Long studentId, @PathVariable Long currentId){
        Result result = service.findById(studentId, currentId);
        return result;
    }

    //doesn't need a add new student option here because the only time a Student account is created is when
    //a dirctor makes one, this is in director controller.

    //id is the id of the user that is sending the update request
    //user is the user object (the user object in DB with the new data being updated)
    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody User user){
        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public Result delete(@PathVariable Long id){
        service.delete(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
