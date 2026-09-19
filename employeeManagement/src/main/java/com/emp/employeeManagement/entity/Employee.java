package com.emp.employeeManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Entity
@Data
@Table(name = "employee")
public class Employee {
    @Id
    int id;
    String name;
    String designation;
    int salary;
}
