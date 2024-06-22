package com.erpapp.erpapp.repositories;


import com.erpapp.erpapp.domain.Category;
import com.erpapp.erpapp.exceptions.EmpBadRequestException;
import com.erpapp.erpapp.exceptions.EmpResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CategoryRepoImpl implements CategoryRepo{


    private static final String SQL_CREATE = "INSERT_INTO EMP_CATEGORY(CATEGORY_ID, EMP_ID, TITLE, DESCRIPTION) VALUES(NEXTVAL(EMP_CAT_SEQ), ?, ?, ?)";
    private static final String SQL_FIND_BY_ID ="SELECT C.CATEGORY_ID, C.EMP_ID, C.TITLE, C.DESCRIPTION, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM EMP_SALARY T RIGHT OUTER JOIN EMP_CATEGORY C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.EMP_ID = ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID";
    private static final String SQL_UPDATE = "UPDATE EMP_CATEGORY SET TITLE = ?, DESCRIPTION = ? " +
            "WHERE EMP_ID = ? AND CATEGORY_ID = ?";



    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public List<Category> findAll(Integer emp_id) throws EmpResourceNotFoundException {
        return List.of();
    }

    @Override
    public Category findById(Integer emp_id, Integer categoryId) throws EmpResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{emp_id, categoryId}, categoryRowMapper);
        }catch (Exception e) {
            throw new EmpResourceNotFoundException("Category not found");
        }
    }

    @Override
    public Integer create(Integer emp_id, String title, String description) throws EmpBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, emp_id);
                ps.setString(2, title);
                ps.setString(3, description);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("CATEGORY_ID");
        }catch (Exception e) {
            throw new EmpBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer emp_id, Integer category_id, Category category) throws EmpBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{category.getTitle(), category.getDescription(), emp_id, category_id});
        }catch (Exception e) {
            throw new EmpBadRequestException("Invalid request");
        }

    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {

    }

    private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
        return new Category(rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getString("TITLE"),
                rs.getString("DESCRIPTION"));
    });
}
