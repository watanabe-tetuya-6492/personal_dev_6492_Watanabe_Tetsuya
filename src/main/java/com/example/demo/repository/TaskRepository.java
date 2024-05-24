package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer>{
	List<Task>findByCategoryId(Integer categoryId);
	List<Task>findByClosingDate(LocalDate closingDate);
	List<Task>findByClosingDateOrderByIdAsc(LocalDate closingDate);
	List<Task>findByProgress(Integer progress);
	List<Task>findAllByOrderByIdAsc();
}
