package com.erpapp.erpapp.repositories;

import com.erpapp.erpapp.domain.Employee;
import com.erpapp.erpapp.exceptions.EmpAuthException;

public interface EmpRepo {

    Integer create(String first_name, String last_name, String email, String password) throws EmpAuthException;

    Employee findByEmailAndPassword(String email, String password) throws EmpAuthException;
     Integer getCountByEmail(String email);
    Employee findById(Integer emp_id);


}
