package com.socialyze.entity;

import java.util.Date;

public class SentMail {
	public String receivers;
	public String receiverPic;
	public String subject;
	public String body;
	public Date date = new Date();
	public SentMail() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SentMail(String receivers,String receiverPic , String subject, String body) {
		super();
		this.receivers = receivers;
		this.receiverPic = receiverPic;
		this.subject = subject;
		this.body = body;
	}
	
}
