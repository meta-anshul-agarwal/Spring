package com.socialyze.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
public class Employee {

	@Id
	public String employeeID;
	public UserDetails userDetails;
	public PersonalDetails personalDetails;
	public List<Tasks> tasks = new ArrayList<>();
	public Email email = new Email();
	public Employee() {
		super();
		
	}

	public Employee(String employeeID, UserDetails userdetails, PersonalDetails personalDetails) {
		super();
		this.employeeID = employeeID;
		this.userDetails = userdetails;
		this.personalDetails = personalDetails;
	}
	
	@Override
	public String toString() {
		String str = "\n";
		str += "emp ID : " + this.employeeID;
		str += ", task: " + this.tasks;
		return str;
	}
	
}
