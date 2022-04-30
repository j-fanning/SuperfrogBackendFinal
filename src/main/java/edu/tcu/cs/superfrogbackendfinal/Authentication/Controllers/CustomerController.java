package edu.tcu.cs.superfrogbackendfinal.Authentication.Controllers;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Services.CustomerService;
import edu.tcu.cs.superfrogbackendfinal.domain.Result;
import edu.tcu.cs.superfrogbackendfinal.domain.StatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('DIRECTOR')")
public class CustomerController {

    private CustomerService customerService;
    //Spring will automatically inject an instance of ArtifactDao into this class
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('DIRECTOR')")
    public Result findAll(){
        List<User> all = customerService.findAll();
        Result result = new Result(true, StatusCode.SUCCESS, "Find All Success", all);
        return result;
    }

    @GetMapping("/{customerId}/{currentId}")
    public Result findById(@PathVariable Long customerId, @PathVariable Long currentId){
        return customerService.findById(customerId, currentId);
    }

    @PostMapping()
    public Result save(@RequestBody User newCustomer){
        customerService.save(newCustomer);
        return new Result(true, StatusCode.SUCCESS, "Save Success");
    }

    @PutMapping("/{Id}")
    public Result update(@PathVariable Long Id, @RequestBody User updatedCustomer){
        //ID is user that is requesting the update, updatedCustomer is the new data
        System.out.println("TEST1");
        return customerService.update(Id, updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public Result delete(@PathVariable Long customerId){
        customerService.delete(customerId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
