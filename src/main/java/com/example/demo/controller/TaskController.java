package com.example.demo.controller;

import java.time.LocalDate;
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
		List<String>errorList=new ArrayList<>();
		List<Task>taskList=taskRepository.findAll();
		List<Category>categoryList=categoryRepository.findAll();
		if(taskList.size()==0) {
			errorList.add("タスクがありません。新規登録をお願いいたします。");
			model.addAttribute("categoryList",categoryList);
			model.addAttribute("error",errorList);
			return "taskAdd";
		}
		model.addAttribute("taskList",taskList);
		model.addAttribute("categoryList",categoryList);
		return "task";
	}
	@GetMapping("/tasks/{id}")
	public String taskIndex(@PathVariable("id")Integer id,
			Model model) {
		List<String>errorList=new ArrayList<>();
		List<Task>taskList=taskRepository.findByCategoryId(id);
		List<Category>categoryList=categoryRepository.findAll();
		if(taskList.size()==0) {
			errorList.add("タスクがありません。新規登録をお願いいたします。");
			model.addAttribute("error",errorList);
			model.addAttribute("categoryList",categoryList);
			return "taskAdd";
		}
		model.addAttribute(taskList);
		model.addAttribute("categoryList",categoryList);
		return "task";
	}
	@GetMapping("/tasks/new")
	public String taskNew(Model model) {
		List<Category>categoryList=categoryRepository.findAll();
		model.addAttribute("categoryList",categoryList);
		return "taskAdd";
	}
	@GetMapping("/tasks/{id}/edit")
	public String taskEdit(
			@PathVariable("id")Integer id,
			Model model) {
		Task task=taskRepository.findById(id).get();
		Category category=categoryRepository.findById(task.getCategoryId()).get();
		List<Category>categoryList=categoryRepository.findByNameNotLike(category.getName());
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("category",category);
		model.addAttribute(task);
		model.addAttribute("id",id);
		return "taskEdit";
	}
	
	@GetMapping("/tasks/{id}/increase")
	public String increase(@PathVariable("id")Integer id) {
		
		return "redirect:/tasks";
	}
	@GetMapping("/tasks/{id}/decrease")
	public String decrease(@PathVariable("id")Integer id) {
		return "redirect:/tasks";
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
	@PostMapping("/tasks/{id}/update")
	public String taskUpdate(
			@PathVariable("id")Integer id,
			@RequestParam("category")Integer categoryId,
			@RequestParam("title")String title,
			@RequestParam("closingDate")LocalDate closingDate,
			@RequestParam("progress")Integer progress,
			@RequestParam("memo")String memo) {
		Integer userId = account.getId();
		Task task = new Task(id,categoryId,userId,title,closingDate,progress,memo);
		taskRepository.save(task);
		return "redirect:/tasks";
	}
	@PostMapping("/tasks/{id}/delete")
	public String taskDelete(
			@PathVariable("id")Integer id) {
		taskRepository.deleteById(id);
		return "redirect:/tasks";
	}
	
}
