package edu.tcu.cs.superfrogbackendfinal.Authentication.Controllers;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Payload.Request.SignupRequest;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Services.SpiritDirectorService;
import edu.tcu.cs.superfrogbackendfinal.domain.Result;
import edu.tcu.cs.superfrogbackendfinal.domain.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/directors")
public class SpiritDirectorController {
    private SpiritDirectorService service;

    public SpiritDirectorController(SpiritDirectorService service) {
        this.service = service;
    }

    @GetMapping()
    public Result findAll() {
        List<User> all = service.findAll();
        Result result = new Result(true, StatusCode.SUCCESS, "Find All Success", all);
        return result;
    }

    @GetMapping("/{spiritDirectorId}")
    public Result findById(@PathVariable Long spiritDirectorId) {
        return new Result(true, StatusCode.SUCCESS, "Find One Success", service.findById(spiritDirectorId));
    }

    @PostMapping()
    public Result save(@RequestBody User spiritDirector) {
        service.save(spiritDirector);
        return new Result(true, StatusCode.SUCCESS, "Save Success");
    }

    @PutMapping("/{spiritDirectorId}")
    public Result update(@PathVariable Long spiritDirectorId, @RequestBody User spiritDirector) {
        service.update(spiritDirectorId, spiritDirector);
        return new Result(true, StatusCode.SUCCESS, "Update Success");
    }

    @DeleteMapping("/{spiritDirectorId}")
    public Result delete(@PathVariable Long spiritDirectorId) {
        service.delete(spiritDirectorId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

    @PostMapping("/register-user")
    public Result registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        ResponseEntity<?> response = service.registerUser(signUpRequest);
        if (response.getStatusCode() == HttpStatus.valueOf(403)) {
            return new Result(false, StatusCode.FAILURE, "Not Authorized", response);
        } else {
            return new Result(true, StatusCode.SUCCESS, "Student Registered Successfully");
        }
    }

    //method to view entire student roster (if enabled = true and role = student)
    @GetMapping("/student-roster")
    public Result getRoster() {
        List<User> roster = service.getRoster();
        return new Result(true, StatusCode.SUCCESS, "Find Student Roster Success", roster);
    }
}
