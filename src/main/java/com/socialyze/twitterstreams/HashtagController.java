package com.socialyze.twitterstreams;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.util.JSON;
import com.socialyze.entity.ChatMessage;
import com.socialyze.entity.Chating;
import com.socialyze.entity.Employee;
import com.socialyze.entity.ReceivedMail;
import com.socialyze.entity.SentMail;
import com.socialyze.entity.Tasks;

@CrossOrigin
@RestController
public class HashtagController {
	@Autowired
	GetARepositoryCustom getA;

	@RequestMapping(value="/add" , method=RequestMethod.POST)
	public String add(@RequestBody Employee employee){
		getA.saveCollection(employee);
		return employee.userDetails.getAccessToken();
	}
	
	@RequestMapping(value = "/getAllEmployee/{userId}" , method=RequestMethod.POST)
	public List<Employee> getAll(@PathVariable String userId, @RequestParam("token") String token , @RequestBody Employee employee){
		return getA.getAll(userId , token , employee);
	}
	
//	@RequestMapping(value = "/employee" , method=RequestMethod.POST)
//	public void removeEmployee(@RequestBody String[] employees){
//		getA.removeEmployee(employees);
//	}
//	
	@RequestMapping(value = "/login/{userId}" , method=RequestMethod.GET)
	public Employee userAuthenticate(@PathVariable String userId , @RequestParam("password") String password) {
		if (getA.userAuthentication(userId , password)) {
			return getA.setAccessToken(userId);
		}
		return null;	
	}
	

	@RequestMapping(value = "/logout/{userId}" , method=RequestMethod.GET)
	public boolean logout(@PathVariable String userId , @RequestParam("token") String token) {
			return getA.logout(userId , token);
	}
	
	@RequestMapping(value = "/{userId}" , method=RequestMethod.POST)
	public List<Tasks> addTask(@PathVariable String userId , @RequestParam("token") String token , @RequestBody Tasks tasks) {
			return getA.setTask(userId , token , tasks);
	}
	
	@RequestMapping(value = "/{userId}/tasks" , method=RequestMethod.POST)
	public List<Tasks> updateStatus(@PathVariable String userId, @RequestParam("token")String token, @RequestParam("taskID")int TaskNo) {
			return getA.updateStatus(userId, token, TaskNo);
	}
	@RequestMapping(value = "/{userId}/tasks/complete" , method=RequestMethod.POST)
	public List<Tasks> updateCompleteStatus(@PathVariable String userId, @RequestParam("token")String token, @RequestParam("taskID")int TaskNo) {
			return getA.updateCompleteStatus(userId, token, TaskNo);
	}
	@RequestMapping(value = "/{userId}/allTasks" , method=RequestMethod.POST)
	public List<Tasks> GetTask(@PathVariable String userId, @RequestParam("token")String token) {
			return getA.getTasks(userId, token);
	}
	@RequestMapping(value = "/{userId}/tasks/remove" , method=RequestMethod.POST)
	public List<Tasks> removeTask(@PathVariable String userId, @RequestParam("token")String token, @RequestParam("taskID")int TaskNo) {
			return getA.removeTask(userId, token, TaskNo);
	}
	@RequestMapping(value = "/{userId}/tasks/update" , method=RequestMethod.POST)
	public List<Tasks> updateTasks(@PathVariable String userId, @RequestParam("token")String token, @RequestParam("taskID")int TaskNo ,@RequestBody Tasks tasks) {
			return getA.updateTasks(userId, token, TaskNo , tasks);		
	}
	
	@RequestMapping(value = "/{userId}/mail" , method=RequestMethod.POST)
	public boolean saveEmail(@PathVariable String userId, @RequestParam("token")String token, @RequestBody SentMail mail) {
		return getA.saveEmail(userId, token, mail);		
	}
	@RequestMapping(value = "/{userId}/sentMail" , method=RequestMethod.GET)
	public List<SentMail> getSentEmail(@PathVariable String userId, @RequestParam("token")String token) {
		return getA.getSentEmail(userId, token);		
	}
	@RequestMapping(value = "/{userId}/inbox" , method=RequestMethod.GET)
	public List<ReceivedMail> getEmail(@PathVariable String userId, @RequestParam("token")String token) {
			return getA.getEmail(userId, token);		
	}
	@RequestMapping(value = "/{userId}/getTasks" , method=RequestMethod.GET)
	public List<Tasks> getTasks(@PathVariable String userId, @RequestParam("token")String token , @RequestParam("user") String user) {
			return getA.getTasks(userId, token, user);		
	}
	@RequestMapping(value = "/{userId}/chatMessage" , method=RequestMethod.POST)
	public boolean storeChatMessage(@PathVariable String userId, @RequestParam("token")String token , @RequestParam("chatId")String chatId ,@RequestBody ChatMessage chatMessage) {
		return getA.storeChatMessage(userId , token , chatId , chatMessage);
	}
	@RequestMapping(value = "/{userId}/chatMessage" , method=RequestMethod.GET)
	public List<ChatMessage> getChatMessage(@PathVariable String userId, @RequestParam("token")String token , @RequestParam("user")String chatId) {
		System.out.println(chatId);
		return getA.getChatMessage(userId , token , chatId);
	}
}