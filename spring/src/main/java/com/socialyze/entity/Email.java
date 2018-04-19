package com.socialyze.entity;

import java.util.ArrayList;
import java.util.List;

public class Email {
	public List<ReceivedMail> inbox = new ArrayList<>();
	public List<SentMail> sent = new ArrayList<>();
}
