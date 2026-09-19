package com.emp.employeeManagement.controller;

import com.emp.employeeManagement.Exception.DuplicateIdException;
import com.emp.employeeManagement.Exception.UserNotFoundException;
import com.emp.employeeManagement.entity.Employee;
import com.emp.employeeManagement.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class EmployeeController {

    EmployeeService service;
    public EmployeeController(EmployeeService service){
        this.service=service;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        LocalDateTime time = LocalDateTime.now();
        log.info("Method starts :"+time);

        List<Employee> empList = service.getAllEmployee();
        log.info("Method ends :"+time);
        return  new ResponseEntity<List<Employee>>(empList,HttpStatus.OK);

    }

    @GetMapping("/employeeById")
    public ResponseEntity<Employee> findById(@RequestParam Integer empId){
        LocalDateTime time = LocalDateTime.now();
        log.info("Method starts :"+time);

        Employee emp;
        try{
             emp=service.findEmployeeById(empId);
        }catch(Exception e){
            log.error("Employee already present in db");
            throw new UserNotFoundException("Employee with id: "+empId+" not present in DB!");
        }
        log.info("Method ends :"+time);
        return new ResponseEntity<Employee>(emp,HttpStatus.OK);
    }
    @PostMapping("/saveEmployee")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        LocalDateTime time = LocalDateTime.now();
        log.info("Method starts :"+time);

        Employee emp;
        try{
             emp=service.saveEmployee(employee);
        }catch(Exception e){
            throw new DuplicateIdException(e.getMessage());
        }
        log.info("Method ends :"+time);
        return new ResponseEntity<Employee>(emp,HttpStatus.OK);
    }

    @PostMapping("/delete/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("empId") Integer empId){
        return  new ResponseEntity<String>(service.deleteEmployeeById(empId),HttpStatus.ACCEPTED);
    }

}
