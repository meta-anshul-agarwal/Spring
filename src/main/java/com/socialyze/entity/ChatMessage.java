package com.socialyze.entity;

import java.util.Date;

public class ChatMessage {
	
	public String message;
	public String date;
	public String messageSender;
	
	public ChatMessage(String message,String date ,  String messageSender) {
		super();
		this.message = message;
		this.date = date;
		this.messageSender = messageSender;
	}
	public ChatMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
