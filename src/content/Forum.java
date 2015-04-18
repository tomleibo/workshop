package content;

import java.util.List;

import users.Member;

@SuppressWarnings("unused")
public class Forum {
	private Member superAdmin;
	private List<SubForum> subForums;
	private List<Thread> threads;
	private List<Message> messages;
	private List<Member> members;
	private List<Policy> policies;
	
}
