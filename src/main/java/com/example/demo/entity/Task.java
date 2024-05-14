package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="categoly_id")
	private Integer categoryId;
	@Column(name="user_id")
	private Integer userId;
	private String title;
	@Column(name="closing_date")
	private LocalDate closingDate;
	private Integer progress;
	private String memo;
	
	public Task() {
	}
	public Task(
			Integer id,
			Integer categoryId,
			Integer userId,
			String title,
			LocalDate closingDate,
			Integer progress,
			String memo) {
		this.id=id;
		this.categoryId=categoryId;
		this.userId=userId;
		this.title=title;
		this.closingDate=closingDate;
		this.progress=progress;
		this.memo=memo;
	}
	public Integer getId() {
		return id;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public Integer getUserId() {
		return userId;
	}
	public String getTitle() {
		return title;
	}
	public LocalDate getClosingDate() {
		return closingDate;
	}
	public Integer getProgress() {
		return progress;
	}
	public String getMemo() {
		return memo;
	}
	

}
