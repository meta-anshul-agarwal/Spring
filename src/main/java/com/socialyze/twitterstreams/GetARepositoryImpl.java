package com.socialyze.twitterstreams;

import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.socialyze.entity.ChatMessage;
import com.socialyze.entity.Chating;
import com.socialyze.entity.Employee;
import com.socialyze.entity.ReceivedMail;
import com.socialyze.entity.SentMail;
import com.socialyze.entity.Status;
import com.socialyze.entity.Tasks;

import ch.qos.logback.core.net.SyslogOutputStream;

@Component
public class GetARepositoryImpl implements GetARepositoryCustom {

	@Autowired
    MongoTemplate mongoTemplate;
	
	@Override
	public void saveCollection(Employee employee) {
		// TODO Auto-generated method stub
		
	       mongoTemplate.save(employee, "Employee");
		
	}

	@Override
	public List<Employee> getAll(String userId, String token , Object employee) {
		// TODO Auto-generated method stub
		Employee emp = getEmployee(userId);
		if(checkAccessControl(emp,token)) {
			List<Employee> list = mongoTemplate.findAll(Employee.class, "Employee");
			for(Employee e : list) {
				if(e.employeeID.equals(((Employee)employee).employeeID)) {
					list.remove(e);
					break;
				}
			}
			return list;
		}
		return null;
		
	}

//	@Override
//	public void removeEmployee(String[] employees) {
//		// TODO Auto-generated method stub
//		for(String emp : employees) {
//			Query query = new Query();
//			query.addCriteria(Criteria.where("employeeID").is(emp));
//			mongoTemplate.remove(query, Employee.class, emp);
//		}
//	}

	@Override
	public boolean userAuthentication(String userId, String password) {
		Employee employee = getEmployee(userId);
		if((employee != null && employee.employeeID.equals(userId)) && employee.userDetails.password.equals(password)) {
			return true;
		}
		return false;
	}

	@Override
	public Employee setAccessToken(String userId) {
		UUID uuid = UUID.randomUUID();
		String accessToken = uuid.toString();
		Employee employee = getEmployee(userId);
		String token = employee.userDetails.getAccessToken();
		if(token == null) {
			employee.userDetails.setAccessToken(accessToken);
			mongoTemplate.save(employee, "Employee");
			
		}
		employee.userDetails.password = null;
		employee.email = null;
		employee.tasks = null;
		return employee;
	}

	@Override
	public boolean logout(String userId, String token) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			if(employee.userDetails.getAccessToken().equals(token)) {
				employee.userDetails.setAccessToken(null);
				mongoTemplate.save(employee, "Employee");
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Tasks> setTask(String userId,String token, Tasks task ) {
		
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			int listLength = employee.tasks.size();
			if(listLength > 0) {
				task.taskNo = employee.tasks.get(listLength - 1).taskNo + 1;
			} else {
				task.taskNo = 1;
			}
			task.status = Status.ASSIGNED;
			employee.tasks.add(task);
			mongoTemplate.save(employee, "Employee");
			return employee.tasks;
		}
		return null;
	}
	
	boolean checkAccessControl(Employee employee , String accessToken) {
		if(employee.userDetails.accessToken.equals(accessToken)) {
			return true;
		}
		return false;
		
	}

