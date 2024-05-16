package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class TaskController {
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	HttpSession session;
	@Autowired
	Account account;
	@GetMapping("/tasks")
	public String index(Model model) {
		List<Task>taskList=taskRepository.findAll();
		model.addAttribute("taskList",taskList);
		
		return "task";
	}
	@GetMapping("/tasks/new")
	public String taskNew(Model model) {
		List<Category>categoryList=categoryRepository.findAll();
		model.addAttribute("categoryList",categoryList);
		return "taskAdd";
	}
	
	@PostMapping("/tasks/add")
	public String taskAdd(
			@RequestParam("category")Integer categoryId,
			@RequestParam("title")String title,
			@RequestParam("closingDate")LocalDate closingDate,
			@RequestParam("memo")String memo) {
		Integer id=account.getId();
		Task task = new Task(categoryId,id,title,closingDate,0,memo);
		taskRepository.save(task);
		return "redirect:/tasks";
		
	}
	
}
