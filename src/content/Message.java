package content;

import users.Member;



public class Message {
	int id;
	String title="";
	String body="";
	java.sql.Date date;
	//date last edited?
	Member publisher;
	Thread thread; // or opening post id.
	
	public boolean edit(String body, Member member) {
		//check user's own post. or moderator
		return false;
	}
	
	public boolean delete(Member member) {
		//check user's own post. or moderator
		return false;
	}
}
