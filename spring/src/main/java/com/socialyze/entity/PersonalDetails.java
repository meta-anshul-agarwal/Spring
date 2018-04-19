package com.socialyze.entity;

public class PersonalDetails {

	public String name;
	public String image;
	public String designation;
	public String address;
	public String email;
	public String interest;
	public String remarks;
	
	public PersonalDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonalDetails(String name, String image, String designation, String address, String email, String interest,
			String remarks) {
		super();
		this.name = name;
		this.image = image;
		this.designation = designation;
		this.address = address;
		this.email = email;
		this.interest = interest;
		this.remarks = remarks;
	}
	
	
}
