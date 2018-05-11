package com.socialyze.entity;

import java.util.Date;

public class ReceivedMail {
	public String sender;
	public String senderProfilePic;
	public String subject;
	public String body;
	public Date date = new Date();
	public ReceivedMail() {
		super();
	}
	public ReceivedMail(String sender,String senderProfilePic, String subject, String body) {
		super();
		this.sender = sender;
		this.senderProfilePic = senderProfilePic;
		this.subject = subject;
		this.body = body;
	}
}
