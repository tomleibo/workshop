package controllers;

import java.util.List;

import content.Message;
import content.SubForum;
import content.Thread;


public class ContentController {
	public boolean editPost() {
		return false;
	}
	
	public boolean deletePost() {
		return false;
	}
	
	public List<SubForum> viewForumList() {
		return null;
	}
	
	public List<Thread> viewForum(SubForum subForum) {
		return null;
	}
	
	public List<Message/*or thread?*/> searchMessages(String title,String content,String memberName, java.sql.Date startDate, java.sql.Date endDate) {
		return null;
	}
	
	public Thread openNewThread(String title,String content) {
		return null;
	}
	
	public Message reply(String title,String content) {
		return null;
	}
}
