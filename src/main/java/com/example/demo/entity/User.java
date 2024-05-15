package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String email;
	private String password;
	
	
	public User() {
	}
	public User(Integer id,String name,String email,String password) {
		this.id=id;
		this.name=name;
		this.email=email;
		this.password=password;
	}
	public User(String name,String email,String password) {
		this.name=name;
		this.email=email;
		this.password=password;
	}
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	
	

}
