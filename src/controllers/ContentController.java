package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import policy.ForumPolicy;
import policy.PolicyHandler;
import users.User;
import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;


public class ContentController {
	
	public boolean editPost(Forum forum, User user, Message msg, String body) {
		if (PolicyHandler.canUserEditComment(forum, user, msg)) {
			return msg.edit(body);	
		}
		return false;
		
	}
	
	public boolean deletePost(Forum forum, User user, Message msg) {
		if (PolicyHandler.canUserDeleteComment(forum, user, msg)) {
			return msg.deleteSelf(msg);	
		}
		return false;
		
	}
	
	public List<SubForum> viewForumList(Forum forum) {
		return forum.getSubForums();
	}
	
	public List<Thread> viewThreads(SubForum subForum) {
		return subForum.viewThreads();
	}
	
	public List<Message> searchMessages(Forum forum, String title,String content,String memberName, java.sql.Date startDate, java.sql.Date endDate) {
		List<Message> ans = new ArrayList<>();
		for (SubForum sub : forum.getSubForums()) {
			for (Thread thread : sub.viewThreads()) {
				ans.addAll(getAllMessages(thread.getOpeningMessage(),forum,title,content,memberName,startDate,endDate));
			}
		}
		return ans;
	}
	
	private Collection<? extends Message> getAllMessages(Message openingMessage,Forum forum, String title,
			String content,String memberName, java.sql.Date startDate, java.sql.Date endDate) {
		List<Message> ans = new ArrayList<>();
		if ((title==null ? false : openingMessage.getTitle().equals(title)) ||
				(content==null ? false : openingMessage.getBody().startsWith(content))||
				(memberName==null ? false : memberName.equals(openingMessage.getUser()))
				) {
			ans.add(openingMessage);
		}
		if (openingMessage.getComments() != null) {
			for (Message msg : openingMessage.getComments()) {
				ans.addAll(getAllMessages(msg,forum,title,content,memberName,startDate,endDate));
			}
		}
		return ans;
	}

	public Thread openNewThread(SubForum sbfrm, String title,String content, User user) {
		Message openingMsg = new Message(title, content, user, null, null);
		Thread threadAdd = new Thread(user, openingMsg, sbfrm);
		if (openingMsg.setThread(threadAdd)) {
			return threadAdd;
		}
		return null;
	}
	
	public Message reply(Message addTo, String title,String content,User user) {
		Message comment = new Message(title, content, user, null, addTo);
		if (addTo.addComment(comment)) {
			return comment;
		}
		return null;
	}
	
	public boolean deleteSubForum(Forum forum, SubForum subForum) {
		return forum.deleteSubForum(subForum);
	}
	
	public boolean defineProperties(Forum forum, ForumPolicy policy) {
		return forum.setProperties(policy);
	}
	
	public SubForum addSubForum(Forum forum, String title, User mod) {
		SubForum sub = new SubForum(title, mod, forum.getProperties().getMaxModerators());
		if (forum.addSubForum(sub)) {
			return sub;
		}
		return null;
	}

}
