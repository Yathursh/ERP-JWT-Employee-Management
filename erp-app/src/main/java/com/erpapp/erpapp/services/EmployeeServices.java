package com.erpapp.erpapp.services;

import com.erpapp.erpapp.domain.Employee;
import com.erpapp.erpapp.exceptions.EmpAuthException;

public interface EmployeeServices {

    Employee validateEmp(String email, String password) throws EmpAuthException;

    Employee registerEmp(String first_name, String last_name, String email, String password) throws EmpAuthException;


}
