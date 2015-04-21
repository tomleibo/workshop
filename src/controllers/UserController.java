package controllers;

import content.Forum;
import exceptions.UsernameAlreadyExistsException;
import policy.PolicyHandler;
import users.FriendRequest;
import users.Report;
import users.User;
import utils.GMailAuthenticator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class UserController {

	String host ="imap.gmail.com"; //for imap protocol
	String userName = "sadnase2015@gmail.com";
	String password = "sadna2015";

	public User login(Forum forum, String username, String password) {
		User user = getUserFromForum(username, forum);
		if (user == null)
			return null;
		if (!user.getHashedPassword().equals(cipherString(password)))
			return null;
		return user;
	}

	public User enterAsGuest(Forum forum) {
		return User.newGuestUser();
	}
	
	public boolean logout(Forum forum, String username) {
		User user = getUserFromForum(username, forum);
		return user != null && user.logout();
	}

	public User register(Forum forum, String username, String password, String emailAddr) throws UsernameAlreadyExistsException {
		if (getUserFromForum(username, forum) != null)
			throw new UsernameAlreadyExistsException("Username: " + username + " already exists in forum: " + forum.getName() + ".");
		User newUser = new User(username, cipherString(password), emailAddr);
		sendVerificationMail(emailAddr);
		if(authorizedMailIncome(emailAddr)) {
			forum.addMember(newUser);
			return newUser;
		}
		return null;
	}
	
	public boolean sendFriendRequest(Forum forum, User from, User to, String message) {
		if (PolicyHandler.canUserHaveFriends(forum, from) & PolicyHandler.canUserHaveFriends(forum, to)) {
			FriendRequest request = new FriendRequest(from, to, message);
			return to.addFriendRequest(request);
		}
		return false;
	}
	
	public boolean removeFriend(User user, User friend) {
		return friend.deleteFriend(user) && user.deleteFriend(friend);
	}
	
	public String viewOwnProfile(User user) {
		return user.toString();
	}
	
	public boolean replyToFriendRequest(FriendRequest request, boolean answer) {
		if (answer) {
			User requesting = request.getRequestingMember();
			User receiving = request.getReceivingMember();
			return requesting.addFriend(receiving) && receiving.addFriend(requesting);
		}
		return false;
	}
	
	public boolean report(Forum forum, User reporter, User admin, String title, String content) {
		Report report = new Report(title, content, reporter, admin);
		return forum.addReport(report) && reporter.addSentReport(report);
	}
	
	public boolean deactivate(User member) {
		return member.deactivate();
	}
	
	private User getUserFromForum(String username, Forum forum) {
		List<User> members = forum.getMembers();
		for (User member : members) {
			if (member.getUserName().equals(username))
				return member;
		}
		return null;
	}
	
	private String cipherString(String string) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(string.getBytes());
			return Arrays.toString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return string;
		}
	}

	public void sendVerificationMail(String toAddressStr){
		try {
			String from = userName;
			String pass = password;
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", pass);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");


			Session session = Session.getInstance(props, new GMailAuthenticator(userName, password));
			MimeMessage message = new MimeMessage(session);
			Address fromAddress = new InternetAddress(from);
			Address toAddress = new InternetAddress(toAddressStr);

			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);

			message.setSubject("Testing JavaMail");
			message.setText("Welcome to JavaMail");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			message.saveChanges();
			Transport.send(message);
			transport.close();

		}catch(Exception ex){


		}
	}

	private boolean authorizedMailIncome(String mailAddress){

		/*this will print subject of all messages in the inbox of sender@gmail.com*/
		long start = System.currentTimeMillis() / 1000;


		Properties props2=System.getProperties();

		props2.setProperty("mail.store.protocol", "imaps");
		// I used imaps protocol here

		Session session2=Session.getDefaultInstance(props2, null);

		try {
			while(((System.currentTimeMillis()/1000) - start) < 180) {

				Store store = session2.getStore("imaps");

				store.connect(host, userName, password);

				Folder folder = store.getFolder("INBOX");//get inbox

				folder.open(Folder.READ_ONLY);//open folder only to read

				Message message[] = folder.getMessages();

				for (int i = 0; i < message.length; i++) {

					//print subjects of all mails in the inbox
					Address[] a = message[i].getAllRecipients();
					for (int j = 0; j < a.length; j++) {
						return true;

					}


					//anything else you want

				}

				//close connections

				folder.close(true);

				store.close();
			}

		} catch (Exception e) {

			System.out.println(e.toString());

		}
		return false;

	}
}
