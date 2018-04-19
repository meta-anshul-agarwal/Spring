package com.socialyze.entity;

import java.util.Date;

public class SentMail {
	public String[] receivers;
	public String subject;
	public String body;
	Date date = new Date();
	public SentMail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SentMail(String[] receivers, String subject, String body) {
		super();
		this.receivers = receivers;
		this.subject = subject;
		this.body = body;
	}
	
}
