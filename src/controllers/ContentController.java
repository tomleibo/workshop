package controllers;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import exceptions.EmptyMessageTitleAndBodyException;
import exceptions.UserNotAuthorizedException;
import policy.ForumPolicy;
import policy.PolicyHandler;
import users.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ContentController {
	
	public static boolean editPost(Forum forum, SubForum subForum, User user, Message msg, String body) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserEditComment(forum, subForum, user, msg)) {
			return msg.edit(body);	
		}
		throw new UserNotAuthorizedException("to edit post.");
	}
	
	public static boolean deletePost(Forum forum, SubForum subForum, User user, Message msg) throws UserNotAuthorizedException {
		if (PolicyHandler.canUserDeleteComment(forum, subForum,user, msg)) {
			return msg.deleteSelf();	
		}
		throw new UserNotAuthorizedException("to delete post.");
	}
	
	public static List<SubForum> viewSubForumList(Forum forum,User user) throws UserNotAuthorizedException {
		if(PolicyHandler.canUserViewSubForums(forum,user)) {
			return forum.getSubForums();
		}
		throw new UserNotAuthorizedException("to view subforum list.");
	}
	
	public static List<Thread> viewThreads(Forum forum,SubForum subForum,User user) throws UserNotAuthorizedException {
		if(PolicyHandler.canUserViewSubForums(forum,user)) {
			return subForum.viewThreads();
		}
		throw new UserNotAuthorizedException("to view threads.");
	}
	
	public static List<Message> searchMessages(Forum forum, String title,String content,String memberName, java.sql.Date startDate, java.sql.Date endDate) {
		List<Message> ans = new ArrayList<>();
		for (SubForum sub : forum.getSubForums()) {
			for (Thread thread : sub.viewThreads()) {
				ans.addAll(getAllMessages(thread.getOpeningMessage(),forum,title,content,memberName,startDate,endDate));
			}
		}
		return ans;
	}
	
	private static Collection<? extends Message> getAllMessages(Message openingMessage,Forum forum, String title,
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

	public static Thread openNewThread(Forum forum,SubForum sbfrm, String title,String content, User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		if (!PolicyHandler.canUserOpenThread(forum, user)){
			throw new UserNotAuthorizedException("to open thread.");
		}
		if ((title==null || title.equals("")) && (content==null || content.equals("")) ) {	
			throw new EmptyMessageTitleAndBodyException();
		}
		Message openingMsg = new Message(title, content, user, null, null);
		Thread threadAdd = new Thread(user, openingMsg, sbfrm);
		if (sbfrm.addThread(threadAdd) ) {
			return threadAdd;
		}
		return null;
	}
	
	public static Message reply(Forum forum, Message addTo, String title,String content,User user) throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
		if (PolicyHandler.canUserReply(forum,user)) {
			if ((title==null || title.equals("")) && (content==null || content.equals("")) ) {	
				throw new EmptyMessageTitleAndBodyException();
			}
			else {
				Message comment = new Message(title, content, user, null, addTo);
				if (addTo.addComment(comment)) {
					return comment;
				}
			}
		}
		throw new UserNotAuthorizedException("to reply.");
	}
	
	public static boolean deleteSubForum(Forum forum, SubForum subForum,User user) throws UserNotAuthorizedException {
		if (!PolicyHandler.canUserDeleteSubForum(forum,user)) {
			throw new UserNotAuthorizedException("to delete sub forum");
		}
		return forum.deleteSubForum(subForum);
	}
	
	public static boolean defineProperties(Forum forum, ForumPolicy policy) {
		String warning = "this method is not tested";
		return forum.setPolicy(policy);
	}
	
	public static SubForum addSubForum(Forum forum, String title, User mod) throws UserNotAuthorizedException {
		if (!PolicyHandler.canUserAddSubForum(forum, mod)) {
			throw new UserNotAuthorizedException("to add subForum.");
		}
		SubForum sub = new SubForum(title, mod, forum.getPolicy().getMaxModerators());
		if (forum.addSubForum(sub)) {
			return sub;
		}
		return null;
	}
}
