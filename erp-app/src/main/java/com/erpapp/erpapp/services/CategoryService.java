package com.erpapp.erpapp.services;

import com.erpapp.erpapp.domain.Category;
import com.erpapp.erpapp.exceptions.EmpBadRequestException;
import com.erpapp.erpapp.exceptions.EmpResourceNotFoundException;

import java.util.List;

public interface CategoryService {

    List<Category> fetchAllCategories(Integer emp_id);

    Category fetchCategoryById(Integer emp_id, Integer category_id) throws EmpResourceNotFoundException;

    Category addCategory(Integer emp_id, String title, String description) throws EmpBadRequestException;

    void updateCategory(Integer emp_id, Integer category_id, Category category) throws EmpBadRequestException;

    void removeCategoryWithAllTransactions(Integer emp_id, Integer category_id) throws EmpResourceNotFoundException;
}
