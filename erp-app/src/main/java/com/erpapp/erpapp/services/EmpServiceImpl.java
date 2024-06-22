package com.erpapp.erpapp.services;

import com.erpapp.erpapp.domain.Employee;
import com.erpapp.erpapp.exceptions.EmpAuthException;
import com.erpapp.erpapp.repositories.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.regex.Pattern;

@Service
@Transactional
public class EmpServiceImpl implements EmployeeServices {

    @Autowired
    EmpRepo empRepo;


    @Override
    public Employee validateEmp(String email, String password) throws EmpAuthException {
        if(email != null) email = email.toLowerCase();
        return empRepo.findByEmailAndPassword(email, password);
    }

    @Override
    public Employee registerEmp(String first_name, String last_name, String email, String password) throws EmpAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new EmpAuthException("Invalid Email Format");
        Integer count = empRepo.getCountByEmail(email);

        if(count > 0)
            throw new EmpAuthException("Email is Already Exists");

        Integer emp_id = empRepo.create( first_name,  last_name,  email,  password);
        return empRepo.findById(emp_id);
    }
}
