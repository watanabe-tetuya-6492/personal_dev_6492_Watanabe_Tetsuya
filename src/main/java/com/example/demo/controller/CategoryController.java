package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Controller
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepository;
	@GetMapping("/categories")
	public String index(Model model) {
		List<Category>categoryList=categoryRepository.findAllByOrderByIdAsc();
		model.addAttribute("categoryList",categoryList);
		return "category";
	}
	@GetMapping("/categories/new")
	public String newCategory() {
		return "categoryAdd";
	}
	@GetMapping("/categories/{id}/delete")
	public String delete(@PathVariable("id")Integer id) {
		categoryRepository.deleteById(id);
		return "redirect:/categories";
	}
	@GetMapping("/categories/{id}/edit")
	public String edit(@PathVariable("id")Integer id,
						Model model) {
		Category category=categoryRepository.findById(id).get();
		model.addAttribute(category);
		return "categoryEdit";
	}
	
	
	@PostMapping("/categories/add")
	public String categoryAdd(@RequestParam("name")String name,Model model) {
		List<String>errorList=new ArrayList<>();
		if(name.equals(null)||name.length()==0) {
			errorList.add("カテゴリが未入力です");
			model.addAttribute("error",errorList);
			return "categoryAdd";
		}
		Category category=new Category(name);
		categoryRepository.save(category);
		return "redirect:/categories";
	}
	@PostMapping("/categories/{id}/update")
	public String update(
			@PathVariable("id")Integer id,
			@RequestParam("name")String name,
			Model model) {
		Category category = new Category(id,name);
		List<String>errorList=new ArrayList<>();
		if(name.equals(null)||name.length()==0) {
			errorList.add("カテゴリが未入力です");
			model.addAttribute("error",errorList);
			model.addAttribute("category",category);
			return "categoryEdit";
		}
		
		categoryRepository.save(category);
		return "redirect:/categories";
	}

}
