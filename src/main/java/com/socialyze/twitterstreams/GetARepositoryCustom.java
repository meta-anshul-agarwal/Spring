package com.socialyze.twitterstreams;

import java.util.List;

import com.socialyze.entity.ChatMessage;
import com.socialyze.entity.Chating;
import com.socialyze.entity.Employee;
import com.socialyze.entity.ReceivedMail;
import com.socialyze.entity.SentMail;
import com.socialyze.entity.Tasks;

public interface GetARepositoryCustom {

	void saveCollection(Employee employee);

	List<Employee> getAll(String userId, String token , Object employee);

//	void removeEmployee(String[] employees);

	boolean userAuthentication(String userId, String password);

	Employee setAccessToken(String userId);

	boolean logout(String userId, String token);
	
	List<Tasks> setTask(String userId, String token, Tasks tasks);
	
	List<Tasks> updateStatus(String userId, String token, int TaskNo);

	List<Tasks> getTasks(String userId, String token);

	List<Tasks> updateCompleteStatus(String userId, String token, int TaskNo);

	List<Tasks> removeTask(String userId, String token, int taskNo);

	List<Tasks> updateTasks(String userId, String token, int taskNo, Tasks tasks);

	boolean saveEmail(String userId, String token, SentMail mail);

	List<ReceivedMail> getEmail(String userId, String token);

	List<Tasks> getTasks(String userId, String token, String user);

	List<SentMail> getSentEmail(String userId, String token);

	boolean storeChatMessage(String userId, String token, String chatId , ChatMessage chatMessage);

	List<ChatMessage> getChatMessage(String userId, String token, String chatId);

}
