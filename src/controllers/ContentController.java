package controllers;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import exceptions.EmptyMessageTitleAndBodyException;
import policy.ForumPolicy;
import users.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ContentController {
	
	public static boolean editPost(Forum forum, SubForum subForum, User user, Message msg, String body) {
		return msg.edit(body);
	}
	
	public static boolean deletePost(Forum forum, SubForum subForum, User user, Message msg) {
		return msg.deleteSelf();
	}
	
	public static List<SubForum> viewSubForumList(Forum forum, User user) {
		return forum.getSubForums();
	}
	
	public static List<Thread> viewThreads(Forum forum, SubForum subForum, User user) {
		return subForum.viewThreads();
	}
	
	public static List<Message> searchMessages(Forum forum, String title, String content, String memberName, java.sql.Date startDate, java.sql.Date endDate) {
		List<Message> ans = new ArrayList<>();
		for (SubForum sub : forum.getSubForums()) {
			for (Thread thread : sub.viewThreads()) {
				ans.addAll(getAllMessages(thread.getOpeningMessage(),forum,title,content,memberName,startDate,endDate));
			}
		}
		return ans;
	}
	
	private static Collection<? extends Message> getAllMessages(Message openingMessage, Forum forum, String title,
			String content, String memberName, java.sql.Date startDate, java.sql.Date endDate) {
		// TODO: delete
		List<Message> ans = new ArrayList<>();
		if ((title != null && openingMessage.getTitle().startsWith(title)) ||
				(content != null && openingMessage.getBody().startsWith(content))||
				(memberName != null && memberName.equals(openingMessage.getUser().getUsername())) ||
				((!(startDate == null || endDate == null)) && startDate.getTime() < openingMessage.getDate().getTime() && endDate.getTime() > openingMessage.getDate().getTime())) {
			ans.add(openingMessage);
		}
		if (openingMessage.getComments() != null) {
			for (Message msg : openingMessage.getComments()) {
				ans.addAll(getAllMessages(msg,forum,title,content,memberName,startDate,endDate));
			}
		}
		return ans;
	}

	public static Thread openNewThread(Forum forum, SubForum subforum, String title,String content, User user) throws EmptyMessageTitleAndBodyException {
		if ((title == null || title.equals("")) && (content == null || content.equals(""))) {
			throw new EmptyMessageTitleAndBodyException();
		}
		Message openingMsg = new Message(title, content, user, null, null);
		Thread threadAdd = new Thread(user, openingMsg, subforum);
		if (subforum.addThread(threadAdd)) {
			return threadAdd;
		}
		return null;
	}
	
	public static Message reply(Forum forum, Message addTo, String title,String content, User user) throws EmptyMessageTitleAndBodyException {
		if ((title==null || title.equals("")) && (content==null || content.equals("")) ) {
			throw new EmptyMessageTitleAndBodyException();
		}
		Message comment = new Message(title, content, user, null, addTo);
		if (addTo.addComment(comment)) {
			return comment;
		}
		return null;
	}
	
	public static boolean deleteSubForum(Forum forum, SubForum subForum, User user) {
		return forum.deleteSubForum(subForum);
	}
	
	public static boolean defineProperties(Forum forum, ForumPolicy policy) {
		// TODO - Method not tested.
		return forum.setPolicy(policy);
	}
	
	public static SubForum addSubForum(Forum forum, String title, User mod) {
		SubForum sub = new SubForum(title, mod, forum.getPolicy().getMaxModerators());
		if (forum.addSubForum(sub)) {
			return sub;
		}
		return null;
	}
}
