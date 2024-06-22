package com.erpapp.erpapp.resources;

import com.erpapp.erpapp.constant;
import com.erpapp.erpapp.domain.Employee;
import com.erpapp.erpapp.services.EmployeeServices;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@RestController
@RequestMapping("/api/emp")
public class EmpResource {

    @Autowired
    EmployeeServices employeeServices;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginEmp(@RequestBody Map<String, Object> empMap){
        String email = (String) empMap.get("email");
        String password = (String) empMap.get("password");
        Employee employee = employeeServices.validateEmp(email, password);
        return new ResponseEntity<>(generateJWTToken(employee), HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> empMap){
        String first_name = (String) empMap.get("first_name");
        String last_name = (String) empMap.get("last_name");
        String email = (String) empMap.get("email");
        String password = (String) empMap.get("password");
        Employee employee = employeeServices.registerEmp(first_name, last_name,email,password);
        return new ResponseEntity<>(generateJWTToken(employee), HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(Employee employee){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, constant.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + constant.TOKEN_VALIDITY))
                .claim("emp_id", employee.getEmpId())
                .claim("email", employee.getEmail())
                .claim("first_name", employee.getFirst_name())
                .claim("last_name", employee.getLast_name())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
