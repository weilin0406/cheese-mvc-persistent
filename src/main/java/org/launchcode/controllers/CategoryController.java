package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value="")
    public String index(Model model) {

        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Categories");

        return "category/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCategoryForm(Model model) {
        model.addAttribute("title", "Add Category");
        model.addAttribute(new Category());
        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Category category, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "category/add";
        }

        categoryDao.save(category);
        return "redirect:";
    }

    @RequestMapping(value = "edit/{categoryId}", method = RequestMethod.GET)
    public String displayCategoryEditForm(Model model, @PathVariable int categoryId){
        model.addAttribute("title", "Edit "+ categoryDao.findOne(categoryId).getName());
        model.addAttribute("category", categoryDao.findOne(categoryId));
        return "category/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String editCategory(Model model, @RequestParam int categoryId, @ModelAttribute @Valid Category category, Errors errors){

        if (errors.hasErrors()){
            model.addAttribute("categoryId", categoryId);
            return "category/edit";
        }

        Category currCategory = categoryDao.findOne(categoryId);
        currCategory.setName(category.getName());
        categoryDao.save(currCategory);

        return "redirect:../cheese/category/" + categoryId;
    }


}
