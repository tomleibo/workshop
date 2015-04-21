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
import exceptions.UserNotAuthorized;


public class ContentController {
	
	
	
	public boolean editPost(Forum forum, User user, Message msg, String body) throws UserNotAuthorized {
		if (PolicyHandler.canUserEditComment(forum, user, msg)) {
			return msg.edit(body);	
		}
		throw new UserNotAuthorized("to edit post.");
		
	}
	
	public boolean deletePost(Forum forum, User user, Message msg) throws UserNotAuthorized {
		if (PolicyHandler.canUserDeleteComment(forum, user, msg)) {
			return msg.deleteSelf();	
		}
		throw new UserNotAuthorized("to delete post.");
	}
	
	public List<SubForum> viewSubForumList(Forum forum,User user) throws UserNotAuthorized {
		if(PolicyHandler.canUserViewSubForums(forum,user)) {
			return forum.getSubForums();
		}
		throw new UserNotAuthorized("to view subforum list.");
		
	}
	
	public List<Thread> viewThreads(Forum forum,SubForum subForum,User user) throws UserNotAuthorized {
		if(PolicyHandler.canUserViewSubForums(forum,user)) {
			return subForum.viewThreads();
		}
		throw new UserNotAuthorized("to view threads.");
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
		//TODO: delete
		System.out.println(openingMessage);
		List<Message> ans = new ArrayList<>();
		if ((title==null ? false : openingMessage.getTitle().startsWith(title)) ||
				(content==null ? false : openingMessage.getBody().startsWith(content))||
				(memberName==null ? false : memberName.equals(openingMessage.getUser().getUserName())) ||
				((startDate==null || endDate==null) ? false : startDate.getTime() < openingMessage.getDate().getTime() && endDate.getTime() > openingMessage.getDate().getTime())
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

	public Thread openNewThread(Forum forum,SubForum sbfrm, String title,String content, User user) throws UserNotAuthorized{
		if (!PolicyHandler.canUserOpenThread(forum, user)){
			throw new UserNotAuthorized("to open thread.");
		}
		Message openingMsg = new Message(title, content, user, null, null);
		Thread threadAdd = new Thread(user, openingMsg, sbfrm);
		if (sbfrm.addThread(threadAdd) ) {
			return threadAdd;
		}
		return null;
	}
	
	public Message reply(Message addTo, String title,String content,User user) throws UserNotAuthorized {
		Message comment = new Message(title, content, user, null, addTo);
		if (addTo.addComment(comment)) {
			return comment;
		}
		throw new UserNotAuthorized("to reply.");
	}
	
	public boolean deleteSubForum(Forum forum, SubForum subForum,User user) throws UserNotAuthorized {
		if (!PolicyHandler.canUserDeleteSubForum(forum,user)) {
			throw new UserNotAuthorized("to delete sub forum");
		}
		return forum.deleteSubForum(subForum);
	}
	
	public boolean defineProperties(Forum forum, ForumPolicy policy) {
		String warning = "this method is not tested";
		return forum.setProperties(policy);
	}
	
	public SubForum addSubForum(Forum forum, String title, User mod) throws UserNotAuthorized {
		if (!PolicyHandler.canUserAddSubForum(forum, mod)) {
			throw new UserNotAuthorized("to add subForum.");
		}
		SubForum sub = new SubForum(title, mod, forum.getProperties().getMaxModerators());
		if (forum.addSubForum(sub)) {
			return sub;
		}
		return null;
	}
}
