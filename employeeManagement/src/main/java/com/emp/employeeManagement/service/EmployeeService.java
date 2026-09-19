package com.emp.employeeManagement.service;

import com.emp.employeeManagement.Exception.DuplicateIdException;
import com.emp.employeeManagement.Exception.UserNotFoundException;
import com.emp.employeeManagement.entity.Employee;
import com.emp.employeeManagement.repository.EmpRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {


    EmpRepository repository;
    public EmployeeService(EmpRepository repository){
        this.repository=repository;
    }

    public List<Employee> getAllEmployee(){
        return repository.findAll();
    }

    @Transactional
    public Employee saveEmployee(Employee employee){
        Optional<Employee> emp = repository.findById((long)employee.getId());

        if (emp.isPresent()){
            throw new DuplicateIdException("Employee already present with name:"+emp.get().getName());
        }
        return repository.save(employee);
    }

    @Cacheable(value = "employees", key = "#id")
    public Employee findEmployeeById(Integer id){
        return repository.findById((long) id).orElseThrow();
    }

    @CacheEvict(value = "employees", key = "#id")
    public String deleteEmployeeById(Integer id){
        repository.deleteById((long) id);
        return "Employee Deleted SuccessFully";
    }
}
