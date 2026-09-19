package com.emp.employeeManagement.Exception;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ErrorMessage {
    String message;
    int status;
    String description;

    public ErrorMessage(String message,String description,int status){
        this.message=message;
        this.description=description;
        this.status=status;
    }


}
