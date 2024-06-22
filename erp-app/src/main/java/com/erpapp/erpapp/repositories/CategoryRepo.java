package com.erpapp.erpapp.repositories;

import com.erpapp.erpapp.domain.Category;
import com.erpapp.erpapp.exceptions.EmpBadRequestException;
import com.erpapp.erpapp.exceptions.EmpResourceNotFoundException;

import java.util.List;

public interface  CategoryRepo {

    List<Category> findAll(Integer userId) throws EmpResourceNotFoundException;

    Category findById(Integer emp_id, Integer category_id) throws EmpResourceNotFoundException;

    Integer create(Integer emp_id, String title, String description) throws EmpBadRequestException;

    void update(Integer emp_id, Integer category_id, Category category) throws EmpBadRequestException;

    void removeById(Integer emp_id, Integer category_id);
}
