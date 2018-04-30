package com.socialyze.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chating")
public class Chating {
	
	@Id
	public String chatId;
	public List<ChatMessage> chatmessage = new ArrayList<>();
	
	
	public Chating() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Chating(String chatId, ChatMessage chatmessage) {
		super();
		this.chatId = chatId;
		this.chatmessage.add(chatmessage);
	}


	public Chating(String chattingId) {
		// TODO Auto-generated constructor stub
		this.chatId = chattingId;
	}


	public Chating(String chattingId, ArrayList<ChatMessage> arrayList) {
		// TODO Auto-generated constructor stub
		this.chatId = chattingId;
		this.chatmessage = arrayList;
	}


	public String getChatId() {
		return chatId;
	}


	public void setChatId(String chatId) {
		this.chatId = chatId;
	}


	public List<ChatMessage> getChatmessage() {
		return chatmessage;
	}


	public void setChatmessage(List<ChatMessage> chatmessage) {
		this.chatmessage = chatmessage;
	}
	
	
}
