package content;

import users.Member;


public class Thread {
	int id;
	Member memberStarted;
	java.sql.Date data;
	
	public Message addReply(Member member, String title, String content) {
		return null;
	}
	
	public boolean delete(Member member) {
		//check user's own post. or moderator
		//delete all replies.
		return false;
	}
}
