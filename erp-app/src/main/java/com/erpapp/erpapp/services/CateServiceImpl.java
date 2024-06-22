package com.erpapp.erpapp.services;

import com.erpapp.erpapp.domain.Category;
import com.erpapp.erpapp.exceptions.EmpBadRequestException;
import com.erpapp.erpapp.exceptions.EmpResourceNotFoundException;
import com.erpapp.erpapp.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CateServiceImpl implements CategoryService{


    @Autowired
    CategoryRepo categoryRepo;


    @Override
    public List<Category> fetchAllCategories(Integer emp_id) {
        return List.of();
    }

    @Override
    public Category fetchCategoryById(Integer emp_id, Integer category_id) throws EmpResourceNotFoundException {
        return null;
    }

    @Override
    public Category addCategory(Integer emp_id, String title, String description) throws EmpBadRequestException {
        int category_id = categoryRepo.create(emp_id, title, description);
        return categoryRepo.findById(emp_id, category_id);
    }

    @Override
    public void updateCategory(Integer emp_id, Integer category_id, Category category) throws EmpBadRequestException {

        categoryRepo.update(emp_id, category_id, category);

    }

    @Override
    public void removeCategoryWithAllTransactions(Integer emp_id, Integer category_id) throws EmpResourceNotFoundException {

    }
}
