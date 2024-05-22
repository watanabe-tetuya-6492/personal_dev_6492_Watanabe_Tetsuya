package com.example.demo.model;

import java.time.LocalDate;

public class Calender {
	private Integer weekNo;
	private Integer day;
	private String dayOfWeek;
	private LocalDate date;
	
	public Calender() {
	}
	public Calender(Integer weekNo,Integer day,LocalDate date) {
		this.weekNo=weekNo;
		this.day=day;
		this.date=date;
	}
	public Integer getWeekNo() {
		return weekNo;
	}
	public Integer getDay() {
		return day;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public LocalDate getDate() {
		return date;
	}
	
	
}
