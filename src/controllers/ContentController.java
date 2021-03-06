package controllers;

import content.Forum;
import content.Message;
import content.SubForum;
import content.Thread;
import exceptions.EmptyMessageTitleAndBodyException;
import users.Notification;
import users.User;
import utils.HibernateUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ContentController {
	
	public static boolean editPost(Message post, String body) {
		if (post.edit(body)) {
            post.sendNotificationToAllUsersCommented(Notification.editMessageNotification(post));
			return HibernateUtils.update(post);
		}
		return false;
	}
	
	public static boolean deletePost(Message post) {
		if (post.deleteSelf()) {
            post.sendNotificationToAllUsersCommentedRecursively(Notification.deleteMessageNotification(post));
            if (post.getEnclosingMessage() == null) {
                Thread thread = post.getThread();
                SubForum subForum = thread.getSubForum();
                HibernateUtils.del(post);
                HibernateUtils.del(thread);
                return HibernateUtils.update(subForum);
            }
			return HibernateUtils.update(post.getEnclosingMessage()) && HibernateUtils.del(post);
		}
		return false;
	}
	
	public static List<SubForum> viewSubForumList(Forum forum) {
		return forum.getSubForums();
	}
	
	public static List<Thread> viewThreads(SubForum subForum) {
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

	public static Thread openNewThread(Forum forum, SubForum subforum, String title, String content, User user) throws EmptyMessageTitleAndBodyException {
		if ((title == null || title.equals("")) && (content == null || content.equals(""))) {
			throw new EmptyMessageTitleAndBodyException();
		}
		Message openingMsg = new Message(title, content, user, null, null);
		Thread threadAdd = new Thread(user, openingMsg, subforum);
		if (subforum.addThread(threadAdd)) {
            forum.sendNotificationToAllUsers(Notification.newThreadNotification(threadAdd));
			HibernateUtils.save(threadAdd);
			HibernateUtils.merge(subforum);
			HibernateUtils.merge(forum);
			return threadAdd;
		}
		return null;
	}
	
	public static Message reply(Forum forum, Message addTo, String title, String content, User user) throws EmptyMessageTitleAndBodyException {
		if ((title==null || title.equals("")) && (content==null || content.equals("")) ) {
			throw new EmptyMessageTitleAndBodyException();
		}
		Message comment = new Message(title, content, user, null, addTo);
		if (addTo.addComment(comment)) {
            forum.sendNotificationToAllUsers(Notification.repliedToMessageNotification(user, addTo));
            HibernateUtils.merge(addTo);
			return comment;
		}
		return null;
	}
	
	public static boolean deleteSubForum(Forum forum, SubForum subForum) {
		if (forum.deleteSubForum(subForum)) {
			return HibernateUtils.update(forum) && HibernateUtils.del(subForum);
		}
		return false;
	}

	public static SubForum addSubForum(Forum forum, String title, User moderator) {
		SubForum sub = new SubForum(title, moderator, forum.getPolicy().getMaxModerators());
		if (forum.addSubForum(sub)) {
			HibernateUtils.save(sub);
			HibernateUtils.merge(forum);
			return sub;
		}
		return null;
	}
}
