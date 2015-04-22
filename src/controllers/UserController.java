package controllers;

import content.Forum;
import exceptions.UsernameAlreadyExistsException;
import policy.PolicyHandler;
import users.FriendRequest;
import users.Report;
import users.User;
import utils.Cipher;
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

	public User login(Forum forum, String username, String password) throws NoSuchAlgorithmException {
		User user = getUserFromForum(username, forum);
		if (user == null)
			return null;
		if (!user.getCipheredPassword().equals(Cipher.cipherString(password, "SHA")))
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

	public User register(Forum forum, String username, String password, String emailAddr) throws UsernameAlreadyExistsException, NoSuchAlgorithmException {
		if (getUserFromForum(username, forum) != null)
			throw new UsernameAlreadyExistsException("Username: " + username + " already exists in forum: " + forum.getName() + ".");
		User newUser = new User(username, Cipher.cipherString(password, "SHA"), emailAddr);
		sendVerificationMail(emailAddr);
		User newUser = new User(username, cipherString(password), emailAddr);
		sendVerificationMail(emailAddr, username);
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

	public void sendVerificationMail(String toAddressStr, String name){
		try {
			String from = userName;
			String pass = password;
			//Setting mail properties
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", pass);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			//props.put("mail.debug", "true");
			//setting the session to be gmail.
			Session session = Session.getInstance(props, new GMailAuthenticator(userName, password));
			MimeMessage message = new MimeMessage(session);
			Address fromAddress = new InternetAddress(from);
			Address toAddress = new InternetAddress(toAddressStr);
			//setting message properties
			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(name + ", verify your account");
			message.setText("Hi " + name + ",\n Please verify your account by replying this mail!\n Greetings,\n Yuval, Shai, Tom, Roee and Hadar");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			message.saveChanges();
			//Sending message
			Transport.send(message);
			transport.close();

		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}
	}

	private boolean authorizedMailIncome(String mailAddress){
		System.out.println("enter!");
		long start = System.currentTimeMillis() / 1000;
		while(((System.currentTimeMillis()/1000) - start) < 600) {
			Properties props2=System.getProperties();
			props2.setProperty("mail.store.protocol", "imaps");
			Session session2=Session.getDefaultInstance(props2, null);
			try {
				//stops when 3 minutes passed
				Store store = session2.getStore("imaps");

				store.connect(host, userName, password);

				Folder folder = store.getFolder("INBOX");//get inbox

				folder.open(Folder.READ_ONLY);//open folder only to read

				Message message[] = folder.getMessages();

				for (int i = 0; i < message.length; i++) {

					//print subjects of all mails in the inbox
					Address[] a = message[i].getAllRecipients();
					for (int j = 0; j < a.length; j++) {
						System.out.println("out!");
						return true;

					}


					//anything else you want

				}

				//close connections

				folder.close(true);

				store.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println("out!");
		return false;

	}
}
