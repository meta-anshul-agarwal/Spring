package com.socialyze.entity;

import java.util.Date;

public class Tasks {
	
	public String taskName;
	public Date deadline;
	public Status status;
	public String taskDetails;
	public int taskNo;
	

	@Override
	public String toString() {
		String str = "";
		str += "task name: " + taskName;
		str += ", deadline: " + deadline;
		str += ", status:" + status;
		str += ", task detail: " + taskDetails;
		return str;
	}
	
}
