package com.erpapp.erpapp.repositories;

import com.erpapp.erpapp.domain.Employee;
import com.erpapp.erpapp.exceptions.EmpAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class EmpRepoImpl implements EmpRepo{

    private static final String SQL_CREATE = "INSERT INTO EMP_USER(EMP_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES (NEXTVAL('EMP_USER_SEQ'), ?, ?, ?, ?)";

    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT (*) FROM EMP_USER WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT EMP_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM EMP_USER WHERE EMP_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT EMP_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM EMP_USER WHERE EMAIL = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String first_name, String last_name, String email, String password) throws EmpAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, first_name);
                ps.setString(2, last_name);
                ps.setString(3,email);
                ps.setString(4, hashedPassword);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("EMP_ID");
        }
        catch(Exception e){
            throw new EmpAuthException("Invalid details");
        }

    }

    @Override
    public Employee findByEmailAndPassword(String email, String password) throws EmpAuthException {
        try {
            Employee employee = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, empRowMapper);
            if(!BCrypt.checkpw(password, employee.getPassword()))
                throw new EmpAuthException("Invalid email or password");
            return employee;
        }catch (EmptyResultDataAccessException e) {
            throw new EmpAuthException("Invalid email or password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);

    }

    @Override
    public Employee findById(Integer emp_id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{emp_id}, empRowMapper);
    }

    private RowMapper<Employee> empRowMapper = ((rs, rowNum) -> {
        return new Employee(rs.getInt("EMP_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });
}