	@Override
	public List<Tasks> updateStatus(String userId, String token, int TaskNo) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			for(Tasks tasks : employee.tasks) {
				if(TaskNo == tasks.taskNo) {
					if(tasks.status == Status.ASSIGNED) {
						tasks.status = Status.ONGOING;
					}
					else if(tasks.status == Status.ONGOING){
						tasks.status = Status.ASSIGNED;
					}
					mongoTemplate.save(employee, "Employee");
					return employee.tasks;
				}
			}
		}
		return null;
	}
	@Override
	public List<Tasks> getTasks(String userId, String token) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			return employee.tasks;
		}
		return null;
	}
	@Override
	public List<Tasks> updateCompleteStatus(String userId, String token, int TaskNo) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			for(Tasks tasks : employee.tasks) {
				if(TaskNo == tasks.taskNo) {
					tasks.status = Status.COMPLETED; 
					mongoTemplate.save(employee, "Employee");
					return employee.tasks;
				}
			}
		}
		return null;
	}

	@Override
	public List<Tasks> removeTask(String userId, String token, int taskNo) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			for(Tasks tasks : employee.tasks) {
				if(taskNo == tasks.taskNo) {
					employee.tasks.remove(tasks);
					mongoTemplate.save(employee, "Employee");
					return employee.tasks;
				}
			}
		}
		return null;
	}

	@Override
	public List<Tasks> updateTasks(String userId, String token, int taskNo, Tasks tasks) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			for(Tasks task : employee.tasks) {
				if(taskNo == task.taskNo) {
					task.taskName = tasks.taskName;
					task.deadline = tasks.deadline;
					task.taskDetails = tasks.taskDetails;
					mongoTemplate.save(employee, "Employee");
					return employee.tasks;
				}
			}
		}
		return null;
	}

	@Override
	public boolean saveEmail(String userId, String token, SentMail mail) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			saveToSentMail(employee , mail);
			//employee.email.sent.add(mail);
			saveToInbox(employee.personalDetails.email,employee.personalDetails.image , mail);
			//mongoTemplate.save(employee, "Employee");
			return true;
		}
		return false;
	}
	
	private void saveToSentMail(Employee employee, SentMail mail) {
		String receivers = mail.receivers;
		StringTokenizer emails = new StringTokenizer(receivers , ",");
		while(emails.hasMoreTokens()) {
			Employee emp = findEmployeeByEmail(emails.nextToken());
			SentMail sentMail = new SentMail(emp.personalDetails.email,emp.personalDetails.image , mail.subject , mail.body);
			employee.email.sent.add(sentMail);
			
		}
		
		mongoTemplate.save(employee, "Employee");
	}

	private void saveToInbox(String senderEmail,String profilePic, SentMail mail) {
		String receivers = mail.receivers;
		StringTokenizer emails = new StringTokenizer(receivers , ",");
		ReceivedMail inboxMail = new ReceivedMail(senderEmail,profilePic , mail.subject , mail.body);
		while(emails.hasMoreTokens()) {
			Employee employee = findEmployeeByEmail(emails.nextToken());
			employee.email.inbox.add(inboxMail);
			mongoTemplate.save(employee, "Employee");
		}
		
	}

	private Employee findEmployeeByEmail(String employeeEmail) {
		Query query = new Query();
		query.addCriteria(Criteria.where("personalDetails.email").is(employeeEmail));
		Employee employee = mongoTemplate.findOne(query, Employee.class, "Employee");
		return getEmployee(employee.employeeID);
	}

	public Employee getEmployee(String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("employeeID").is(userId));
		return mongoTemplate.findOne(query, Employee.class, "Employee");
	}

	@Override
	public List<ReceivedMail> getEmail(String userId, String token) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			return employee.email.inbox;
		}
		return null;
	}
	@Override
	public List<SentMail> getSentEmail(String userId, String token) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			return employee.email.sent;
		}
		return null;
	}
	@Override
	public List<Tasks> getTasks(String userId, String token, String user) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee,token)) {
			if(user.equals(userId)) {
				return employee.tasks;
			}
			List<Employee> employees = getAll(userId , token , employee);
			for(Employee emp : employees) {
				if(emp.employeeID.equals(user)) {
					return emp.tasks;
				}
			}	
		}
		return null;
	}

	@Override
	public boolean storeChatMessage(String userId, String token,String chatId ,  ChatMessage chatMessage) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee, token)) {
			saveChatMessage(userId,chatId , chatMessage);
			return true;
		}
		
		return false;
	}
	
	boolean saveChatMessage(String userId ,String chatId, ChatMessage chatMessage) {
		String chattingId = createChattingId(userId , chatId);
		Chating chat =  mongoTemplate.findById(chattingId, Chating.class, "chating");
		chat.chatmessage.add(chatMessage);
		mongoTemplate.save(chat , "chating");
		return true;
		
	}

	private String createChattingId(String userId, String chatId) {
		if(userId.compareToIgnoreCase(chatId) < 0) {
			return userId + "_" + chatId; 
		}
		return chatId + "_" + userId;
	}

	@Override
	public List<ChatMessage> getChatMessage(String userId, String token, String chatId) {
		Employee employee = getEmployee(userId);
		if(checkAccessControl(employee, token)) {
			String chattingId = createChattingId(userId , chatId);
			if(mongoTemplate.getCollection("chating") != null) {
				Chating chat =  mongoTemplate.findById(chattingId, Chating.class, "chating");
				if(chat != null) {
					return chat.chatmessage;
				}
				Chating chatting = new Chating(chattingId , new ArrayList<ChatMessage>());
				mongoTemplate.save(chatting , "chating");
				return chatting.chatmessage;
			}
			Chating chatting = new Chating(chattingId , new ArrayList<ChatMessage>());
			mongoTemplate.save(chatting , "chating");
			return chatting.chatmessage;

		}
		return null;
	}
}
